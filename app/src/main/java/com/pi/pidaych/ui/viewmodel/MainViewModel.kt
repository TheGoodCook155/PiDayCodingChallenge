package com.pi.pidaych.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pi.pidaych.ui.repo.MainRepository
import kotlinx.coroutines.launch


class MainViewModel : ViewModel (){

    private val repo = MainRepository()

    private var _piNumbersList = emptyList<Int>()
    var piNumsReturned = emptyList<Int>()

    fun getPINumbers(context: Context): List<Int> {
        viewModelScope.launch {
            _piNumbersList = repo.readPiNums(context)
        }
        piNumsReturned = _piNumbersList
        Log.d("data", "getPINumbers: data = ${piNumsReturned}")
        return piNumsReturned
    }


}