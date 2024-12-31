package com.myapp.answercard.opencvActivity

import com.myapp.answercard.opencvActivity.core.Template
import org.opencv.core.Size
import java.io.FileInputStream

data class Settings private constructor(
    val version:String
){
    var imgSize = Size(480.0,720.0)

    lateinit var template: MutableList<Template>

    fun setTemplateByFile(fileName:String){
    }

    companion object{
        private var INSTANCE: Settings? = null
        fun getInstance(): Settings {
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
