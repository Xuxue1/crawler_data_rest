package com.xuxue.crawler.util

/**
  * Created by Administrator on 2017/7/15 0015.
  */
class Recode(val date:String,
             val store:String,
             val address:String,
             val online:String,
             val creditCard:String,
             val last4Digits:String,
             val subtotal:String,
             val receiptCredit:String){

    override def toString = s"Recode($date, $store, $address, $online, $creditCard, $last4Digits, $subtotal, $receiptCredit)"
}
