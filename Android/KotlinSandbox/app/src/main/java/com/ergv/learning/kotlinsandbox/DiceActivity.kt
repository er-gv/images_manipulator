package com.ergv.learning.kotlinsandbox

import android.content.res.TypedArray
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import com.ergv.learning.kotlinsandbox.Dice

class DiceActivity  : AppCompatActivity() {

    private val my_dice= Dice(6)
    //private val images :TypedArray = resources.obtainTypedArray(R.array.dice_faces)

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.dice_layout)
            val btn = findViewById<Button>(R.id.roll);
            btn.setOnClickListener{rollDice()}
            rollDice()
    }

    fun rollDice() {
        val roll1 = my_dice.roll()
        val roll2 = my_dice.roll()
        val resTxt = findViewById<TextView>(R.id.outcome)
        setDiceImage(roll1, findViewById<ImageView>(R.id.dice1Image))
        //setDiceImage(roll2, findViewById<ImageView>(R.id.dice2Image))

        resTxt.text = "Got ${roll1+roll2} out of ${2*my_dice.sides}"



    }

    fun setDiceImage(roll:Int, img: ImageView){
        val drawableRes = when (roll) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        img.setImageResource(drawableRes)
    }
}
