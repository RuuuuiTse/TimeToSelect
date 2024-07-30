package com.timetoselect

import androidx.compose.runtime.mutableStateListOf

data class MainState(
    val timeList : MutableList<String> = mutableStateListOf(),
    val okList: MutableList<Int> = mutableStateListOf(),
    val noList:MutableList<Int> = mutableStateListOf()
)