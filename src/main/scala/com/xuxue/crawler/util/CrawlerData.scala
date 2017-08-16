package com.xuxue.crawler.util

import java.util

import org.apache.http.client.methods.{CloseableHttpResponse, HttpGet, HttpUriRequest}
import org.apache.http.impl.client.CloseableHttpClient
import org.slf4j.LoggerFactory
import com.xuxue.crawler.util.Loan.use
import org.apache.http.client.config.RequestConfig
import org.apache.http.util.EntityUtils
import org.jsoup.Jsoup

import collection.JavaConversions._

/**
  * @author xuxue
  */
class CrawlerData(val client:CloseableHttpClient) {
    val LOG =  CrawlerData.LOG
    val config = RequestConfig.custom().setConnectionRequestTimeout(10000)
          .setSocketTimeout(10000)
          .setConnectTimeout(10000).build()

    def execute(httpUriRequest: HttpUriRequest,times:Int):CloseableHttpResponse={
        try{
            client.execute(httpUriRequest)
        }catch {
            case ex:Throwable => {
                LOG.warn(s"request ${httpUriRequest.getURI.toString}",ex)
                if (times < 5) execute(httpUriRequest, times + 1)
                else throw new Exception
            }
        }
    }

    def crawlerXuFeiDate():String = {
        val get = new HttpGet("https://svnbackoffice.net/SVNBOProfileV2.aspx")
        get.setConfig(config)
        use(execute(get,0)){ response=>
            val result = EntityUtils.toString(response.getEntity)
            val doc = Jsoup.parse(result)
            doc.select("#ctl00_ContentPlaceHolder1_lblRenewalDate").text()
        }
    }

    def crawlerBangDianDate():String = {
        val get = new HttpGet("https://svnbackoffice.net/SVNBOGSPCashBack.aspx?anchor=cashback")
        get.setConfig(config)
        use(execute(get,0)){ response=>
            val result = EntityUtils.toString(response.getEntity)
            val doc = Jsoup.parse(result)
            doc.select("#ctl00_ContentPlaceHolder1_lblRegistrationDate").text()
        }
    }

    def crawlerShuHuiDate():String = {
        val get = new HttpGet("https://svnbackoffice.net/SVNBOCashBackWallet.aspx")
        get.setConfig(config)
        use(execute(get,0)){ response=>
            val result = EntityUtils.toString(response.getEntity)
            val doc = Jsoup.parse(result)
            doc.select("#ctl00_ContentPlaceHolder1_lblNextRedemptionDate").text().replace("Next Online Redemption Date:","")
        }
    }

    def crawlerXiaoFeiJilu():java.util.List[Recode] = {
        val get = new HttpGet("https://svnbackoffice.net/SVNBOCashBackRedemptionView.aspx")
        get.setConfig(config)
        use(execute(get,0)){ response=>
            val result = EntityUtils.toString(response.getEntity)
            val doc = Jsoup.parse(result)
            val elements = doc.select("#ctl00_ContentPlaceHolder1_GridView1 > tbody> tr")
            if(elements.size() == 1) return new util.ArrayList[Recode]();
            elements.map(element=>{
                 new Recode(
                        element.child(0).text(),
                        element.child(1).text(),
                        element.child(2).text(),
                        element.child(3).text(),
                        element.child(4).text(),
                        element.child(5).text(),
                        element.child(6).text(),
                        element.child(7).text())
            }).filter(recode=> !recode.date.equals("Date"))
        }
    }

    def crawlerFanXian():java.util.List[FanXian] = {
        val get = new HttpGet("https://svnbackoffice.net/SVNBOEWalletV2.aspx")
        get.setConfig(config)
        use(execute(get,0)){ response=>
            val result = EntityUtils.toString(response.getEntity)
            val doc = Jsoup.parse(result)
            val elements = doc.select("#ctl00_ContentPlaceHolder1_GridView1 > tbody > tr")
            if(elements.size() == 1) return new util.ArrayList[FanXian]()
            elements.map(element=>{
                new FanXian(
                    element.child(0).text(),
                    element.child(1).text(),
                    element.child(2).text(),
                    element.child(3).text(),
                    element.child(4).text())
            }).filter(fanXian=> !fanXian.balance.equals("Balance"))
        }
    }

    def crawlerTuanDui():String = {
        val get = new HttpGet("https://svnbackoffice.net/svnbogenealogy.aspx")
        get.setConfig(config)
        use(execute(get,0)){ response=>
            val result = EntityUtils.toString(response.getEntity)
            val doc = Jsoup.parse(result)
            doc.select("#ctl00_ContentPlaceHolder1_PanelEverything").get(0).child(2).text()
        }
    }

    def crawlerTuiRen():java.util.List[TuiRenMessage] = {
        val get = new HttpGet("https://svnbackoffice.net/SVNBOPersonals.aspx")
        get.setConfig(config)
        use(execute(get,0)){ response=>
            val result = EntityUtils.toString(response.getEntity)
            val doc = Jsoup.parse(result)
            val elements = doc.select("#ctl00_ContentPlaceHolder1_GridView2 > tbody > tr")
            if(elements.size() == 1) return new util.ArrayList[TuiRenMessage]();
            elements.map(element=>{
                new TuiRenMessage(
                    element.child(1).text(),
                    element.child(2).text(),
                    element.child(3).text(),
                    element.child(4).text(),
                    element.child(5).text(),
                    element.child(6).text(),
                    element.child(7).text())
            }).filter(tuiren=> !tuiren.id.equals("Id"))
        }
    }
}


object CrawlerData{
    val LOG = LoggerFactory.getLogger(classOf[CrawlerData])
}
