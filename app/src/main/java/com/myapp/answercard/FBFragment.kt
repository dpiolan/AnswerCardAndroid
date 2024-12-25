package com.myapp.answercard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.myapp.answercard.data.ConfigData
import kotlin.math.min


class FBFragment(context:MainActivity):Fragment() {

    private var configData: ConfigData = ConfigData("",false,null,false,false)
    private val mainActivity = context

    private lateinit var backReturn:View
    private lateinit var nameIdInput:EditText
    private lateinit var isAnswersInput:CheckBox
    private lateinit var secondInput:View
    private lateinit var answersInput:EditText
    private lateinit var isStudentNum:CheckBox
    private lateinit var isDataBase:CheckBox
    private lateinit var nextButton:Button
    private lateinit var databaseView:View
    private lateinit var nameIDInputTip:LinearLayout

    private fun refreshLayout(){
        //根据configData更新layout
        nameIdInput.setText(configData.nameID)
        isAnswersInput.isChecked = configData.isAnswer
        secondInput.visibility = if (configData.isAnswer) View.VISIBLE else View.GONE
        answersInput.setText(configData.answers)
        isStudentNum.isChecked = configData.isStudentNum
        isDataBase.isChecked = configData.isDataBase
        databaseView.visibility = if (configData.isDataBase) View.VISIBLE else View.GONE
    }

    private class SelectItem(context:Context):androidx.appcompat.widget.AppCompatTextView(context){
        private lateinit var configData: ConfigData
        constructor(context: Context,configData: ConfigData,fragment: FBFragment):this(context){
            this.configData = configData
            this.setText(configData.nameID)
            this.visibility = View.GONE
            this.setOnClickListener{
                fragment.configData.copy(this.configData)
                fragment.refreshLayout()
            }
        }

        fun updateConfigData(configData: ConfigData){
            this.configData.copy(configData)
            this.setText(this.configData.nameID)
        }
    }

    private fun mid(s:Int,a:Int,b:Int):Int{
        if (s > b) return b
        if (s < a) return a
        return s
    }

    private fun changeNameIdInputTipContent(listConfigData:List<ConfigData>){

        val size = mid(listConfigData.size-1,-1,4)
        for (i in 0..4){
            if(i <= size) (nameIDInputTip.getChildAt(i) as SelectItem).updateConfigData(listConfigData[i])
            else nameIDInputTip.getChildAt(i).visibility = View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fb_main,container,false)
        val v = view.findViewById<View>(R.id.FB_main)
        nameIdInput = v.findViewById(R.id.FB_idInput)
        isAnswersInput = v.findViewById(R.id.FB_isAnswersInput)
        secondInput = v.findViewById(R.id.FB_secondInput)
        answersInput = v.findViewById(R.id.FB_answersInput)
        isStudentNum = v.findViewById(R.id.FB_isStudentNum)
        isDataBase = v.findViewById(R.id.FB_isDataBase)
        backReturn = v.findViewById(R.id.back_return)
        nextButton = v.findViewById(R.id.FB_nextButton)
        databaseView = v.findViewById(R.id.view_ndatabase)
        nameIDInputTip = v.findViewById(R.id.FB_idInputTip)
        bindAct()
        return view
    }

    private fun bindAct(){

        backReturn.setOnClickListener{
            mainActivity.viewModel.popFragment<FBFragment>()
        }

        isDataBase.setOnCheckedChangeListener{_,b->
            configData.isDataBase = b
            if (b){
                databaseView.visibility = View.VISIBLE
            }else{
                databaseView.visibility = View.GONE
            }
        }

        nameIdInput.doOnTextChanged { text, start, before, count ->
            configData.nameID = text.toString()
            this.mainActivity.viewModel.dataService?.findAllConfigData(this.configData){
                listConfigData->
                this.changeNameIdInputTipContent(listConfigData)
            }
        }

        nameIdInput.setOnFocusChangeListener { view, b ->
            if(b){
                nameIDInputTip.visibility = View.VISIBLE
            }else{
                nameIDInputTip.visibility = View.GONE
            }

        }

        isAnswersInput.setOnCheckedChangeListener{_,isChecked:Boolean->
            configData.isAnswer = isChecked
            if(isChecked){
                secondInput.visibility = View.VISIBLE
            }else{
                secondInput.visibility = View.GONE
            }
        }

        answersInput.doOnTextChanged{text,start,before,count->
            configData.answers = text.toString()
        }

        isStudentNum.setOnCheckedChangeListener { compoundButton, b ->
            configData.isStudentNum = b
        }

        for (i in 0..4){
            nameIDInputTip.addView(
                SelectItem(this.mainActivity,this.configData,this)
            )
        }


    }

}