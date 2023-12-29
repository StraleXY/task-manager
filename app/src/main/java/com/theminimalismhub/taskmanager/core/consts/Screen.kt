package com.theminimalismhub.taskmanager.core.consts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class Screen {
    companion object {
        var HEIGHT by mutableStateOf(0.dp)
        var WIDTH by mutableStateOf(0.dp)
        var ROW_COUNT by mutableIntStateOf(1)
        var MAX_PILL_WIDTH = 200.dp
        var PILL_WIDTH by mutableStateOf(0.dp)
        var FULL_WIDTH by mutableStateOf(0.dp)

        fun setSize(width: Dp, height: Dp) {
            HEIGHT = height
            WIDTH = width
            ROW_COUNT = Integer.max((width / MAX_PILL_WIDTH).toInt(), 2)
            PILL_WIDTH = (WIDTH - Padding.PAGE_H * 2) / ROW_COUNT
            FULL_WIDTH = (WIDTH - Padding.PAGE_H * 2)
        }
    }
}