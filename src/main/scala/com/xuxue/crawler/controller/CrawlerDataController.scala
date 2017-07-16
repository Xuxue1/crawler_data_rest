package com.xuxue.crawler.controller

import com.google.gson.Gson
import com.xuxue.crawler.frame.DaMaTuConfig
import com.xuxue.crawler.util.{CrawlerData, Login, Recognize, ResultMessage}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, RequestParam, RestController}

/**
  * @author xuxue
  */
@RestController
@RequestMapping(Array("/api/crawler"))
class CrawlerDataController {
    val LOG = CrawlerDataController.LOG

    @Autowired
    var daMaTuConfig:DaMaTuConfig = _


    @Autowired
    var recognize:Recognize = _

    val G = new Gson()

    @RequestMapping(value=Array("/ping"),method = Array(RequestMethod.GET,RequestMethod.POST))
    def test():String = {
        "OK"
    }

    @RequestMapping(value = Array("/crawler"),method = Array(RequestMethod.GET,RequestMethod.POST))
    def crawler(@RequestParam(value = "userName", required = true) userName: String,
                @RequestParam(value = "password", required = true) password: String,
                @RequestParam(value = "proxy", required = false) proxy:String):String = {
        val login = new Login(userName,password,proxy,recognize)
        val client = login.doLogin()
        if(client.isEmpty){
            return "log error";
        }
        val crawlerData = new CrawlerData(client.get)
        val xuFeiDate  = crawlerData.crawlerXuFeiDate()
        LOG.info(s"crawler xuFeiDate $xuFeiDate")
        Thread.sleep(1500)
        val bangDianDate = crawlerData.crawlerBangDianDate()
        LOG.info(s"crawler bangDian $bangDianDate")
        Thread.sleep(1500)
        val shuHuiDate = crawlerData.crawlerShuHuiDate()
        LOG.info(s"crawler shuHuiDate $shuHuiDate")
        Thread.sleep(1500)
        val xiaoFeiJilu = crawlerData.crawlerXiaoFeiJilu()
        LOG.info(s"crawler xiaoFeiJilu $xiaoFeiJilu")
        Thread.sleep(1500)
        val fanXian = crawlerData.crawlerFanXian()
        LOG.info(s"crawler fanXian $fanXian")
        Thread.sleep(1500)
        val tuanDui = crawlerData.crawlerTuanDui()
        LOG.info(s"crawler tuanDui $tuanDui")
        Thread.sleep(1500)
        val tuiRen = crawlerData.crawlerTuiRen()
        LOG.info(s"crawler tuiRen $tuiRen")
        val resultMessage = new ResultMessage(xuFeiDate,bangDianDate,shuHuiDate,xiaoFeiJilu,fanXian,tuanDui,tuiRen)
        LOG.info(s"result message ${G.toJson(resultMessage)}")
        login.logOut(client.get)
        LOG.info("log out success!")
        G.toJson(resultMessage)
    }

}

object CrawlerDataController{
    val LOG = LoggerFactory.getLogger(classOf[CrawlerDataController])
}