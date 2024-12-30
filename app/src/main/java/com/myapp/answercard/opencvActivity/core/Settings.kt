package com.myapp.answercard.opencvActivity.core

import org.opencv.core.Size

data class Settings private constructor(
    val version:String
){
    var imgSize = Size(480.0,720.0)
    companion object{
        private var INSTANCE:Settings? = null
        fun getInstance():Settings{
            if (INSTANCE == null){
                INSTANCE = Settings("0.1")
            }
            return INSTANCE!!
        }

        fun releaseInstance(){
            INSTANCE = null
        }

    }

}
