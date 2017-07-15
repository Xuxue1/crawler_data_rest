package com.xuxue.crawler

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
  * Created by nt on 2017/7/13.
  */
@SpringBootApplication
class Main
object Main{
    def main(args: Array[String]): Unit = {
        SpringApplication.run(classOf[Main])
    }
}
