package com.theminimalismhub.taskmanager.feature_my_day.presentation

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.theminimalismhub.taskmanager.core.navigation.NavigationController
import com.theminimalismhub.taskmanager.utils.CalendarUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class MyDayVM @Inject constructor(
    val navigationController: NavigationController
) : ViewModel() {

    private val _state = mutableStateOf(MyDayState())
    val state: State<MyDayState> = _state
    private lateinit var context: Context

    fun init(context: Context) {
        this.context = context
        _state.value = _state.value.copy(
            tasks = CalendarUtils.getTodayTasks(context, listOf("9", "11", "20", "24", "27", "28", "29")).sortedBy { it.timeStart }
        )
    }
}