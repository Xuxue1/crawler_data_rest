package com.xuxue.crawler.util

import com.xuxue.crawler.frame.DaMaTuConfig
import org.junit.{Before, Test}

/**
  * Created by nt on 2017/7/14.
  */
class TestLogin {

    var login:Login = _

    var recognize:Recognize = _

    @Before
    def before():Unit={
        val config = new DaMaTuConfig
        config.setAppID("48120")
        config.setAppKey("38c63e94d371eba7b04c15d5f5a754f5")
        config.setPassword("6998490")
        config.setRquestUrl("http://api.dama2.com:7766/app/d2File")
        config.setUser("xuxu2017")
        recognize = new Recognize
        recognize.daMaTuConfig = config
        login = new Login("qianxianfeng13","qian123",null,recognize)
    }

    @Test
    def test():Unit = {
        val client = login.doLogin()
    }

}
