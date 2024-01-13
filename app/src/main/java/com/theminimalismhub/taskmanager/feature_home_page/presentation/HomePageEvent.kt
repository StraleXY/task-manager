package com.theminimalismhub.taskmanager.feature_home_page.presentation

sealed class HomePageEvent {
    data class SelectCalendar(val id: Long) : HomePageEvent()
}