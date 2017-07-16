package com.xuxue.crawler.util



import org.apache.http.client.methods.{HttpGet, HttpUriRequest, RequestBuilder}
import org.apache.http.impl.client.{CloseableHttpClient, HttpClients}
import Loan.use
import org.apache.http.util.EntityUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.slf4j.LoggerFactory

/**
  *
  * @author xuxue 2017-7-14
  */
class Login(val userName:String,val password:String,val proxy:String,val recognize: Recognize) {

    val LOG = Login.LOG

    def doLogin():Option[CloseableHttpClient] = {
        val client = HttpClients.createDefault()
        val document  = requestLoginPage(client)
        val request  = buildLoginRequest(document)
        val code = use(client.execute(request)){ request=>
            val headers = request.getAllHeaders
            headers.foreach(header=>LOG.info(s"request header key ${header.getName} value ${header.getValue}"))
            //LOG.info(s"request result ${EntityUtils.toString(request.getEntity)}")
            request.getStatusLine.getStatusCode
        }
        //if(code != 302) return Option.empty
        LOG.info(s"status code $code")
        LOG.info("will request https://svnbackoffice.net/SVNBOUpdate.aspx")
        val get = new HttpGet("https://svnbackoffice.net/SVNBOUpdate.aspx")
        use(client.execute(get)){ response=>
            //LOG.info(s"request https://svnbackoffice.net/SVNBOUpdate.aspx response ${EntityUtils.toString(response.getEntity)}")
            LOG.info("Login success!")
            Option(client)
        }
    }

    def buildLoginRequest(doc:Document):HttpUriRequest = {
        val builder = RequestBuilder.post("https://svnbackoffice.net/SVNLogin.aspx")
        builder.addParameter("__EVENTTARGET","")
        builder.addParameter("__EVENTARGUMENT","")
        builder.addParameter("__VIEWSTATE", doc.select("#__VIEWSTATE").get(0).attr("value"))
        builder.addParameter("__VIEWSTATEGENERATOR", doc.select("#__VIEWSTATEGENERATOR").get(0).attr("value"))
        builder.addParameter("__EVENTVALIDATION", doc.select("#__EVENTVALIDATION").get(0).attr("value"))
        builder.addParameter("ctl00$ContentPlaceHolder1$txtUserName",userName)
        builder.addParameter("ctl00$ContentPlaceHolder1$txtPassword",password)
        builder.addParameter("ctl00$ContentPlaceHolder1$btnLogin","Login")
        if(doc.select("#BDC_VCID_c_svnlogin_ctl00_contentplaceholder1_examplecaptcha").size()>0) {
            builder.addParameter("BDC_VCID_c_svnlogin_ctl00_contentplaceholder1_examplecaptcha",
                doc.select("#BDC_VCID_c_svnlogin_ctl00_contentplaceholder1_examplecaptcha").get(0).attr("value"))
            builder.addParameter("BDC_BackWorkaround_c_svnlogin_ctl00_contentplaceholder1_examplecaptcha",
                doc.select("#BDC_BackWorkaround_c_svnlogin_ctl00_contentplaceholder1_examplecaptcha").get(0).attr("value"))
            builder.addParameter("ctl00$ContentPlaceHolder1$CaptchaCodeTextBox",recognize.recognize(doc).result)
        }
        builder.build()
    }

    def requestLoginPage(client:CloseableHttpClient):Document = {
        val get  = new HttpGet("https://svnbackoffice.net/SVNLogin.aspx")
        use(client.execute(get)){ response=>
            val resultString  = EntityUtils.toString(response.getEntity)
            Jsoup.parse(resultString,get.getURI.toString)
        }
    }

    def logOut(client:CloseableHttpClient):Unit={
        val get = new HttpGet("https://svnbackoffice.net/SVNBOLogout.aspx")
        val doc =use(client.execute(get)){ response=>
            val result = EntityUtils.toString(response.getEntity)
             Jsoup.parse(result)
        }
        val request  = RequestBuilder
          .post("https://svnbackoffice.net/SVNBOLogout.aspx")
          .addParameter("__VIEWSTATE",doc.select("#__VIEWSTATE").get(0).attr("value"))
          .addParameter("__VIEWSTATEGENERATOR",doc.select("#__VIEWSTATEGENERATOR").get(0).attr("value"))
          .addParameter("__EVENTVALIDATION",doc.select("#__EVENTVALIDATION").get(0).attr("value"))
          .addParameter("ctl00$ContentPlaceHolder1$btnYes","Yes")
          .build()
        use(client.execute(request)){response=>
            EntityUtils.toString(response.getEntity)
        }
    }

}

object Login{
    val LOG = LoggerFactory.getLogger(classOf[Login])
}
