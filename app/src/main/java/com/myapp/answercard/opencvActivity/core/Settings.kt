package com.myapp.answercard.opencvActivity.core

import java.io.FileInputStream

data class Settings private constructor(
    val version:String
){

    companion object{
        private var INSTANCE:Settings? = null
        fun getInstance():Settings{
            if (INSTANCE == null){
                INSTANCE = Settings("0.1")
            }
            return INSTANCE!!
        }
    }

}
