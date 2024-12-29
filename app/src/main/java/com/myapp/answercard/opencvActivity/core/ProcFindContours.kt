package com.myapp.answercard.opencvActivity.core


import com.myapp.answercard.opencvActivity.MyCameraViewManger
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.MatOfPoint2f
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class ProcFindContours():MyCameraViewManger.ProcFrameCallback{

    private val settings:Settings = Settings.getInstance()

    private var perspectiveCallback:PerspectiveCallback? = null

    private var saveTranslateMat = Mat()
    private var saveInputFrame = Mat()

    var clocked = false

    interface PerspectiveCallback{
        fun onFrameCompleted(inputMat: Mat,translateMat:Mat?)
    }

    private fun setPerspectiveCallback(callback: PerspectiveCallback){
        perspectiveCallback = callback
    }

    constructor(perspectiveCallback: PerspectiveCallback):this(){
        this.perspectiveCallback = perspectiveCallback
    }


    private fun preProcess(inputMat: Mat):Mat{
        val gray = Mat()
        Imgproc.cvtColor(inputMat,gray,Imgproc.COLOR_BGRA2GRAY)

        val threshold:Mat = Mat()
        Imgproc.threshold(gray,threshold,125.0,255.0,Imgproc.THRESH_BINARY)
        val blur = Mat()
        Imgproc.GaussianBlur(threshold,blur, Size(Point(5.0,5.0)),0.0)
        return blur
    }

    private fun findContourExternal(inputMat: Mat):MatOfPoint2f?{
        val edge:Mat = Mat()
        Imgproc.Canny(inputMat,edge,30.0,150.0)

        val contours:MutableList<MatOfPoint> = mutableListOf()
        val l_ = Mat()
        Imgproc.findContours(edge,contours,l_,Imgproc.RETR_EXTERNAL,Imgproc.CHAIN_APPROX_SIMPLE)
        if (contours.isEmpty()){
            return null
        }
        contours.sortBy{
            a->
            -Imgproc.contourArea(a)
        }
        val pContour = MatOfPoint2f(*contours[0].toArray())
        val epsilon = 0.1 * Imgproc.arcLength(MatOfPoint2f(*contours[0].toArray()),true)
        val sContour = MatOfPoint2f()
        Imgproc.approxPolyDP(pContour,sContour,epsilon,true)
        if (sContour.toArray().size != 4){
            return null
        }
        return sContour
    }

    /**
     * @return List<Point> 返回依次为 左上 右上,右下,左下
     */
    private fun getTranslatePoints(contour:MatOfPoint2f):List<Point>{
        val points = contour.toArray().sortedWith(compareBy({ it.y }, { it.x }))
        val topPoints = points.take(2).sortedBy { it.x }
        val bottomPoints = points.takeLast(2).sortedBy { it.x }
        return listOf(topPoints[0], topPoints[1], bottomPoints[1], bottomPoints[0])
    }

    override fun onFrame(inputFrame: Mat): Mat {
        if (!clocked) {
            var mat: Mat = preProcess(inputFrame)
            val contour = findContourExternal(mat)
            if (contour != null) {
                val points = getTranslatePoints(contour)
                // 勾股定律计算长度
                settings.imgSize
                val pointsT = listOf(
                    Point(0.0, 0.0),
                    Point(settings.imgSize.width, 0.0),
                    Point(settings.imgSize.width, settings.imgSize.height),
                    Point(0.0, settings.imgSize.height)
                )

                Imgproc.getPerspectiveTransform(
                    MatOfPoint2f(*points.toTypedArray()),
                    MatOfPoint2f(*pointsT.toTypedArray())
                ).copyTo(saveTranslateMat)
                inputFrame.copyTo(saveInputFrame)
                perspectiveCallback?.onFrameCompleted(inputFrame, saveTranslateMat)
                Imgproc.drawContours(
                    inputFrame,
                    listOf(MatOfPoint(*contour.toArray())),
                    -1,
                    Scalar(0.0, 255.0, 0.0)
                )
            }
            return inputFrame
        }else{
            val transFrame = Mat()
            Imgproc.warpPerspective(saveInputFrame,transFrame,saveTranslateMat,settings.imgSize)
            return transFrame
        }
    }

}