package com.myapp.answercard.opencvActivity.core

import org.opencv.core.Point

class Ponit2() {

    val point1:Point = Point()
    val point2:Point = Point()

    constructor(point1:Point,point2: Point):this(){
        this.point1.x = point1.x
        this.point1.y = point1.y
        this.point2.x = point2.x
        this.point2.y = point2.y
    }

    fun getDistance():Double{
        return Math.pow(
            Math.pow(point1.x - point2.x,2.0) + Math.pow(point1.y - point2.y,2.0),
            0.5
        )
    }

    fun getDirection():Point{
        return Point(
            point2.x - point1.x,
            point2.y - point1.y
        )

    }

}