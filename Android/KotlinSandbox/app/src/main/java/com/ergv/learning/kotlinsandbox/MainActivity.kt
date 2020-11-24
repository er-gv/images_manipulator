package com.ergv.learning.kotlinsandbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var greatTop = this.findViewById<TextView>(R.id.great_top)
        greatTop.text="${getString(R.string.happy_birthday)} to you, ${getString(R.string.to)}"

        var greatBottom = this.findViewById<TextView>(R.id.great_bottom)
        greatBottom.text="${getString(R.string.lots_love)} from ${getString(R.string.from)}"

    }
}