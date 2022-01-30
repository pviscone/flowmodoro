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
import android.text.format.DateUtils
import android.view.View
import android.widget.Chronometer
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity() {


    var isRunning: Boolean = false;
    var isStudying: Boolean = false;
    var isShortBreak: Boolean = false;
    var isLongBreak: Boolean = false;



    var TimeWhenStopped: Long=0;




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var StudyChrono: Chronometer = findViewById<Chronometer>(R.id.StudyTime)
        lateinit var ShortCountDown: CountDownTimer;
        lateinit var LongCountDown: CountDownTimer;
        var ShortSeconds: Long=0;
        var LongSeconds:Long=0;
        var counterS: Long=0;
        var counterL: Long=0;




        fun updateTextUI() {
            ShortTime.text = DateUtils.formatElapsedTime(ShortSeconds)
            LongTime.text = DateUtils.formatElapsedTime(LongSeconds)
        }

        fun start() {
            val S_string = SFactor.text.toString()
            val S: Long = S_string.toLong()
            val L_string = LFactor.text.toString()
            val L: Long = L_string.toLong()
            isRunning = true
            isStudying = true
            isShortBreak=false
            isLongBreak=false
            StudyChrono.setBase(SystemClock.elapsedRealtime() + TimeWhenStopped)
            StudyChrono.start()
            StudyChrono.setOnChronometerTickListener {
                counterS+=1
                counterL+=1
                ShortSeconds+=counterS/S
                LongSeconds+=counterL/L
                if (counterS==S){
                    counterS=0
                }
                if (counterL==L){
                    counterL=0
                }
                //ShortSeconds=((SystemClock.elapsedRealtime()-StudyChrono.getBase())/(1000*S) );
                //LongSeconds=((SystemClock.elapsedRealtime()-StudyChrono.getBase())/(1000*L) );
                updateTextUI()
            }
        }

        fun pause(){
            isRunning = false
            if(isStudying) {
                TimeWhenStopped = StudyChrono.getBase() - SystemClock.elapsedRealtime();
            }
            StudyChrono.stop()
        }

        fun stop(){
            isRunning=false
            isStudying=false
            isShortBreak=false
            isLongBreak=false
            StudyChrono.setBase(SystemClock.elapsedRealtime());
            TimeWhenStopped=0;
            StudyChrono.stop()
        }

        fun study(){
            isRunning=true
            isStudying=true
            isLongBreak=false
            isShortBreak=false
            start()

        }

        fun short_break(){
            isRunning=true
            isStudying=false
            isShortBreak=true
            isLongBreak=false
            TimeWhenStopped = StudyChrono.getBase() - SystemClock.elapsedRealtime();
            StudyChrono.stop()

            ShortCountDown = object : CountDownTimer(ShortSeconds*1000, 1000) {
                override fun onFinish() {
                    //ALLARME
                }

                override fun onTick(p0: Long) {
                    ShortSeconds = p0/1000
                    updateTextUI()
                }
            }
            ShortCountDown.start()



        }

        fun long_break(){
            isRunning=true
            isStudying=false
            isShortBreak=false
            isLongBreak=true

        }

        buttonPlay.setOnClickListener {
            if (isRunning) {
            } else {
                start()
            }
        }

        buttonPause.setOnClickListener {
            pause()
        }

        buttonStop.setOnClickListener {
            stop()
        }



        buttonStudy.setOnClickListener {
            study()
        }
        buttonShort.setOnClickListener {
            short_break()
        }
        buttonLong.setOnClickListener {
            long_break()
        }







    }




}