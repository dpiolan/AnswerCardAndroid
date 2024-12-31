package com.myapp.answercard.opencvActivity.core

import org.json.JSONArray
import org.json.JSONObject
import org.opencv.core.Point

data class Template(
    val name:String,
    val BarCode:Ponit2,
    val Questions:List<Ponit2>
){
    companion object{
        fun readByJSON(json:JSONObject):Template{
            val j_b = json.getJSONArray("BarCode")
            val j_b_p1 = j_b.getJSONArray(0)
            val j_b_p2 = j_b.getJSONArray(1)
            val barCode= Ponit2(
                Point(j_b_p1.getDouble(0),j_b_p1.getDouble(1)),
                Point(j_b_p2.getDouble(0),j_b_p1.getDouble(1))
            )
            val j_q = json.getJSONArray("Questions")
            val questions = List(j_q.length(),{index: Int -> Ponit2() })
            for (i in 0..j_q.length()){
                questions[i].apply {
                    point1.x =j_q.getJSONArray(i).getJSONArray(0).getDouble(0)
                    point1.y =j_q.getJSONArray(i).getJSONArray(0).getDouble(1)
                    point2.x =j_q.getJSONArray(i).getJSONArray(1).getDouble(0)
                    point1.y =j_q.getJSONArray(i).getJSONArray(1).getDouble(1)
                }
            }
            val name = json.getString("Name")
            return Template(name,barCode,questions)
        }
    }
}
