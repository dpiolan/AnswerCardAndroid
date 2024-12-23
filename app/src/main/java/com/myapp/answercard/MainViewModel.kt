package com.myapp.answercard

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.myapp.answercard.data.ConfigData
import com.myapp.answercard.database.SqlHelper

class MainViewModel():ViewModel() {

    private var fragments:MutableList<Fragment> = mutableListOf()

    private var fragmentLayout: Int = 0
    private var activate: AppCompatActivity? = null

    private val sqlHelper:SqlHelper by lazy {
        SqlHelper(activate as Context,"AnswerCards",null,1)
    }


    private lateinit var configData: ConfigData
    fun initFragmentsManger(activate:AppCompatActivity,fragmentLayout:Int){
        this.activate = activate
        this.fragmentLayout = fragmentLayout
    }

    fun newFreshFragment(){
        if (fragments.isEmpty()) return
        val transaction = activate?.supportFragmentManager?.beginTransaction()
        transaction?:return
        transaction.add(this.fragmentLayout,fragments.last()).commit()

    }

    fun getConfigData(): ConfigData {
        return configData
    }

    fun <T:Fragment> popFragment():T?{
        val frag:T? = fragments.removeLastOrNull() as? T?
        val transaction = activate?.supportFragmentManager?.beginTransaction()
        transaction?:return null
        transaction.setCustomAnimations(R.anim.pop_enter,R.anim.pop_exit)
        if(frag != null){
            transaction.remove(frag)
            if (fragments.isEmpty()) throw Error("Can't PopFragment")
            transaction.add(this.fragmentLayout,fragments.last())
        }
        transaction.commit()
        return frag
    }

    fun <T:Fragment> pushFragment(fragment:T){
        val transaction = activate?.supportFragmentManager?.beginTransaction()
        transaction?:return
        transaction.setCustomAnimations(R.anim.push_enter,R.anim.push_exit)
        if (!fragments.isEmpty()) {
            val frag: T = fragments.last() as T
            transaction.remove(frag)
        }
        transaction.add(this.fragmentLayout,fragment)
        transaction.commit()
        fragments.add(fragment)
    }


    fun <T:Fragment>getLastFragment():T?{
        if (fragments.isEmpty()){
            return null
        }
        else{
            return fragments.last() as T
        }
    }

}