package com.myapp.answercard.opencvActivity

import android.app.Activity
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2
import org.opencv.core.Mat
import android.graphics.Point
import android.util.Log

class MyCameraViewManger(context:Activity,frameId:Int):CvCameraViewListener2 {
    private final val TAG = "MyCameraViewManger"

    private val cameraBridgeViewBase = context.findViewById<CameraBridgeViewBase>(frameId)
    private val context:Activity = context

    init {
        val height = Math.round(1200 / Math.pow(2.0,0.5))
        cameraBridgeViewBase.setMaxFrameSize(height.toInt(), 1200)

        cameraBridgeViewBase.setCvCameraViewListener(this)
    }

    fun onResume(){
        cameraBridgeViewBase.enableView()
    }
    fun onPause(){
        cameraBridgeViewBase.disableView()
    }

    fun getCameraBridgeViewBase():CameraBridgeViewBase{
        return cameraBridgeViewBase
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame?): Mat {
        val size = inputFrame?.rgba()?.size()
        Log.d(TAG,"inputFrameSize width = ${size?.width} height = ${size?.height}")
        return inputFrame?.rgba()?: Mat()
    }

    override fun onCameraViewStopped() {
    }

    override fun onCameraViewStarted(width: Int, height: Int) {

    }

}