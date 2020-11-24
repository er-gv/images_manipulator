package com.ergv.learning.kotlinsandbox

class Dice(val sides: Int = 6) {

    public fun roll(): Int {
        return (1..sides).random()
    }

    public fun flip(): String {
        var result = "Head"
        if(0==(1..20).random()%2)
            result= "Tails"
        return result
    }
}