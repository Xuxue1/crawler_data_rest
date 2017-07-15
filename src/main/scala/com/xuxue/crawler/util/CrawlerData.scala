package com.xuxue.crawler.util

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.CloseableHttpClient
import org.slf4j.LoggerFactory
import com.xuxue.crawler.util.Loan.use
import org.apache.http.util.EntityUtils
import org.jsoup.Jsoup

/**
  * @author xuxue
  */
class CrawlerData(val client:CloseableHttpClient) {
    val LOG =  CrawlerData.LOG


    def crawlerXuFeiDate():String = {
        val get = new HttpGet("https://svnbackoffice.net/SVNBOProfileV2.aspx")
        use(client.execute(get)){ response=>
            val result = EntityUtils.toString(response.getEntity)
            val doc = Jsoup.parse(result)
            doc.select("#ctl00_ContentPlaceHolder1_lblRenewalDate").get(0).text()
        }
    }

    def crawlerBangDianDate():String = {
        null
    }

    def crawlerShuHuiDate():String = {
        null
    }

    def crawlerXiaoFeiJilu():String = {
        null
    }

    def crawlerFanXian():String = {
        null
    }

    def crawlerTuanDui():String = {
        null
    }

    def crawlerTuiRen():String = {
        null
    }

}


object CrawlerData{
    val LOG = LoggerFactory.getLogger(classOf[CrawlerData])
}
