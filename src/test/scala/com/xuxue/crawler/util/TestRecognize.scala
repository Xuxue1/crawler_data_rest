package com.xuxue.crawler.util

import com.xuxue.crawler.frame.DaMaTuConfig
import org.junit.{Before, Test}
import com.xuxue.crawler.util.Loan.use
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.jsoup.Jsoup

/**
  *
  * @author xuxue
  */
class TestRecognize {

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
    }

    @Test
    def test():Unit = {
        use(HttpClients.createDefault()){ client=>
            val get = new HttpGet("https://svnbackoffice.net/svnlogin.aspx")
            use(client.execute(get)){ response =>
                val result = EntityUtils.toString(response.getEntity)
                val doc = Jsoup.parse(result)
                println(recognize.recognize(doc))
            }
        }
    }
}
