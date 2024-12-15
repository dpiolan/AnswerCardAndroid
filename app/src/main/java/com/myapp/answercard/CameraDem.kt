package com.myapp.answercard

import android.content.Context
import android.hardware.camera2.CameraManager

class CameraDem(context: Context) {

    private val cameraManger:CameraManager by lazy {
        context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    }

    lateinit var cameraIdList:Array<String>

    init {
        cameraIdList = cameraManger.cameraIdList


    }




}