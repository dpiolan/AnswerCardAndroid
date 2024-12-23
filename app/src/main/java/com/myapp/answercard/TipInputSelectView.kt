package com.myapp.answercard

import android.content.Context
import android.view.View
import android.widget.TextView

class TipInputSelectView(context:Context,_text:String):androidx.appcompat.widget.AppCompatTextView(context) {
    var _text:String? = _text
        set(value:String?) {
            this.text = _text
            field = value
        }
    constructor(context: Context,_text: String, beAddedView: View):this(context,_text){

    }

}