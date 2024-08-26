package com.example.supercar


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.content.SharedPreferences
import android.content.Context
import android.widget.ImageView

class MainActivity : AppCompatActivity(),GameTask {
    lateinit var rootLayout:LinearLayout
    lateinit var startBtn:Button
    lateinit var mGameview:GameView
    lateinit var score:TextView
    lateinit var highScoreTextView: TextView
    lateinit var logo: ImageView
    lateinit var name: TextView

    private lateinit var sharedPreferences: SharedPreferences
    private var highScore:Int = 0
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startBtn=findViewById(R.id.StartBtn)
        rootLayout=findViewById(R.id.rootLayout)
        score=findViewById(R.id.score)
        highScoreTextView = findViewById(R.id.highScore)
        logo=findViewById(R.id.logo1)
        name=findViewById(R.id.creater)
        sharedPreferences = getSharedPreferences("GamePreferences", Context.MODE_PRIVATE)
        // Retrieve the high score from SharedPreferences
        highScore = sharedPreferences.getInt("highScore", 0)
        highScoreTextView.text = "High Score: $highScore"

        mGameview=GameView(this,this)

        startBtn.setOnClickListener {
            // Reset the score
            score.text = "SCORE:0"

            // Reset the variables in the GameView class
            mGameview = GameView(this, this)
            mGameview.time = 0
            mGameview.score = 0
            mGameview.speed = 1
            mGameview.otherCars.clear()

            // Add the GameView to the root layout
            mGameview.setBackgroundResource(R.drawable.road1)
            rootLayout.addView(mGameview)

            // Hide the Start button and score TextView
            startBtn.visibility = View.GONE
            score.visibility = View.GONE
            logo.visibility = View.GONE
            name.visibility = View.GONE



            // Invalidate the GameView to redraw it
            mGameview.invalidate()
        }
    }

    override fun closeGame(mscore: Int) {
        score.text="Score:$mscore"

        if (mscore > highScore) {
            highScore = mscore
            highScoreTextView.text = "High Score: $highScore"
            // Save the new high score to SharedPreferences
            sharedPreferences.edit().putInt("highScore", highScore).apply()
        }
        rootLayout.removeView(mGameview)
        startBtn.visibility=View.VISIBLE
        score.visibility=View.VISIBLE
    }
}