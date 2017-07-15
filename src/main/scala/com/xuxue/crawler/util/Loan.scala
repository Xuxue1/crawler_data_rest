package com.xuxue.crawler.util

/**
  * Created by nt on 2017/7/13.
  */
object Loan {
    def use[T<:{def close();},R](resource:T)(func:T=>R)={
        try{
            func(resource)
        }finally {
            resource.close()
        }
    }
}
