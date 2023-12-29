package com.theminimalismhub.taskmanager.core.navigation.popup

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.theminimalismhub.taskmanager.core.navigation.NavigationController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class ToastArgs(
    val text: String = "",
    val actionLabel: String = "",
    val length: Long = 2000,
    val onAction: (() -> Unit)? = null
)

class Toast : PopupItem() {
    private fun make(args: ToastArgs) = open(listOf(args))
    fun make(text: String, length: Long = 2000) = make(ToastArgs(text = text, length = length))
    fun make(text: String, actionLabel: String, length: Long = 2000, onAction: () -> Unit) = make(
        ToastArgs(text = text, actionLabel = actionLabel, length = length, onAction = onAction)
    )
}

@Composable
fun Toast(
    modifier: Modifier = Modifier,
    navigationController: NavigationController
) {
    val scope = rememberCoroutineScope()
    var state by remember { mutableStateOf(ToastArgs()) }

    DisposableEffect(true) {
        Log.d("Toast", "Loading...")
        navigationController.toast.registerCallback {
            state = it[0] as ToastArgs
            scope.launch {
                delay(state.length)
                navigationController.toast.close()
            }
        }
        onDispose { navigationController.toast.unregisterCallback() }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(16.dp))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(if (state.onAction == null) 1f else 0.68f),
                text = state.text,
                style = MaterialTheme.typography.bodyLarge
            )
            state.onAction?.let {
                TextButton(
                    modifier = Modifier.padding(end = 6.dp),
                    onClick = {
                        it()
                        navigationController.toast.close()
                    }
                ) {
                    Text(
                        text = state.actionLabel,
                        style = MaterialTheme.typography.displayMedium
                    )
                }
            }
        }
    }
}