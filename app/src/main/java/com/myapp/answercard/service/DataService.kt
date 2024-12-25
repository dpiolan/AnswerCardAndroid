package com.myapp.answercard.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import com.myapp.answercard.R
import com.myapp.answercard.data.ConfigData
import com.myapp.answercard.data.StudentAnswers
import com.myapp.answercard.database.SqlHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Stack

class DataService : Service() {
    /*
    * 功能1: 全局ConfigData存储和更改
    * 功能2: 数据库操作
    * 功能3: 基础的内部错误栈
    * */

    private val errorStack = Stack<Exception>()
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)
    val configData:ConfigData = ConfigData("",false,null,false,false)
    inner class LocalBinder : Binder(){
        fun getService():DataService = this@DataService
    }
    private val binder = LocalBinder()

    fun changeConfigData(newConfigData: ConfigData){
        configData.copy(newConfigData)
    }

    private val sqlHelper by lazy {
        SqlHelper.initInstance(
            this,
            resources.getString(R.string.DataBase_name),
            null,
            1
        )
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onCreate() {
        super.onCreate()
    }

    fun CreateSheetByConfigData(configData: ConfigData,callBack: (Boolean)->Unit = {}){
        scope.launch {
            val b =  withContext(Dispatchers.IO){
                try {
                    sqlHelper.CreateSheetByConfigData(configData)
                    return@withContext true
                }catch (e:Exception){
                    errorStack.push(e)
                    return@withContext false
                }
            }
            callBack(b)
        }
    }

    fun insertAnswer(configData: ConfigData, studentAnswers: StudentAnswers,CallBack:(Boolean)->Unit = {}){
        scope.launch {
            val b = withContext(Dispatchers.IO){
                try {
                    sqlHelper.insertAnswer(configData,studentAnswers)
                    return@withContext true
                }catch (e:Exception){
                    errorStack.push(e)
                    return@withContext false
                }
            }
        }
    }

    fun findAllConfigData(configData: ConfigData,CallBack: (List<ConfigData>) -> Unit = {}){
        scope.launch {
            val b = withContext(Dispatchers.IO ){
                try {
                    return@withContext sqlHelper.findAllConfigData(configData)

                }catch (e:Exception){
                    errorStack.push(e)
                    return@withContext listOf<ConfigData>()
                }
            }
            CallBack(b)
        }
    }

    fun findStudentAnswers(configData: ConfigData,studentID:String,CallBack:(StudentAnswers?)->Unit = {}){
        scope.launch {
            val b = withContext(Dispatchers.IO){
                try {
                    return@withContext sqlHelper.findStudentAnswers(configData,studentID)
                }catch (e:Exception){
                    errorStack.push(e)
                    return@withContext null
                }
            }
            CallBack(b)
        }
    }

    fun updateConfigData(configData: ConfigData,CallBack:(Boolean)->Unit = {}){
        scope.launch {
            val b = withContext(Dispatchers.IO){
                try {
                    sqlHelper.updateConfigData(configData)
                    return@withContext true
                }catch (e:Exception){
                    errorStack.push(e)
                    return@withContext false
                }
            }
            CallBack(b)
        }
    }

    fun deleteConfigData(configData: ConfigData,CallBack:(Boolean)->Unit = {}){
        scope.launch {
            val b = withContext(Dispatchers.IO){
                try {
                    sqlHelper.deleteConfigData(configData)
                    return@withContext true
                }catch (e:Exception){
                    errorStack.push(e)
                    return@withContext false
                }
            }
            CallBack(b)
        }
    }

    fun getLastError():Exception{
        return errorStack.lastElement()
    }

    companion object{
        fun startService(context: Context,connection: ServiceConnection){
            val intent = Intent(context,DataService::class.java)
            context.bindService(intent,connection,Context.BIND_AUTO_CREATE)
        }
    }
}