package com.myapp.answercard

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel

class FBViewModel():ViewModel() {

    private var fragments:MutableList<Fragment> = mutableListOf()
    private lateinit var transaction: FragmentTransaction
    private var fragmentLayout: Int = 0

    private lateinit var configData:ConfigData
    fun initConfigData(nameID:String):ConfigData{
        configData = VarMangerConfig.GetInstance().getConfig(nameID)
        return configData
    }
    fun initFragmentsManger(transaction: FragmentTransaction,fragmentLayout:Int){
        this.transaction = transaction
        this.fragmentLayout = fragmentLayout
    }

    fun getConfigData():ConfigData{
        return configData
    }

    fun <T:Fragment> popFragment():T?{
        val frag:T? = fragments.removeLastOrNull() as T
        if(frag != null){
            this.transaction.remove(frag).commit()
        }
        return frag
    }

    fun <T:Fragment> pushFragment(fragment:T){
        fragments.add(fragment)
        this.transaction.add(this.fragmentLayout,fragment).commit()
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