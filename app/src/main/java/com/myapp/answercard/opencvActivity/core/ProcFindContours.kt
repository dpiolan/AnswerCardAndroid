package com.myapp.answercard.opencvActivity.core


import com.myapp.answercard.opencvActivity.MyCameraViewManger
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.MatOfPoint2f
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class ProcFindContours:MyCameraViewManger.ProcFrameCallback{

    private val settings:Settings = Settings.getInstance()

    interface PerspectiveCallback{
        fun onFrameCompleted(inputMat: Mat,contours:MutableList<MatOfPoint>)
    }

    private fun preProcess(inputMat: Mat):Mat{
        val threshold:Mat = Mat(inputMat.height(),inputMat.width(),inputMat.type())
        Imgproc.threshold(inputMat,threshold,125.0,255.0,Imgproc.THRESH_BINARY)
        val blur = Mat(inputMat.size(),inputMat.type())
        Imgproc.GaussianBlur(threshold,blur, Size(Point(5.0,5.0)),0.0)
        return blur
    }

    private fun findContourExternal(inputMat: Mat):Mat{
        val inputMat_ = Mat()

        val edge:Mat = Mat()
        Imgproc.Canny(inputMat,edge,30.0,150.0)

        val contours:MutableList<MatOfPoint> = mutableListOf()
        val l_ = Mat(inputMat.size(),inputMat.type())
        Imgproc.findContours(edge,contours,l_,Imgproc.RETR_EXTERNAL,Imgproc.CHAIN_APPROX_SIMPLE)
        val maxContours = MatOfPoint2f(*contours[0].toArray())
        val epsilon = 0.1*Imgproc.arcLength(maxContours,true)
        val nContour = MatOfPoint2f()
        Imgproc.approxPolyDP(maxContours,nContour,epsilon,true)
        if (nContour.width() == 4 || nContour.height() == 4){
            Imgproc.drawContours(inputMat, listOf(MatOfPoint(nContour)),-1, Scalar(0.0,255.0,0.0),3)
        }
        return inputMat
    }



    override fun onFrame(inputFrame: Mat): Mat {

        var mat:Mat = preProcess(inputFrame)
        return findContourExternal(mat)
    }
}