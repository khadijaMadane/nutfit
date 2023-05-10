package com.example.nutfit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.nutfit.NutrientsName

class DashboardMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_main)
        val nutrientCount = intent.getIntExtra("nutrientCount", 0)
        val nutrientCountTextView = findViewById<TextView>(R.id.aaaa).apply {
            text= nutrientCount.toString()
        }
        //nutrientCountTextView.text = nutrientCount.toString()

    }
}