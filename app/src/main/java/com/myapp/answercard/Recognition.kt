package com.myapp.answercard

import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class Recognition : AppCompatActivity() {

//    private lateinit var transaction: FragmentTransaction
//
//    val fbViewModel:FBViewModel by lazy {
//        ViewModelProvider(this)[FBViewModel::class.java]
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_recognition)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.FB_main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {





        return super.onKeyDown(keyCode, event)
    }
}