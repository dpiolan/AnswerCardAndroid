package com.myapp.answercard.opencvActivity

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.myapp.answercard.R
import org.opencv.android.CameraActivity
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2
import org.opencv.android.OpenCVLoader
import org.opencv.core.Mat
import java.util.Collections

private val TAG = "OpencvActivity"

class opencvActivity : CameraActivity(),CvCameraViewListener2{

    private var mOpenCvCameraView:CameraBridgeViewBase? = null

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
            Log.d(TAG,"opencv init fail")
            return
        }
        mOpenCvCameraView =findViewById(R.id.camera_surface)
        mOpenCvCameraView?.setCvCameraViewListener(this)
    }

    override protected fun getCameraViewList(): MutableList<out CameraBridgeViewBase> {
        return Collections.singletonList(mOpenCvCameraView).filterNotNull().toMutableList()
    }

    override fun onPause() {
        super.onPause()
        mOpenCvCameraView?.disableView()
    }

    override fun onResume() {
        super.onResume()
        mOpenCvCameraView?.enableView()
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame?): Mat {
        return inputFrame?.rgba()?:Mat()
    }

    override fun onCameraViewStopped() {
    }

    override fun onCameraViewStarted(width: Int, height: Int) {
    }


}