package com.xuxue.crawler.util

/**
  * Created by Administrator on 2017/7/15 0015.
  */
class TuiRenMessage(
                     val id:String,
                     val username:String,
                     val entryDate:String,
                     val renewalDate:String,
                     val activeStatus:String,
                     val eventReg:String,
                     val rank:String){


    override def toString = s"TuiRenMessage($id, $username, $entryDate, $renewalDate, $activeStatus, $eventReg, $rank)"
}
