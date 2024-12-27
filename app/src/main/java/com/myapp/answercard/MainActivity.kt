package com.myapp.answercard

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.provider.ContactsContract.Data
import android.view.KeyEvent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.myapp.answercard.service.DataService

class MainActivity : AppCompatActivity() {

    val viewModel:MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel.initFragmentsManger(this,R.id.main)
        if (viewModel.getLastFragment<Fragment>() == null) {
            viewModel.pushFragment(MainFragment(this))
        }else{
            viewModel.newFreshFragment()
        }
        viewModel.initService(this)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (viewModel.getLastFragment<Fragment>() != null){
                viewModel.popFragment<Fragment>()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}