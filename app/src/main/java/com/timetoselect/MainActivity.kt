package com.timetoselect

import androidx.activity.ComponentActivity
import android.os.Bundle
import androidx.activity.compose.setContent



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                MainScreen()
        }
    }
}




