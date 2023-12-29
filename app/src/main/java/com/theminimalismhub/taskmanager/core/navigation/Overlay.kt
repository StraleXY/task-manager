package com.theminimalismhub.taskmanager.core.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils

@Composable
fun Overlay(
    visible: Boolean,
    alpha: Int = 255,
    onClick: () -> Unit = {}
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(200, easing = EaseIn)),
        exit = fadeOut(tween(200, 400, easing = EaseIn))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(ColorUtils.setAlphaComponent(MaterialTheme.colorScheme.surface.toArgb(), alpha)))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onClick() },
        )
    }
}