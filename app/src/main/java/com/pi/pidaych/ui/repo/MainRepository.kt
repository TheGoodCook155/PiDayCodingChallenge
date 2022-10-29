package com.pi.pidaych.ui.repo

import android.content.Context
import android.util.Log
import java.io.InputStream



class MainRepository {

    private var PI_ARR_DIGIT = mutableListOf<Int>()

     fun readPiNums(context: Context): MutableList<Int>{

         val inputStream: InputStream = context.assets.open("milion_PI.txt")

         val str = inputStream.bufferedReader().use {
            it.readText()
        }

        str.forEach { char ->

            if(char != '.'){
                var digit = Integer.parseInt(char+"")
                PI_ARR_DIGIT.add(digit)
            }
        }
         Log.d("data", "readPiNums: size = ${PI_ARR_DIGIT.size} and value = ${PI_ARR_DIGIT}")
        return PI_ARR_DIGIT
    }

}