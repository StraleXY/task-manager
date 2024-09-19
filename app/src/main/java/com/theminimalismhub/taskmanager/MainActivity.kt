package com.theminimalismhub.taskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updatePadding
import com.theminimalismhub.taskmanager.core.navigation.CoreViewGUI
import com.theminimalismhub.taskmanager.core.navigation.NavigationController
import com.theminimalismhub.taskmanager.core.navigation.pages.NavPage
import com.theminimalismhub.taskmanager.feature_home_page.presentation.HomePage
import com.theminimalismhub.taskmanager.feature_my_day.presentation.MyDayPage
import com.theminimalismhub.taskmanager.feature_settings.presentation.SettingsPage
import com.theminimalismhub.taskmanager.ui.theme.TaskManagerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { view, windowInsets ->
            windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(insets.left, 0, insets.right, insets.bottom)
            WindowInsetsCompat.CONSUMED
        }

        super.onCreate(savedInstanceState)

        setContent {
            TaskManagerTheme {
                NavigationController.init(listOf(
                    NavPage(Pages.SETTINGS_KEY) { SettingsPage() }
                ))
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    CoreViewGUI {
                        HomePage()
                    }
                }
            }
        }
    }
}

object Pages {
    const val HOME_KEY = "home_page_key"
    const val SETTINGS_KEY = "settings_page_key"
}