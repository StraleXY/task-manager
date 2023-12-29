package com.theminimalismhub.taskmanager.core.navigation.popup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

abstract class PopupItem {

    var openCallback: ((List<Any>) -> Unit)? = null
    var visible by mutableStateOf(false)
    var cached: List<Any>? = null

    fun registerCallback(open: (List<Any>) -> Unit) {
        openCallback = open
        cached?.let { this.open(it) }
    }
    fun unregisterCallback() {
        openCallback = null
    }
    protected fun open(args: List<Any>) {
        if (openCallback == null) cached = args
        visible = true
        openCallback?.invoke(args)
    }
    fun close() {
        visible = false
        cached = null
    }
}