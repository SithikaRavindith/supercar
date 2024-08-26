package com.example.supercar


import android.graphics.Paint

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.view.MotionEvent

import android.view.View


class GameView(var c:Context,var game1:GameTask):View(c)
{
    public  var myPaint: Paint? = null
    public var speed = 1
    public var time = 0
    public var score = 0
    public var myCarPosition= 0


    public val otherCars=ArrayList<HashMap<String,Any>>()

    var viewWidth=0
    var viewHeight=0
    init{
        myPaint= Paint()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewWidth=this.measuredWidth
        viewHeight=this.measuredHeight

        if(time % 700<10+speed)
        {
            val map=HashMap<String,Any>()
            map["lane"]=(0..2).random()
            map["startTime"]=time
            otherCars.add(map)

        }
        time=time+10+speed
        val otherCarsWidth=viewWidth/5
        val otherCarsHeight=otherCarsWidth+10
        myPaint!!.style=Paint.Style.FILL
        val d=resources.getDrawable(R.drawable.maincar1,null)

        d.setBounds(
            myCarPosition *viewWidth/3+viewWidth/15+25,
            viewHeight-2-otherCarsHeight,
            myCarPosition*viewWidth/3+viewWidth/15+otherCarsWidth-25,
            viewHeight-2
        )
        d.draw(canvas!!)
        myPaint!!.color=Color.GREEN
        var highScore= 0
        for (i in otherCars.indices)
        {
            try{
                val spaceX=otherCars[i]["lane"]as Int *viewWidth/3+viewWidth/15
                var spaceY=time-otherCars[i]["startTime"] as Int
                val d2=resources.getDrawable(R.drawable.yellowcar,null)

                d2.setBounds(
                    spaceX+25,spaceY-otherCarsHeight,spaceX+otherCarsWidth-25,spaceY

                )
                d2.draw(canvas)
                if(otherCars[i]["lane"] as Int==myCarPosition)
                {
                    if (spaceY>viewHeight-2-otherCarsHeight && spaceY<viewHeight -2){
                        game1.closeGame(score)
                    }
                }
                if(spaceY>viewHeight +otherCarsHeight)
                {
                    otherCars.removeAt(i)
                    score++
                    speed=1+Math.abs(score/8)
                    if(score>highScore)
                    {
                        highScore=score
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }
        myPaint!!.color=Color.WHITE
        myPaint!!.textSize=40f
        canvas.drawText("Score:$score",80f,80f,myPaint!!)
        canvas.drawText("Speed:$speed",380f,80f,myPaint!!)
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event!!.action){
            MotionEvent.ACTION_DOWN->{
                val x1=event.x
                if(x1<viewWidth/2){
                    if(myCarPosition>0){
                        myCarPosition--
                    }
                }
                if (x1>viewWidth/2){
                    if (myCarPosition<2){
                        myCarPosition++
                    }
                }
                invalidate()
            }
            MotionEvent.ACTION_UP->{}
        }
        return true
    }


}
