package com.myapp.answercard.opencvActivity

import android.app.Activity
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2
import org.opencv.core.Mat
import android.util.Log
import com.myapp.answercard.opencvActivity.core.ProcFindContours

class MyCameraViewManger(context:Activity,frameId:Int):CvCameraViewListener2 {
    private final val TAG = "MyCameraViewManger"
    private val stackFrameCallback:MutableList<ProcFrameCallback> = mutableListOf()
    private val cameraBridgeViewBase = context.findViewById<MyCameraView>(frameId)
    private val context:Activity = context

    interface ProcFrameCallback{
        fun onFrame(inputFrame: Mat):Mat
    }

    init {
        val width = Math.round(1200 / Math.pow(2.0,0.5)).toInt()
        cameraBridgeViewBase.setMaxFrameSize(width, 1200)
        cameraBridgeViewBase.setCvCameraViewListener(this)
        this.addStackFrameCallback(ProcFindContours())

    }

    fun addStackFrameCallback(cv:ProcFrameCallback){
        stackFrameCallback.add(cv)
    }

    fun removeStackFrameCallback(cv:ProcFrameCallback){
        stackFrameCallback.remove(cv)
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
        var mat:Mat = inputFrame?.gray()?:Mat()
        try {
            for (c in stackFrameCallback){
               mat = c.onFrame(mat)
            }
        }catch (e:Exception){
            Log.d(TAG,e.toString())
        }

//        Log.d(TAG,"inputFrameSize width = ${size?.width} height = ${size?.height}")
        return mat
    }

    override fun onCameraViewStopped() {
    }

    override fun onCameraViewStarted(width: Int, height: Int) {

    }

}