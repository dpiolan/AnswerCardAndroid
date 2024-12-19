package com.myapp.answercard

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment


class FBFragment(context:Recognition):Fragment() {

    private var configData: ConfigData = ConfigData("",false,null,false,false)
    private val context = context

    private lateinit var nameIdInput:EditText
    private lateinit var isAnswersInput:CheckBox
    private lateinit var secondInput:View
    private lateinit var answersInput:EditText
    private lateinit var isStudentNum:CheckBox
    private lateinit var isDataBase:CheckBox

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
        val view = inflater.inflate(R.layout.fb_firstfragment,container,false)
        nameIdInput = view.findViewById(R.id.FB_idInput)
        isAnswersInput = view.findViewById(R.id.FB_isAnswersInput)
        secondInput = view.findViewById(R.id.FB_secondInput)
        answersInput = view.findViewById(R.id.FB_answersInput)
        isStudentNum = view.findViewById(R.id.FB_isStudentNum)
        isDataBase = view.findViewById(R.id.FB_isDataBase)
        bindAct()
        return view
    }

    private fun bindAct(){


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