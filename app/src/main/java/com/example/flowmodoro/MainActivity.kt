package com.example.flowmodoro

/*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}


*/
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.os.CountDownTimer
import android.view.View
import android.widget.Chronometer
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity() {


    var isRunning: Boolean = false;
    var isStudying: Boolean = false;
    var isBreaking: Boolean = false;




    var time_when_stopped: Long=0;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //var chrono =  Chronometer(this);
        var chrono: Chronometer = findViewById<Chronometer>(R.id.StudyTime)

        buttonPlay.setOnClickListener {
            if (isRunning) {
            } else {
                isRunning=true
                isStudying=true
                chrono.setBase(SystemClock.elapsedRealtime() + time_when_stopped);
                chrono.start()
            }
        }

        buttonPause.setOnClickListener {
            isRunning = false
            time_when_stopped = chrono.getBase() - SystemClock.elapsedRealtime();
            chrono.stop()
        }

        buttonStop.setOnClickListener {
            isRunning=false
            isStudying=false
            isBreaking=false

            chrono.setBase(SystemClock.elapsedRealtime());
            time_when_stopped=0;
            chrono.stop()
        }


    }




}