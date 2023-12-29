package com.theminimalismhub.taskmanager.feature_home_page.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.theminimalismhub.taskmanager.core.navigation.pages.Page

@Composable
fun HomePage(vm: HomePageVM = hiltViewModel()) {
    Page {
        Text(
            text = "Task Manager",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}