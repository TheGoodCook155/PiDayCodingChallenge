package com.pi.pidaych.ui.views

import androidx.compose.ui.geometry.Size
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pi.pidaych.R
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pi.pidaych.ui.screens.Screens


@Composable
fun SplashScreen( navController: NavController = rememberNavController()){


    val animateFloat = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    val radius = 250f




    LaunchedEffect(animateFloat) {
        animateFloat.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 3000, easing = LinearEasing))

        navController.navigate(Screens.MAIN_SCREEN.name)
    }


        androidx.compose.material.Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Transparent, shape = RectangleShape),
            shape = RectangleShape

        ) {

            Surface(modifier = Modifier.fillMaxSize()) {

                Column(modifier = Modifier.padding(2.dp), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {



                    Image(
                        painter = painterResource(id = R.drawable.pi),
                        contentDescription = "a",
                        modifier = Modifier
                            .width(250.dp)
                            .height(250.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))


                    Canvas(modifier = Modifier
                        .width(300.dp).height(300.dp)
                        .background(Color.Transparent)) {


                        drawArc(
                            color = Color.Black,
                            startAngle = 1f,
                            sweepAngle = 360f * animateFloat.value,
                            useCenter = true,
                            size = Size(radius * 2.4f, radius * 2.4f),
                            style = Stroke(2.0f)
                        )

                }








            }

            }
        }

}