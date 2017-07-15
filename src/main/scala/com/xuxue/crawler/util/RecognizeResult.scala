package com.xuxue.crawler.util

/**
  * Created by nt on 2017/7/13.
  */
class RecognizeResult(val ret:Int,val id:Int,val result:String,val sign:String){

    override def toString = s"RecognizeResult($ret, $id, $result, $sign)"
}
