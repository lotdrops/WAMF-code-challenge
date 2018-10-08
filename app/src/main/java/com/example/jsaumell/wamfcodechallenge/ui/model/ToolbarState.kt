package com.example.jsaumell.wamfcodechallenge.ui.model

import com.example.jsaumell.wamfcodechallenge.ui.SharedViewModel.Companion.TOOLBAR_STATE_LIST

data class ToolbarState(
    val state: Int = TOOLBAR_STATE_LIST,
    val showUpButton: Boolean = false,
    val showAppNameAsTitle: Boolean = true,
    val title: String? = null
)