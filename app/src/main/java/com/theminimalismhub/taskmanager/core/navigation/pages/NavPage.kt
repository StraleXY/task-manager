package com.theminimalismhub.taskmanager.core.navigation.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class NavPage(
    val key: String,
    val composable: @Composable () -> Unit
) {
    var openCallback: ((List<Any>) -> Unit)? = null
    var closeCallback: (() -> Unit)? = null
    var cached: List<Any>? = null
    var visible by mutableStateOf(false)

    fun registerCallback(open: (List<Any>) -> Unit, close: () -> Unit) {
        openCallback = open
        closeCallback = close
        cached?.let { this.open(it) }
    }
    fun open(args: List<Any>) {
        cached = args
        visible = true
        openCallback?.invoke(args)
    }
    fun close() {
        visible = false
        cached = null
        closeCallback?.invoke()
    }
}