package com.ergv.learning.kotlinsandbox

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import java.text.Format
import java.text.NumberFormat

class TipCalculator  : AppCompatActivity() {


    private lateinit var btn : Button
    private lateinit var priceEdt :EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tip_calculator)
        btn = findViewById(R.id.btnCalcTip)
        priceEdt = findViewById(R.id.price)
        val radioGroup = findViewById<RadioGroup>(R.id.tip_options)
        val tipAmountTxt =findViewById<TextView>(R.id.txtTipValue)
        val roundTipchk = findViewById<SwitchCompat>(R.id.round_tip)


        priceEdt.setOnEditorActionListener { view, action, event ->
            evalTipButton()
        }

        btn.setOnClickListener{
            if (!evalTipButton()){
                Toast.makeText(it.context, "No amount to calcuate tip from. Tip is zero.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val price = priceEdt.text.toString().toDouble()
            val percentages = when(radioGroup.checkedRadioButtonId){
                R.id.optAmazing ->.15
                R.id.optSuperb ->.10
                R.id.optOK ->0.025
                else->0.0
            }
            tipAmountTxt.text = String.format("%s $%.2f",
                it.context.getString(R.string.tip_amount),
                calcTip(price, percentages, roundTipchk.isChecked))
        }
    }

    override fun onResume() {
        super.onResume()
        evalTipButton()
    }

    private fun calcTip(price: Double, tip: Double, isRound: Boolean): Double {
        var result = price*tip
        if(isRound)
            result = Math.ceil(result).toDouble()
        return result
    }

    private fun evalTipButton() : Boolean{
        val priceStr = priceEdt.text.toString()
        return (priceStr != "" && priceStr.toDouble() != 0.0)

    }

}