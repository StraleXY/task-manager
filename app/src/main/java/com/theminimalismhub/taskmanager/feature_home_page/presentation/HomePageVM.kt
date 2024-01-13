package com.theminimalismhub.taskmanager.feature_home_page.presentation

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.theminimalismhub.taskmanager.core.navigation.NavigationController
import com.theminimalismhub.taskmanager.utils.CalendarUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class HomePageVM @Inject constructor(
    val navigationController: NavigationController
) : ViewModel() {
    private val _state = mutableStateOf(HomePageState())
    val state: State<HomePageState> = _state
    private lateinit var context: Context

    fun init(context: Context) {
        this.context = context
        _state.value = _state.value.copy(
            calendars = CalendarUtils.getAllCalendars(context)
//            tasks = CalendarUtils.getCalendarEvents(context)
        )
    }

    fun onEvent(event: HomePageEvent) {
        when(event) {
            is HomePageEvent.SelectCalendar -> {
                _state.value = _state.value.copy(
                    selectedCalendar = _state.value.calendars.first { it.id == event.id },
                    tasks = CalendarUtils.getCalendarEvents(context, event.id.toString())
                )
            }
        }
    }
}