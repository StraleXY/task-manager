package com.theminimalismhub.taskmanager.core.navigation.popup

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.theminimalismhub.taskmanager.core.navigation.NavigationController

data class DialogArgs(
    val title: String = "",
    val description: String = "",
    @DrawableRes val icon: Int? = null,
    val actionLabel: String = "",
    val onAction: (() -> Unit)? = null,
    val content: @Composable () -> Unit = { }
)

class Dialog : PopupItem() {
    private fun make(args: DialogArgs) = open(listOf(args))

    fun info(title: String, description: String, @DrawableRes icon: Int?) = make(DialogArgs(title = title, description = description, icon = icon))

    fun action(title: String, description: String, actionLabel: String, @DrawableRes icon: Int? = null, onAction: () -> Unit, content: @Composable () -> Unit = {})
        = make(DialogArgs(title = title, description = description, icon = icon, actionLabel = actionLabel, onAction = onAction, content = content))

    fun delete(onAction: () -> Unit) = make(
        DialogArgs(
        title = "Confirm Deletion",
        description = "Are you sure that you want to delete this item? This action is not reversible.",
        actionLabel = "DELETE",
        onAction = onAction
    )
    )
}

@Composable
fun Dialog(
    modifier: Modifier = Modifier,
    navigationController: NavigationController
) {
    var state by remember { mutableStateOf(DialogArgs()) }

    DisposableEffect(true) {
        navigationController.dialog.registerCallback { state = it[0] as DialogArgs }
        onDispose { navigationController.dialog.unregisterCallback() }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 24.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                state.icon?.let { 
                    Icon(modifier = Modifier.size(24.dp), painter = painterResource(id = it), contentDescription = "Dialog icon")
                    Spacer(modifier = Modifier.width(6.dp))
                }
                Text(
                    text = state.title,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Start
                )
            } 
            Spacer(modifier = Modifier.height(12.dp))
            if(state.description.isNotEmpty()) Text(
                text = state.description,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(12.dp))
            state.content()
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                OutlinedButton(onClick = { navigationController.dialog.close() }) { Text(text = "CLOSE", style = MaterialTheme.typography.displayMedium) }
                state.onAction?.let {
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            it()
                            navigationController.dialog.close()
                        }
                    ) { Text(text = state.actionLabel, style = MaterialTheme.typography.displayMedium) }
                }
            }
        }
    }
}