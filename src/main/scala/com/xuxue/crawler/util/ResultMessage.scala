package com.xuxue.crawler.util

/**
  * Created by Administrator on 2017/7/15 0015.
  */
class ResultMessage(
                     val xuFeiDate:String,
                     val bangDianDate:String,
                     val shuHuiDate:String,
                     val xiaoFeiJilu:java.util.List[Recode],
                     val fanXian:java.util.List[FanXian],
                     val tuanDui:String,
                     val tuiRen:java.util.List[TuiRenMessage]){

    override def toString = s"ResultMessage($xuFeiDate, $bangDianDate, $shuHuiDate, $xiaoFeiJilu, $fanXian, $tuanDui, $tuiRen)"
}
