package com.example.mymechinelearning

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)


    }

    fun Pindah(view: View) {
        val intent = Intent(this@WelcomeActivity, NavigationActivity::class.java)
        startActivity(intent)
    }

}