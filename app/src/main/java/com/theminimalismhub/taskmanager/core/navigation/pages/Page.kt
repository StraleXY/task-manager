package com.theminimalismhub.taskmanager.core.navigation.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.theminimalismhub.taskmanager.core.consts.Padding

@Composable
fun Page(
    modifier: Modifier = Modifier,
    content: @Composable LazyItemScope.() -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .then(modifier),
        contentPadding = PaddingValues(horizontal = Padding.PAGE_H, vertical = Padding.PAGE_V)
    ) { item { content() } }
}

@Composable
fun ColumnPage(
    modifier: Modifier = Modifier,
    content: @Composable LazyItemScope.() -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .then(modifier),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(horizontal = Padding.PAGE_H, vertical = Padding.PAGE_V)
    ) {
        item {
            Spacer(modifier = Modifier.height(Padding.PAGE_H))
            content()
            Spacer(modifier = Modifier.height(Padding.ITEM_S))
        }
    }
}