package com.myapp.answercard.service.sql

import android.os.ResultReceiver
import android.os.Bundle
import android.os.Handler


class IntentResultReceiver(handler:Handler):ResultReceiver(handler) {
    private var receiver:Receivar? = null

    interface Receivar{
        fun onReceiveResult(resultCode:Int,resultData:Bundle?)
    }

    fun setReceiver(receiver: Receivar){
        this.receiver = receiver
    }

    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
        receiver?.onReceiveResult(resultCode,resultData)
    }
}