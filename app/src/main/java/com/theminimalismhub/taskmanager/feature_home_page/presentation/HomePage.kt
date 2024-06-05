package com.theminimalismhub.taskmanager.feature_home_page.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import com.theminimalismhub.taskmanager.core.consts.Padding
import com.theminimalismhub.taskmanager.feature_assignments.presentation.AssignmentsPage
import com.theminimalismhub.taskmanager.feature_my_day.presentation.MyDayPage

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePage() {

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 2 }
    )
    val progress by remember { derivedStateOf { pagerState.getOffsetFractionForPage(pagerState.currentPage) } }

    Column {
        Spacer(modifier = Modifier.height(Padding.SECTION_LARGE))
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            state = pagerState
        ) {
           Text(
               modifier = Modifier
                   .fillMaxWidth()
                   .alpha(0.85f),
               text = if(it == 0) "Events" else "Assignments",
               style = MaterialTheme.typography.headlineMedium,
               textAlign = TextAlign.Center
           )
        }
        Box(

        ) {
            Column(Modifier.alpha(if(progress == 0f && pagerState.currentPage == 0) 1f else if (progress <= 0) 0f else progress * -2 + 1 )) {
                MyDayPage()
            }
            Column(Modifier.alpha(if(progress == 0f && pagerState.currentPage == 1) 1f else if(progress >= 0) 0f else progress * 2 + 1 )) {
                AssignmentsPage()
            }
        }

    }
}