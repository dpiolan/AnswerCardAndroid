package com.myapp.answercard.opencvActivity.core

import com.myapp.answercard.opencvActivity.MyCameraViewManger
import com.myapp.answercard.opencvActivity.Settings
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class Questions:MyCameraViewManger.ProcFrameCallback,ProcFindContours.PerspectiveCallback {

    private fun preProcess(inputFrame: Mat):Mat{
        val gray = Mat()
        Imgproc.cvtColor(inputFrame,gray,Imgproc.COLOR_BGRA2GRAY)
        val blur = Mat()
        Imgproc.GaussianBlur(gray,blur, Size(5.0,5.0),0.0)
        val thres = Mat()
        Imgproc.threshold(blur,thres,0.0,255.0,Imgproc.THRESH_BINARY and Imgproc.THRESH_OTSU)
        return thres
    }

    override fun onFrameCompleted(inputMat: Mat, translateMat: Mat?) {
        // 为了减少开支,所以在这里进行透视变换
        val inputFrame = Mat()
        Imgproc.warpPerspective(inputMat,inputFrame,translateMat, Settings.getInstance().imgSize)
    }

    override fun onFrame(inputFrame: Mat): Mat {
        return inputFrame
    }
}