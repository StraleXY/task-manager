package com.theminimalismhub.taskmanager.core.navigation.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import com.theminimalismhub.taskmanager.core.navigation.NavigationController

@Composable
fun PageWrapper(
    visible: Boolean,
    controller: NavigationController,
    pageKey: String,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    var onTop by remember { mutableStateOf(false) }
    LaunchedEffect(controller.pageStack.size) { onTop = controller.onTop(pageKey) }

    val animatedScale by animateFloatAsState(targetValue = if(!onTop && !controller.wasOnTop(pageKey) && controller.isOverlay) 0.93f else 1f, tween(225, easing = EaseIn), label = "PageScaling")

    AnimatedVisibility(
        modifier = Modifier.scale(animatedScale),
        visible = visible,
        enter = slideInVertically(tween(250, 150, easing = EaseOut)) { it / 4 } + fadeIn(tween(250, 150, easing = EaseOut)),
        exit = slideOutVertically(tween(250, easing = EaseIn)) { it / 4 } + fadeOut(tween(250, easing = EaseIn)),
        content = content
    )
}