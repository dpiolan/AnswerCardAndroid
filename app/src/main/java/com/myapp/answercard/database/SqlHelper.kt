package com.myapp.answercard.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper
import com.myapp.answercard.data.ConfigData
import com.myapp.answercard.data.StudentAnswers
import com.myapp.answercard.data.StudentAnswersWithSource

class SqlHelper private constructor(context:Context,name:String,factory:CursorFactory?,version:Int):SQLiteOpenHelper(context,name,factory,version) {

    private fun configDataCastToContentValues(configData: ConfigData):ContentValues{
        fun boolCastToInt(bool:Boolean):Int{
            if (bool) return 1
            return 0
        }

        val values = ContentValues()
        values.apply {
            put("nameID",configData.nameID)
            put("isAnswer",boolCastToInt(configData.isAnswer))
            put("answers",configData.answers?:"")
            put("isStudentNum",boolCastToInt(configData.isStudentNum))
            put("selectsNum",configData.selectsNum)
        }
        return values
    }

    private fun studentAnswersCastToContentValues(studentAnswers: StudentAnswers):ContentValues{
        val values = ContentValues()
        values.apply {
            put("studentID",studentAnswers.studentID)
            put("answers",studentAnswers.answers.toString())
        }
        return values
    }


    private val ConfigTableName = "ConfigTableName"
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("CREATE TABLE ${ConfigTableName}(nameID TEXT NOT NULL,isAnswer INT2,answers TEXT,isStudentNum INT2, selectsNum INT)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }


    fun CreateSheetByConfigData(configData: ConfigData){

        if (!configData.isDataBase) return
        writableDatabase.insert(
            ConfigTableName,
            null,
            configDataCastToContentValues(configData)
        )
        if (configData.isAnswer){
            writableDatabase.execSQL(
                    "CREATE TABLE ${configData.nameID}(" +
                            "studentID Text," +
                            "answers CHAR)" +
                            "source INT")

        }
        else {
            writableDatabase.execSQL(
                "CREATE TABLE ${configData.nameID}(" +
                        "studentID Text," +
                        "answers CHAR(${configData.selectsNum}))",
            )
        }
    }

    fun insertAnswer(configData: ConfigData, studentAnswers: StudentAnswers){
        writableDatabase.insert(
            configData.nameID,
            null,
            studentAnswersCastToContentValues(studentAnswers)
        )
    }

    fun findAllConfigData(configData: ConfigData):MutableList<ConfigData>{

        val cursor = readableDatabase.query(
            ConfigTableName,
            null,
            "nameID LIKE ",
            arrayOf("${configData.nameID}%"),
            null,
            null,
            null,
            "LIMIT 5"
        )
        fun cast(i:Int):Boolean{
            if(i==1){
                return true
            }
            return false
        }
        val returns = mutableListOf<ConfigData>()
        with(cursor){
         while (moveToNext()){
             val tmpConfigData = ConfigData(configData)
             tmpConfigData.nameID = getString(getColumnIndexOrThrow("nameId"))
             tmpConfigData.isAnswer = cast(getInt(getColumnIndexOrThrow("isAnswer")))
             tmpConfigData.answers = getString(getColumnIndexOrThrow("answers"))
             tmpConfigData.isStudentNum = cast(getInt(getColumnIndexOrThrow("isStudentNum")))
             tmpConfigData.selectsNum = getInt(getColumnIndexOrThrow("selectsNum"))
            returns.add(tmpConfigData)
         }

        }
        return returns
    }

    fun findStudentAnswers(configData: ConfigData, studentID:String): StudentAnswers?{

        val cursor = readableDatabase.query(
            configData.nameID,
            null,
            "studentID = ?",
            arrayOf(studentID),
            null,
            null,
            null,
            "LIMIT 1"
        )
        if (cursor.count == 0){
            return null
        }
        cursor.moveToFirst()
        return StudentAnswers(
            studentID,
            cursor.getString(cursor.getColumnIndexOrThrow("answers")).toList()
        )
    }

    fun findStudentAnswersWithSource(configData: ConfigData,studentID:String):StudentAnswersWithSource?{
        val cursor = readableDatabase.query(
            configData.nameID,
            null,
            "studentID = ?",
            arrayOf(studentID),
            null,
            null,
            null,
            "LIMIT 1"
        )
        if (cursor.count == 0){
            return null
        }
        cursor.moveToFirst()
        return StudentAnswersWithSource(
            studentID,
            cursor.getString(cursor.getColumnIndexOrThrow("answers")).toList(),
            cursor.getInt(cursor.getColumnIndexOrThrow("source"))
        )
    }

    fun updateConfigData(configData: ConfigData){
        writableDatabase.update(
            ConfigTableName,
            configDataCastToContentValues(configData),
            "nameID = ?",
            arrayOf(configData.nameID)
        )
    }

    fun deleteConfigData(configData: ConfigData){
        writableDatabase.delete(
            ConfigTableName,
            "nameID = ?",
            arrayOf(configData.nameID)
        )
        writableDatabase.execSQL("DROP TABLE ${configData.nameID}")
    }

    companion object{
        private var INSTANCE:SqlHelper? = null

        fun initInstance(context:Context,name:String,factory:CursorFactory?,version:Int):SqlHelper{
            if(INSTANCE != null) return INSTANCE as SqlHelper
            INSTANCE = SqlHelper(context,name,factory,version)
            return INSTANCE as SqlHelper

        }
        fun getInstance():SqlHelper{
            return INSTANCE?:throw Error("The SqlHelper Instance have not init")
        }
    }
}