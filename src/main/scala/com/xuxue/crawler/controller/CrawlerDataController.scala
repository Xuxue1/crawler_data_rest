package com.xuxue.crawler.controller

import com.xuxue.crawler.frame.DaMaTuConfig
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, RequestParam, RestController}

/**
  * Created by nt on 2017/7/13.
  */
@RestController
@RequestMapping(Array("/api/crawler"))
class CrawlerDataController {

    @Autowired
    var daMaTuConfig:DaMaTuConfig = _


    @RequestMapping(value=Array("/ping"),method = Array(RequestMethod.GET,RequestMethod.POST))
    def test():String = {
        "OK"
    }

    @RequestMapping(value = Array("/crawler"),method = Array(RequestMethod.GET,RequestMethod.POST))
    def crawler(@RequestParam(value = "userName", required = true) userName: String,
                @RequestParam(value = "password", required = true) password: String,
                @RequestParam(value = "proxy", required = true) proxy:String):String = {
        println(userName)
        println(password)
        println(proxy)
        userName+password+proxy
    }



    def recognizing(url:String):Option[String]={

        null

    }

}

object CrawlerDataController{
    val LOG = LoggerFactory.getLogger(classOf[CrawlerDataController])
}