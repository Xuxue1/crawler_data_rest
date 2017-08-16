package com.xuxue.crawler

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.util.TimeZone
/**
  * Created by nt on 2017/7/13.
  */
@SpringBootApplication
class Main
object Main{
    {
        TimeZone.setDefault(TimeZone.getTimeZone("PRC"))
        println("Hello")
    }
    def main(args: Array[String]): Unit = {
        SpringApplication.run(classOf[Main])
    }
}
