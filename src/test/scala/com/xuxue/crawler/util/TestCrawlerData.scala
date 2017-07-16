package com.xuxue.crawler.util

import java.io.PrintWriter
import java.util

import com.xuxue.crawler.frame.DaMaTuConfig
import org.apache.http.client.methods.RequestBuilder
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils



/**
  * Created by Administrator on 2017/7/15 0015.
  */
class TestCrawlerData {

    var login:Login = _

    var recognize:Recognize = _

    var crawlerData:CrawlerData  = _

    //@Before
    def before(): Unit ={
        val config = new DaMaTuConfig
        config.setAppID("48120")
        config.setAppKey("38c63e94d371eba7b04c15d5f5a754f5")
        config.setPassword("6998490")
        config.setRquestUrl("http://api.dama2.com:7766/app/d2File")
        config.setUser("xuxu2017")
        recognize = new Recognize
        recognize.daMaTuConfig = config

        val client = login.doLogin()
        crawlerData = new CrawlerData(client.get)
    }

    //@Test
    def test():Unit = {
        println(crawlerData.crawlerXuFeiDate())
        Thread.sleep(5000)
        println(crawlerData.crawlerBangDianDate())
        Thread.sleep(5000)
        println(crawlerData.crawlerShuHuiDate())
        Thread.sleep(5000)
        val data = crawlerData.crawlerXiaoFeiJilu()
    }

}

object TestCrawlerData{
    import com.xuxue.crawler.util.Loan.use
    def main(args: Array[String]): Unit = {
        val map = new util.HashMap[String,String]()

    }

}
