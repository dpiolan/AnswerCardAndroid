package com.myapp.answercard

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment


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

//    private class textWatcher:TextWatcher{
//        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            TODO("Not yet implemented")
//        }
//
//        override fun afterTextChanged(p0: Editable?) {
//            TODO("Not yet implemented")
//        }
//
//        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            TODO("Not yet implemented")
//        }
//    }
//

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
        bindAct()
        return view
    }

    private fun bindAct(){

        backReturn.setOnClickListener{
            mainActivity.viewModel.popFragment<FBFragment>()
        }

        nameIdInput.doOnTextChanged { text, start, before, count ->
            configData.nameID = text.toString()
        }
        isAnswersInput.setOnCheckedChangeListener{_,isChecked:Boolean->
            configData.isAnswer = isChecked
            if(isChecked){
                secondInput.visibility = View.VISIBLE
            }
        }
        answersInput.doOnTextChanged{text,start,before,count->
            configData.answers = text.toString()
        }
        isStudentNum.setOnCheckedChangeListener { compoundButton, b ->
            configData.isStudentNum = b
        }
        isDataBase.setOnCheckedChangeListener{_,b->
            configData.isDataBase = b
        }

    }

}