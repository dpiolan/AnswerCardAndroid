package com.myapp.answercard

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.ScaleAnimation
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment


class MainFragment(context:MainActivity):Fragment() {
    private val mainActivity = context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View =inflater.inflate(R.layout.main_layout,container,false)
        val titleVec:View = view.findViewById(R.id.Top_title)
        titleVec.apply {
            setOnClickListener {
                Toast.makeText(context,"bbb don't click me",Toast.LENGTH_SHORT).show()
            }
        }
        val firstBar:View = view.findViewById(R.id.firstBar)
        firstBar.setOnTouchListener{v:View,event->
            val cardView = firstBar.findViewById<CardView>(R.id.firstBar_CardView)
            if(event.actionMasked == MotionEvent.ACTION_DOWN){
                cardView.alpha = 0.3f
                cardView.layoutParams.height = firstBar.height
                cardView.layoutParams.width = firstBar.width
                cardView.layoutParams = cardView.layoutParams
                val c = ScaleAnimation(0.3f,1f,1f,1f)
                c.duration = 100
                cardView.animation = c
                cardView.animation.start()
            }
            else if(event.actionMasked == MotionEvent.ACTION_UP){
                cardView.alpha = 0f
                mainActivity.viewModel.pushFragment(FBFragment(mainActivity))
            }
            true
        }

        val secondBar:View = view.findViewById(R.id.secondBar)
        secondBar.setOnTouchListener{ v,event ->
            val cardView = secondBar.findViewById<CardView>(R.id.secondBar_CardView)
            if(event.actionMasked == MotionEvent.ACTION_DOWN){
                cardView.alpha = 0.3f
                cardView.layoutParams.height = secondBar.height
                cardView.layoutParams.width = secondBar.width
                cardView.layoutParams = cardView.layoutParams
                val c = ScaleAnimation(0.3f,1f,1f,1f)
                c.duration = 100
                cardView.animation = c
                cardView.animation.start()

            }
            else if(event.actionMasked == MotionEvent.ACTION_UP){
                cardView.alpha = 0f

            }
            true

        }

        val setting:View = view.findViewById(R.id.settings)
        setting.setOnClickListener{
        }

        return view
    }

}