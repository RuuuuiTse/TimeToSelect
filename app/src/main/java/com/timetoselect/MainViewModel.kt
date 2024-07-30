package com.timetoselect

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel(){
     private val _state = MutableStateFlow(MainState())
     val state: StateFlow<MainState> = _state.asStateFlow()
}