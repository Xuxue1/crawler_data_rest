package com.xuxue.crawler.util

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.util.{Base64, Date}

import com.xuxue.crawler.frame.DaMaTuConfig
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import Loan.use
import com.google.gson.Gson
import org.apache.http.client.methods.{HttpGet, HttpUriRequest, RequestBuilder}
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.jsoup.nodes.Document

/**
  * Created by nt on 2017/7/13.
  */
class Recognize {

    val LOG = Recognize.LOG

    @Autowired
    var daMaTuConfig:DaMaTuConfig = _

    def recognize(doc:Document):RecognizeResult= {
        val data = download(doc)
        val stream = new ByteArrayOutputStream()
        stream.write((daMaTuConfig.getAppKey + daMaTuConfig.getUser).getBytes())
        stream.write(data)
        val request = RequestBuilder.post(daMaTuConfig.getRquestUrl)
          .addParameter("appID", daMaTuConfig.getAppID)
          .addParameter("user", daMaTuConfig.getUser)
          .addParameter("pwd", pwd(daMaTuConfig.getUser, daMaTuConfig.getPassword, daMaTuConfig.getAppKey))
          .addParameter("type", "55")
          .addParameter("fileDataBase64", Base64.getEncoder.encodeToString(data))
          .addParameter("sign", md5hex(stream.toByteArray))
          .build()
        use(HttpClients.createDefault()){ client=>
            use(client.execute(request)){ response=>
                val string = EntityUtils.toString(response.getEntity)
                Recognize.G.fromJson(string,classOf[RecognizeResult])
            }
        }
    }

    def md5hex(data: Array[Byte]): String = {
        val md5 = java.security.MessageDigest.getInstance("MD5")
        md5.update(data, 0, data.length)
        byteArray2HexString(md5.digest)
    }

    //转化BYTE数组为16进制字符串
    private def byteArray2HexString(data: Array[Byte]) = {
        val sb = new StringBuilder
        for (b <- data) {
            val s = Integer.toHexString(b & 0xff)
            if (s.length == 1) sb.append("0" + s)
            else sb.append(s)
        }
        sb.toString
    }

    def md5hex(txt: String): String = {
        val data = txt.getBytes("utf-8")
        val md5 = java.security.MessageDigest.getInstance("MD5")
        md5.update(data, 0, data.length)
        byteArray2HexString(md5.digest)
    }

    def download(doc:Document):Array[Byte]={
        val url = "https://svnbackoffice.net/BotDetectCaptcha.ashx?get=image&c=c_svnlogin_ctl00_contentplaceholder1_examplecaptcha"
        val request = RequestBuilder
          .get(url)
          .addParameter("t", doc.select("#BDC_VCID_c_svnlogin_ctl00_contentplaceholder1_examplecaptcha").get(0).attr("value"))
          .addParameter("d", new Date().getTime+"").build
        use(HttpClients.createDefault()){ cliet=>
            use(cliet.execute(request)){response=>
                EntityUtils.toByteArray(response.getEntity)
            }
        }
    }

    def pwd(_uname: String, _upwd: String, _softKey: String): String = {
        val a = md5hex(_uname)
        val b = md5hex(_upwd)
        val c = md5hex(a + b)
        md5hex(_softKey + c)
    }

    def getConfig:DaMaTuConfig = daMaTuConfig

    def setConfig(daMaTuConfig: DaMaTuConfig) = this.daMaTuConfig = daMaTuConfig
}

object Recognize{
    val LOG = LoggerFactory.getLogger(classOf[Recognize])
    val G = new Gson()
}

