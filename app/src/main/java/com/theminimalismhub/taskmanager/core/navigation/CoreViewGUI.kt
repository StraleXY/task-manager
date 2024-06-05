package com.theminimalismhub.taskmanager.core.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.theminimalismhub.taskmanager.core.navigation.popup.Toast
import com.theminimalismhub.taskmanager.core.consts.Screen
import com.theminimalismhub.taskmanager.core.navigation.pages.PageWrapper
import com.theminimalismhub.taskmanager.core.navigation.popup.Dialog
import com.theminimalismhub.taskmanager.core.navigation.popup.PopUpWrapper
import kotlinx.coroutines.delay

@Composable
fun CoreViewGUI(
    vm: CoreViewVM = hiltViewModel(),
    backdrop: (@Composable AnimatedVisibilityScope.() -> Unit)? = null,
    splashscreen: (@Composable AnimatedVisibilityScope.() -> Unit)? = null,
    homepage: @Composable BoxScope.() -> Unit
) {

    val screen = LocalConfiguration.current
    val density = LocalDensity.current
    val animatedScale by animateFloatAsState(targetValue = if(vm.navigationController.isOverlay) 0.95f else 1f, tween(175, delayMillis = if(vm.navigationController.isOverlay) 0 else 400))
    var isBackdropVisible by remember { mutableStateOf(false) }
    val animatedOffset by animateDpAsState(targetValue = if(isBackdropVisible) (screen.screenHeightDp - 48).dp else 0.dp, tween(450))

    var isSplashScreenVisible by remember { mutableStateOf(splashscreen != null) }

    BackHandler(enabled = vm.navigationController.isOverlay) { vm.navigationController.popLast() }
    BackHandler(enabled = vm.navigationController.dialog.visible) { vm.navigationController.dialog.close() }

    LaunchedEffect(true) {
        if(splashscreen != null) {
            delay(800)
            isSplashScreenVisible = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        backdrop?.let {
            AnimatedVisibility(
                visible = isBackdropVisible,
                enter = fadeIn(tween(450)) + slideInVertically(tween(450)) { -screen.screenHeightDp - 72 },
                exit = fadeOut(tween(450)) + slideOutVertically(tween(450)) { -screen.screenHeightDp - 72 }
            ) { it() }
        }
        Box(modifier = Modifier
            .fillMaxSize()
            .onSizeChanged {
                Screen.setSize(
                    width = (it.width / density.density).dp,
                    height = (it.height / density.density).dp
                )
            }
            .offset(y = animatedOffset)
            .clip(RoundedCornerShape(8.dp))
            .scale(animatedScale)
        ) { homepage() }

        Overlay(visible = vm.navigationController.isOverlay)
        AnimatedVisibility(visible = vm.navigationController.pages.isNotEmpty(), enter = fadeIn(tween(0)), exit = fadeOut(tween(0))) {
            vm.navigationController.pages.forEach { PageWrapper(visible = it.visible, controller = vm.navigationController, pageKey = it.key) { it.composable() } }
        }

        PopUpWrapper(visible = vm.navigationController.toast.visible) { Toast(navigationController = vm.navigationController) }
        Overlay(visible = vm.navigationController.dialog.visible, alpha = 200) { vm.navigationController.dialog.close() }
        PopUpWrapper(visible = vm.navigationController.dialog.visible) { Dialog(navigationController = vm.navigationController) }

        splashscreen?.let {
            AnimatedVisibility(
                visible = isSplashScreenVisible,
                enter = fadeIn(tween(0)),
                exit = fadeOut(tween(800))
            ) { it() }
        }
    }
}