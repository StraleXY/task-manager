package com.theminimalismhub.taskmanager.core.navigation.popup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun PopUpWrapper(
    visible: Boolean,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(tween(250, 175, easing = EaseOut)) { it / 4 } + fadeIn(tween(250, 175, easing = EaseOut)),
        exit = slideOutVertically(tween(250, easing = EaseIn)) { it / 4 } + fadeOut(tween(250, easing = EaseIn)),
    ) { Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) { content() } }
}