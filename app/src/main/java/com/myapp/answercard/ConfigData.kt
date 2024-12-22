package com.myapp.answercard

data class ConfigData(var nameID:String,
                      var isAnswer:Boolean,
                      var answers:String?,
                      var isStudentNum:Boolean,
                      var isDataBase:Boolean,
                      var selectsNum:Int = 30,
    ){
    constructor(configData: ConfigData):this(
        configData.nameID,
        configData.isAnswer,
        configData.answers,
        configData.isStudentNum,
        configData.isDataBase,
        configData.selectsNum
        )
}