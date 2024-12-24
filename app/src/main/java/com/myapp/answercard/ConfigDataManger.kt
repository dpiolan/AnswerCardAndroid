package com.myapp.answercard
import com.myapp.answercard.data.ConfigData
import com.myapp.answercard.database.SqlHelper

class ConfigDataManger private constructor (){

    private val configData = ConfigData("",false,null,false,false)
    private constructor(configData: ConfigData):this(){
        this.configData.copy(configData)
    }

    private suspend fun writeToSql(){
    }

    fun changeConfigData(configData: ConfigData){
        this.configData.copy(configData)
    }

    fun getConfigData():ConfigData{
        return configData
    }

    companion object{
        private var configDataMangerInstance:ConfigDataManger? = null

        fun getInstance(configData: ConfigData):ConfigDataManger{
            if (configDataMangerInstance == null){
                configDataMangerInstance = ConfigDataManger(configData)
            }
            return configDataMangerInstance as ConfigDataManger
        }
        fun releaseInstance(){
            configDataMangerInstance = null
        }
    }

}