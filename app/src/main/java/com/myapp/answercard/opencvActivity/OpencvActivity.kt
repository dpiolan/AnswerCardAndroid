package com.myapp.answercard.opencvActivity

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.myapp.answercard.R
import com.myapp.answercard.opencvActivity.core.Settings
import org.opencv.android.CameraActivity
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.OpenCVLoader
import java.util.Collections

private val TAG = "OpencvActivity"

class OpencvActivity : CameraActivity(){

    val myCameraViewManger:MyCameraViewManger by lazy {
        MyCameraViewManger.initInstance (this,R.id.camera_surface)
        MyCameraViewManger.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opencv)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if(OpenCVLoader.initLocal()){
            Log.d(TAG,"opencv init success")
        }else{
            Toast.makeText(this,"OpencvLoader.InitLocal error",Toast.LENGTH_SHORT)
            return
        }
    }

    override protected fun getCameraViewList(): MutableList<out CameraBridgeViewBase> {
        return Collections.singletonList(myCameraViewManger.getCameraBridgeViewBase()).filterNotNull().toMutableList()
    }

    override fun onDestroy() {
        super.onDestroy()
        MyCameraViewManger.releaseInstance()
        Settings.releaseInstance()
    }

    override fun onPause() {
        super.onPause()
        myCameraViewManger.onPause()
    }

    override fun onResume() {
        super.onResume()
        myCameraViewManger.onResume()
    }

}