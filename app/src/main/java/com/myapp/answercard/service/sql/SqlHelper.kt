package com.myapp.answercard.service.sql

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper
import androidx.activity.result.contract.ActivityResultContracts

import com.myapp.answercard.ConfigData
import com.myapp.answercard.StudentAnswers

class SqlHelper(context:Context,name:String,factory:CursorFactory,version:Int):SQLiteOpenHelper(context,name,factory,version) {

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
        writableDatabase.insert(
            ConfigTableName,
            null,
            configDataCastToContentValues(configData)
        )
        writableDatabase.execSQL(
            "CREATE TABLE ${configData.nameID}(" +
                    "studentID Text," +
                    "selects CHAR(${configData.selectsNum}))",
        )
    }

    fun insertAnswer(configData: ConfigData,studentAnswers: StudentAnswers){
        writableDatabase.insert(
            configData.nameID,
            null,
            studentAnswersCastToContentValues(studentAnswers)
        )
    }

    fun findAllConfigData(configData:ConfigData):MutableList<ConfigData>{
        val cursor = readableDatabase.query(
            ConfigTableName,
            null,
            "nameID=?",
            arrayOf("%${configData.nameID}%"),
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
}