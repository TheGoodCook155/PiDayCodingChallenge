package com.pi.pidaych.ui.navigation


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pi.pidaych.ui.screens.Screens
import com.pi.pidaych.ui.views.MainScreen
import com.pi.pidaych.ui.views.SplashScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.SPLASH_SCREEN.name) {

        //TODO
        composable(Screens.SPLASH_SCREEN.name){

            SplashScreen(navController);

        }


        composable(Screens.MAIN_SCREEN.name){

            MainScreen(navController);

        }

    }


}