package com.myapp.answercard

import com.myapp.answercard.data.ConfigData

class VarMangerConfig {
    val listConfig:MutableList<ConfigData> = mutableListOf()

    public fun getConfig(nameId:String): ConfigData {
        val data = listConfig.find { configData->
            configData.nameID == nameId
        }
        if (data == null){
            val ndata = ConfigData(nameId,false,null,false, false)
            listConfig.add(ndata)
            return ndata
        }
        return data
    }

    companion object{
        private var INSTANCE:VarMangerConfig? = null
        fun GetInstance():VarMangerConfig{
            if (INSTANCE == null){
                INSTANCE = VarMangerConfig()
            }
            return INSTANCE?: throw Error("can't Get")
        }
    }
}