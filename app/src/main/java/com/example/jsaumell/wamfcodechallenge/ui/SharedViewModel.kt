package com.example.jsaumell.wamfcodechallenge.ui

import androidx.lifecycle.ViewModel
import com.example.jsaumell.wamfcodechallenge.ui.model.Photographer
import com.example.jsaumell.wamfcodechallenge.utils.SingleLiveEvent

class SharedViewModel : ViewModel() {

    val photographerSelectedEvent = SingleLiveEvent<Photographer>()

    fun onListItemClicked(item: Photographer) {
        photographerSelectedEvent.postValue(item)
    }
}