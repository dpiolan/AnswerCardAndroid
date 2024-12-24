package com.myapp.answercard.data

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

    fun copy(configData: ConfigData):Unit{
        this.nameID = configData.nameID
        this.isAnswer = configData.isAnswer
        this.answers = configData.answers
        this.isStudentNum = configData.isStudentNum
        this.isDataBase = configData.isDataBase
        this.selectsNum = configData.selectsNum
    }
}