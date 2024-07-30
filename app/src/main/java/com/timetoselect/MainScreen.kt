package com.timetoselect

import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.compose.runtime.*
import androidx.compose.material.*

@Preview(showBackground = true)
@Composable
fun MainScreen (
    vm: MainViewModel = viewModel(),
) {
    val state by vm.state.collectAsState()
    val showDialog = remember { mutableStateOf(false) }
    AddDialog(dialogState = showDialog)
    Column {
        Row{
            Text(
                text = "TimeToSelect",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .weight(5.5f)
                    .align(Alignment.CenterVertically)
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
        )
        LazyColumn (
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
        ){
            items(state.timeList.size){index ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ){
                    Text(
                        text = state.timeList[index],
                        color = textColor(index,state.timeList.size),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .weight(5.5f)
                            .align(Alignment.CenterVertically)

                    )
                    Button(
                        onClick = {
                            state.okList[index]++
                            sortTime(state.timeList,state.okList,state.noList,index)
                            },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Green,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1.5f)
                    ) {
                        Text(
                            text = state.okList[index].toString(),
                            style = TextStyle(
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold
                            ))
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(0.5f)
                    )
                    Button(
                        onClick = {
                            state.noList[index]++
                            sortTime(state.timeList,state.okList,state.noList,index) },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Red,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1.5f)
                    ) {
                        Text(
                            text = state.noList[index].toString(),
                            style = TextStyle(
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold
                            ))
                    }
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                )
            }
        }
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
        ){
            Spacer(
                modifier = Modifier
                    .weight(3.5f)
                    .fillMaxHeight()
            )
            Button(
                onClick = { showDialog.value = true},
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()) {
                Text(
                    text = "+",
                    style = TextStyle(
                    fontSize = 55.sp,
                    fontWeight = FontWeight.Bold
                ))
            }
            Spacer(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxHeight()
            )
        }
        Spacer(
            modifier = Modifier
                .height(20.dp))
    }
}

@Composable
fun ShowAlert(
    alertState:MutableState<Boolean>,
){
    if(alertState.value){
        AlertDialog(
            onDismissRequest = { !alertState.value },
            title = {
                Text(text = "Alert")
            },
            text = {
              Text(text ="时间输入有误！")
            },
            confirmButton = {
                TextButton(onClick = {
                    alertState.value = false
                }) {
                    Text(text = "ok")
                }
            }
        )
    }
}

@Composable
fun AddDialog(
    dialogState:MutableState<Boolean>,
    vm: MainViewModel = viewModel()
){
    val showAlert = remember { mutableStateOf(false) }
    ShowAlert(alertState = showAlert)
    val state by vm.state.collectAsState()
    var day by remember { mutableStateOf("") }
    var hour by remember { mutableStateOf("") }
    var minute by remember { mutableStateOf("") }
    if(dialogState.value){
        AlertDialog(
            onDismissRequest = { !dialogState.value },
            title = {
                Text(text = "add time")
            },
            text = {
                   Row {
                       Text(
                           text = "周",
                           style = TextStyle(
                               fontSize = 30.sp,
                               fontWeight = FontWeight.Bold
                           ),
                           modifier = Modifier
                               .weight(2f)
                       )
                       TextField(
                           value = day,
                           onValueChange = {
                               day = it
                           },
                           modifier = Modifier
                               .weight(3f)
                       )
                       Spacer(modifier = Modifier
                           .weight(1f))
                       TextField(
                           value = hour,
                           onValueChange = {
                               hour = it
                           },
                           modifier = Modifier
                               .weight(3f)
                       )
                       Text(
                           text = "：",
                           style = TextStyle(
                               fontSize = 30.sp,
                               fontWeight = FontWeight.Bold
                           ),
                           modifier = Modifier
                               .weight(2f)
                       )
                       TextField(
                           value = minute,
                           onValueChange = {
                               minute = it
                           },
                           modifier = Modifier
                               .weight(3f)
                       )
                   }
            },
            confirmButton = {
                TextButton(onClick = {
                    if(checkTime(day,hour,minute)){
                        dialogState.value = false
                        addTime(state.timeList,state.okList,state.noList,day,hour,minute)
                        day=""
                        hour=""
                        minute=""
                    }
                    else{
                        showAlert.value = true
                    }
                }) {
                    Text(text = "ok")
                }
            },
            dismissButton = {
                TextButton(onClick = { dialogState.value = false}) {
                    Text(text = "cancel")
                    day=""
                    hour=""
                    minute=""
                }
            }
        )
    }
}

fun checkTime(day:String,hour:String,minute:String): Boolean {
    return (day in "一二三四五六日") && (hour.toInt() in 0..23) && (minute.toInt() in 0..59)
}

fun addTime(timelist:MutableList<String>,oklist:MutableList<Int>,nolist:MutableList<Int>,day:String,hour:String,minute:String){
    timelist.add("周$day$hour:$minute")
    oklist.add(0)
    nolist.add(0)
}

fun exchangeString(list:MutableList<String>,index:Int){
    val c = list[index]
    list[index]=list[index+1]
    list[index+1]= c
}

fun exchangeInt(list:MutableList<Int>,index:Int){
    val c = list[index]
    list[index]=list[index+1]
    list[index+1]= c
}

fun exchangeTime(timelist:MutableList<String>,oklist:MutableList<Int>,nolist:MutableList<Int>,index:Int){
    exchangeString(timelist,index)
    exchangeInt(oklist,index)
    exchangeInt(nolist,index)
}

fun sortTime(timelist:MutableList<String>,oklist:MutableList<Int>,nolist:MutableList<Int>,index:Int){
    if(index<timelist.size-1){
        if(nolist[index]>nolist[index+1]){
            exchangeTime(timelist,oklist,nolist,index)
            val newindex = index+1
            sortTime(timelist,oklist,nolist,newindex)
        }
        if(nolist[index]==nolist[index+1]){
            if(oklist[index]<oklist[index+1]){
                exchangeTime(timelist,oklist,nolist,index)
                val newindex = index+1
                sortTime(timelist,oklist,nolist,newindex)
            }
        }
    }
    if(index>0){
        if(oklist[index]==oklist[index-1]){
            if(nolist[index]<nolist[index-1]){
                exchangeTime(timelist,oklist,nolist,index-1)
                val newindex = index-1
                sortTime(timelist,oklist,nolist,newindex)
            }
        }
        if(oklist[index]>oklist[index-1]){
            if(nolist[index] <= nolist[index-1]){
                exchangeTime(timelist,oklist,nolist,index-1)
                val newindex = index-1
                sortTime(timelist,oklist,nolist,newindex)
            }
        }
    }
}

fun textColor(index:Int,num:Int):Color{
    val r = 110 + 40*index/num
    val g = 10 + 100*index/num
    val b = 200
    return Color(r,g,b)
}