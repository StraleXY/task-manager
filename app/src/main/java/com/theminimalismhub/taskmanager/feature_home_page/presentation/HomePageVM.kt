package com.theminimalismhub.taskmanager.feature_home_page.presentation

import androidx.lifecycle.ViewModel
import com.theminimalismhub.taskmanager.core.navigation.NavigationController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomePageVM @Inject constructor(
    val navigationController: NavigationController
) : ViewModel()