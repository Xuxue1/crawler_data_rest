package com.xuxue.crawler.util

/**
  * Created by Administrator on 2017/7/15 0015.
  */
class FanXian(val date:String,
              val description:String,
              val credit:String,
              val debit:String,
              val balance:String){

    override def toString = s"FanXian($date, $description, $credit, $debit, $balance)"
}
