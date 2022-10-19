package com.pi.pidaych.ui.views

import android.os.Build
import android.util.Log
import android.view.MotionEvent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pi.pidaych.R
import java.io.*
import java.util.*
import kotlin.math.PI
import kotlin.math.atan2

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(navController: NavController = rememberNavController()){

    var bet = remember {
        mutableStateOf("")
    }

    var rotateReturnedValue = remember {
        mutableStateOf(0L)
    }

    var PI_ARR_DIGIT = mutableListOf<Int>()

    var showText = remember {

        mutableStateOf(false)
    }

    val context = LocalContext.current
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


    var betNum = remember {
        mutableStateOf(0)
    }


    Surface(modifier = Modifier
        .padding(1.dp)
        .fillMaxSize()
    , color = Color.Transparent) {
        Column(modifier = Modifier.padding(2.dp)

            , horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {

            DropDownMenuNum{ it ->

                if (betNum.value != it){
                    showText.value = false
                }

                betNum.value = it


            }
            Spacer(modifier = Modifier.height(10.dp))

            DropDownMenu{ it ->

                if (bet.value != it){
                    showText.value = false
                }
                bet.value = it


            }
            Spacer(modifier = Modifier.height(10.dp))

            PiCircle{

                if (rotateReturnedValue.value != it){
                    showText.value = false
                }
                rotateReturnedValue.value = it

            }


            var result = remember {
                mutableStateOf("")
            }


            var index = rotateReturnedValue.value.toInt()

            if (index >= PI_ARR_DIGIT.size - 1){
                index = PI_ARR_DIGIT.size - 2
            }


            var chosen = PI_ARR_DIGIT[index]




            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = {


                if (betNum.value == chosen && bet.value == "equal"){
                    result.value = "Nice PI Bet! You Rock!"
                }else if (betNum.value > chosen && bet.value == "greater"){
                    result.value = "Nice PI Bet! You Rock!"
                }else if (betNum.value < chosen && bet.value == "smaller"){
                    result.value = "Nice PI Bet! You Rock!"
                }else{
                    result.value = "Bad PI Bet! Show some PI respect :("
                }

                showText.value = true
                index = 0

            }) {
                Text(text = "Try your PI luck")
            }
            Spacer(modifier = Modifier.height(10.dp))


            if (showText.value == true) {
                Text(text = result.value)
            }
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownMenuNum(toReturn: (Int) -> Unit) {

    val listItems = arrayOf(1,2,3,4,5,6,7,8,9,0)

    var selectedItem by remember {
        mutableStateOf(listItems.get(0))
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    // the box
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {

        // text field
        TextField(
            value = selectedItem.toString(),
            onValueChange = {},
            readOnly = true,
            label = {
                Text(text = "Choose a digit!")
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )

        // menu
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            listItems.forEach { selectedOption ->
                // menu item


                DropdownMenuItem(onClick = {

                    selectedItem = selectedOption
                    expanded = false
                    toReturn(selectedItem)

                }) {
                    Text(text = selectedOption.toString())
                }
            }
        }
    }
    toReturn(selectedItem)

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PiCircle (onValueChange : (Long) -> Unit){

    var centerX = remember {
        mutableStateOf(0f)
    }

    var centerY = remember {
        mutableStateOf(0f)
    }

    var touchX = remember {
        mutableStateOf(0f)
    }

    var touchY = remember {
        mutableStateOf(0f)
    }


    var rotation = remember {
       mutableStateOf(0f)
    }


    var numberOfRotation = remember {
        mutableStateOf(0L)
    }


    Image(painterResource(R.drawable.img),
                modifier = Modifier
//                    .fillMaxSize()
                    .width(250.dp)
                    .height(250.dp)
                    .onGloballyPositioned {
                        val windowBound = it.boundsInWindow()
                        centerX.value = windowBound.size.width / 2f
                        centerY.value = windowBound.size.height / 2f
                    }
                    .pointerInteropFilter { event ->
                        touchX.value = event.x
                        touchY.value = event.y

                        //tangent
                        val angle = -atan2(
                            centerX.value - touchX.value,
                            centerY.value - touchY.value
                        ) * (180f / PI).toFloat() // rad to degrees

                        when (event.action) {

                            MotionEvent.ACTION_DOWN,
                            MotionEvent.ACTION_MOVE -> {

                                //remove text


                                val fixedAngle = if (angle in -180f..0F) {
                                    360f + angle
                                } else {
                                    angle
                                }

                                rotation.value = fixedAngle
                                numberOfRotation.value += 1

                                onValueChange(numberOfRotation.value)
                                true
                            }
                            else -> {
                                false
                            }
                        }

                    }
                    .rotate(rotation.value),
                    contentDescription ="image")

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownMenu(toReturn : (String) -> Unit) {

    val listItems = arrayOf("smaller", "equal", "greater")

    var selectedItem by remember {
        mutableStateOf(listItems.get(0))
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    var conjuction = remember {
        mutableStateOf("")
    }

    // the box
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {

        // text field

        if (selectedItem.equals("greater") || selectedItem.equals("smaller")){
            conjuction.value = "than"
        }else{
            conjuction.value = "as"
        }

        TextField(
            value = selectedItem,
            onValueChange = {},
            readOnly = true,
            label = {
                Text(text = "Mine is ${selectedItem} ${conjuction.value} your PI")
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )

        // menu
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            listItems.forEach { selectedOption ->
                // menu item


                DropdownMenuItem(onClick = {

                    selectedItem = selectedOption
                    expanded = false
                    toReturn(selectedItem)


                }) {
                    Text(text = selectedOption)
                }
            }
        }
    }
    toReturn(selectedItem)
}

