package com.myapp.answercard.opencvActivity

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import org.opencv.android.FpsMeter

import org.opencv.android.JavaCamera2View

class MyCameraView(context: Context, attrs:AttributeSet):JavaCamera2View(context,attrs) {

    override fun enableFpsMeter() {
        if (mFpsMeter == null) {
            mFpsMeter = object : FpsMeter() {
                override fun draw(canvas: Canvas?, offsetx: Float, offsety: Float) {
                    super.draw(canvas, offsetx, offsety)
                    with(context as OpencvActivity){
                    }
                }

            }
            mFpsMeter.setResolution(mFrameWidth, mFrameHeight)
        }
    }
}