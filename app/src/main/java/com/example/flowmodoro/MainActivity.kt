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
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.text.format.DateUtils
import android.widget.Chronometer
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.sql.Time
import java.util.*

class MainActivity : AppCompatActivity() {


    var isRunning: Boolean = false;
    var isStudying: Boolean = false;
    var isShortBreak: Boolean = false;
    var isLongBreak: Boolean = false;
    var isTimerActive:Boolean=false;

    var TimeWhenStopped: Long=0;




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var StudyChrono: Chronometer = findViewById<Chronometer>(R.id.StudyTime)
        lateinit var ShortCountDown: CountDownTimer;
        lateinit var LongCountDown: CountDownTimer;
        var ShortSeconds: Long=0;
        var LongSeconds:Long=0;

        var timer = Timer()
        var S: Long = (SFactor.text.toString()).toLong()
        var L: Long = (LFactor.text.toString()).toLong()
        buttonStop.setBackgroundColor(0xFFFF0000.toInt())




        fun updateTextUI() {
            ShortTime.text = DateUtils.formatElapsedTime(ShortSeconds)
            LongTime.text = DateUtils.formatElapsedTime(LongSeconds)
        }




        fun start() {
            S=(SFactor.text.toString()).toLong()
            L=(LFactor.text.toString()).toLong()
            isRunning = true
            isStudying = true
            isShortBreak=false
            isLongBreak=false
            buttonPlay.setBackgroundColor(0xFFFF0000.toInt())
            buttonPause.setBackgroundColor(0x000000FF.toInt())
            buttonStudy.setBackgroundColor(0xFFFF0000.toInt())
            buttonShort.setBackgroundColor(0x000000FF.toInt())
            buttonLong.setBackgroundColor(0x000000FF.toInt())
            buttonStop.setBackgroundColor(0x000000FF.toInt())
            StudyChrono.setBase(SystemClock.elapsedRealtime() + TimeWhenStopped)
            StudyChrono.start()

            var S_timerTask: TimerTask = object : TimerTask() {
                override fun run() {
                    ShortSeconds+=1
                    updateTextUI()
                }
            }
            var L_timerTask: TimerTask = object : TimerTask() {
                override fun run() {
                    LongSeconds+=1
                    updateTextUI()
                }
            }
            if(!isTimerActive) {
                timer.scheduleAtFixedRate(S_timerTask, S * 1000, S * 1000)
                timer.scheduleAtFixedRate(L_timerTask, L * 1000, L * 1000)
            }
            isTimerActive=true
            /*
            StudyChrono.setOnChronometerTickListener {
                increment()
            }*/
        }

        fun pause(){
            if (isTimerActive) {
                timer.cancel()
                timer = Timer()
                isTimerActive=false
            }
            buttonPlay.setBackgroundColor(0x000000FF.toInt())
            buttonPause.setBackgroundColor(0xFFFF0000.toInt())
            isRunning = false
            if(isStudying) {
                TimeWhenStopped = StudyChrono.getBase() - SystemClock.elapsedRealtime();
            }
            else if (isShortBreak){
                ShortCountDown.cancel()
            }
            else if (isLongBreak){
                LongCountDown.cancel()
                ShortCountDown.cancel()
            }
            StudyChrono.stop()
        }


        fun stop(){
            if (isTimerActive) {
                timer.cancel()
                timer = Timer()
                isTimerActive=false
            }
            if(isShortBreak){
                ShortCountDown.cancel()
            }
            else if (isLongBreak) {
                LongCountDown.cancel()
                ShortCountDown.cancel()
            }
            ShortSeconds=0
            LongSeconds=0
            buttonPlay.setBackgroundColor(0x000000FF.toInt())
            buttonPause.setBackgroundColor(0x000000FF.toInt())
            buttonShort.setBackgroundColor(0x000000FF.toInt())
            buttonLong.setBackgroundColor(0x000000FF.toInt())
            buttonStudy.setBackgroundColor(0x000000FF.toInt())
            buttonStop.setBackgroundColor(0xFFFF0000.toInt())
            StudyChrono.setBase(SystemClock.elapsedRealtime());
            TimeWhenStopped=0;
            StudyChrono.stop()
            updateTextUI()
            isRunning=false
            isStudying=false
            isShortBreak=false
            isLongBreak=false
        }

        fun study(){
            buttonShort.setBackgroundColor(0x000000FF.toInt())
            buttonLong.setBackgroundColor(0x000000FF.toInt())
            buttonStudy.setBackgroundColor(0xFFFF0000.toInt())
            S=(SFactor.text.toString()).toLong()
            L=(LFactor.text.toString()).toLong()
            if(isShortBreak){
                ShortCountDown.cancel()
            }
            else if (isLongBreak){
                LongCountDown.cancel()
                ShortCountDown.cancel()

            }
            isRunning=true
            isStudying=true
            isLongBreak=false
            isShortBreak=false
            start()

        }

        fun short_break(){
            if (isTimerActive) {
                timer.cancel()
                timer = Timer()
                isTimerActive=false
            }
            if (isLongBreak){
                LongCountDown.cancel()
            }
            buttonStudy.setBackgroundColor(0x000000FF.toInt())
            buttonLong.setBackgroundColor(0x000000FF.toInt())
            buttonShort.setBackgroundColor(0xFFFF0000.toInt())


            isStudying=false
            isShortBreak=true
            isLongBreak=false
            if (isRunning) {
                TimeWhenStopped = StudyChrono.getBase() - SystemClock.elapsedRealtime();
            }
            StudyChrono.stop()

            ShortCountDown = object : CountDownTimer(ShortSeconds*1000, 1000) {
                override fun onFinish() {
                    val mp: MediaPlayer = MediaPlayer.create(
                        baseContext,
                        R.raw.notification,
                    )

                    mp.start()
                }

                override fun onTick(p0: Long) {
                    ShortSeconds = p0/1000
                    updateTextUI()
                }
            }
            ShortCountDown.start()



        }

        fun long_break(){
            if (isTimerActive) {
                timer.cancel()
                timer = Timer()
                isTimerActive=false
            }
            if (isShortBreak){
                ShortCountDown.cancel()
            }
            buttonStudy.setBackgroundColor(0x000000FF.toInt())
            buttonShort.setBackgroundColor(0x000000FF.toInt())
            buttonLong.setBackgroundColor(0xFFFF0000.toInt())


            isStudying=false
            isShortBreak=false
            isLongBreak=true
            if (isRunning){
                TimeWhenStopped = StudyChrono.getBase() - SystemClock.elapsedRealtime();
            }

            StudyChrono.stop()
            LongCountDown = object : CountDownTimer(LongSeconds*1000, 1000) {
                override fun onFinish() {
                    val mp: MediaPlayer = MediaPlayer.create(
                        baseContext,
                        R.raw.notification,
                    )

                    mp.start()
                }

                override fun onTick(p0: Long) {
                    LongSeconds = p0/1000
                    updateTextUI()
                }
            }
            LongCountDown.start()

            ShortCountDown = object : CountDownTimer(ShortSeconds*1000, 1000) {
                override fun onFinish() {

                }

                override fun onTick(p0: Long) {
                    ShortSeconds = p0/1000
                    updateTextUI()
                }
            }
            ShortCountDown.start()



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