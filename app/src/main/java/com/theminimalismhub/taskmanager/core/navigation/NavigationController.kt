package com.theminimalismhub.taskmanager.core.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.theminimalismhub.taskmanager.core.navigation.pages.NavPage
import com.theminimalismhub.taskmanager.core.navigation.popup.Dialog
import com.theminimalismhub.taskmanager.core.navigation.popup.Toast
import java.util.EmptyStackException
import java.util.Stack

class NavigationController(
    var pages: List<NavPage>
) {
    companion object {
        private var instance: NavigationController? = null
        fun init(pages: List<NavPage>) {
            instance = NavigationController(pages)
        }
        fun getInstance() : NavigationController {
            return if (instance == null) return NavigationController(emptyList())
            else instance!!
        }
    }

    var isOverlay by mutableStateOf(false)

    // Pages
    var pageStack: Stack<NavPage> = Stack()
    private var recentlyOpened: NavPage? = null
    fun registerPage(key: String, onClose: () -> Unit = {}, onOpen: (args: List<Any>) -> Unit = {}) {
        pages.first { it.key == key }.registerCallback(onOpen, onClose)
    }
    fun show(key: String, args: List<Any> = emptyList()) {
        pages.first { it.key == key }.open(args)
        pageStack.push(pages.first { it.key == key })
        isOverlay = true
    }
    fun popLast() {
        if(pageStack.empty()) return
        recentlyOpened = pageStack.pop()
        recentlyOpened?.close()
        isOverlay = pageStack.isNotEmpty()
    }
    fun onTop(pageKey: String) : Boolean {
        return try {
            pageStack.peek().key == pageKey
        } catch (e: EmptyStackException) {
            false
        }
    }
    fun wasOnTop(pageKey: String) : Boolean {
        return recentlyOpened?.key == pageKey
    }

    // Popups
    val toast = Toast()
    val dialog = Dialog()

}