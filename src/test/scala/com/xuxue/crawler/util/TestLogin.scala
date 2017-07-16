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

    }

    @Test
    def test():Unit = {
        val client = login.doLogin()
    }

}
