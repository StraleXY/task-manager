package com.theminimalismhub.taskmanager.core.navigation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoreViewVM @Inject constructor(
    val navigationController: NavigationController
) : ViewModel() { }