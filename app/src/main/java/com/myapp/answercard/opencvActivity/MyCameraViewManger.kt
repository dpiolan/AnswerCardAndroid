package com.myapp.answercard.opencvActivity

import android.app.Activity
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2
import org.opencv.core.Mat
import android.util.Log
import android.widget.ImageView
import com.myapp.answercard.R
import com.myapp.answercard.opencvActivity.core.ProcFindContours
import kotlin.io.path.fileVisitor

class MyCameraViewManger private constructor(context:Activity,frameId:Int):CvCameraViewListener2{
    private final val TAG = "MyCameraViewManger"
    private val stackFrameCallback:MutableList<ProcFrameCallback> = mutableListOf()
    private val cameraBridgeViewBase = context.findViewById<MyCameraView>(frameId)
    private val context:Activity = context
    private lateinit var procFindContours: ProcFindContours

    enum class STATUS(val value:Int){

        USER_USING(0b1),
        PROC_TRANSLATE01(0b10),
        PROC_HAVEGETANSWERS(0b100),
        PROC_HAVEGETSTUDENTID(0b1000),
        PROC_HAVEGETSOURCE(0b10000),
    }

    class StatusManger{
        private var status:Int = 0

        fun addStatus(status:STATUS){
            this.status = this.status or status.value
        }

        fun removeStatus(status:STATUS){
            this.status =  ((this.status and status.value).inv()) and this.status
        }

        fun getStatus(status:STATUS):Boolean{
            return  (this.status and status.value) == status.value
        }
    }

    var status = StatusManger()

    interface ProcFrameCallback{
        fun onFrame(inputFrame: Mat):Mat
    }

    init {
        val width = Math.round(1200 / Math.pow(2.0,0.5)).toInt()
        cameraBridgeViewBase.setMaxFrameSize(width, 1200)
        cameraBridgeViewBase.setCvCameraViewListener(this)
        procFindContours = ProcFindContours()
        status.addStatus(STATUS.USER_USING)

        with(context as OpencvActivity){
            context.findViewById<ImageView>(R.id.camera_button).setOnClickListener{
                status.removeStatus(STATUS.USER_USING)
            }
        }

        this.addStackFrameCallback(procFindContours)
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
        var mat:Mat = inputFrame?.rgba()?:Mat()
        try {
            for (c in stackFrameCallback){
               mat = c.onFrame(mat)
            }
        }catch (e:Exception){
            Log.d(TAG,e.toString())
        }
        return mat
    }

    override fun onCameraViewStopped() {
    }

    override fun onCameraViewStarted(width: Int, height: Int) {
    }

    companion object{
        private var INSTANCE:MyCameraViewManger? = null
        fun initInstance(context: Activity,frameId: Int){
            INSTANCE = MyCameraViewManger(context,frameId)
        }
        fun getInstance():MyCameraViewManger{
            return INSTANCE!!
        }
        fun releaseInstance(){
            INSTANCE = null
        }
    }
}