package com.example.ttmrcan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.example.mrcantt2.LogIn
import com.example.mrcantt2.R

class FlashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash_screen)
        startTimer()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
    }

    private fun startTimer(){
        object: CountDownTimer(3000, 1000){
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                val intent = Intent(applicationContext, LogIn::class.java).apply{}
                startActivity(intent)
            }

        }.start()
    }
}