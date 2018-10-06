package com.example.jsaumell.wamfcodechallenge.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jsaumell.wamfcodechallenge.ui.model.ToolbarState
import com.example.jsaumell.wamfcodechallenge.ui.model.Photographer
import com.example.jsaumell.wamfcodechallenge.utils.SingleLiveEvent

class SharedViewModel : ViewModel() {

    var photographerName: String? = null
    val photographerSelectedEvent = SingleLiveEvent<Photographer>()
    val toolbarState = MutableLiveData<ToolbarState>().apply { value = ToolbarState() }

    fun onListItemClicked(item: Photographer) {
        photographerSelectedEvent.postValue(item)
        photographerName = item.name
    }

    fun onListOpened() {
        toolbarState.value = ToolbarState()
    }

    fun onDetailOpened() {
        toolbarState.value = ToolbarState(TOOLBAR_STATE_DETAIL, showUpButton = true,
                showAppNameAsTitle = photographerName == null, title = photographerName)
    }

    companion object {
        const val TOOLBAR_STATE_LIST = 0
        const val TOOLBAR_STATE_DETAIL = 1
    }
}