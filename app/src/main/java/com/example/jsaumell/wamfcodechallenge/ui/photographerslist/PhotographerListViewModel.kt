package com.example.jsaumell.wamfcodechallenge.ui.photographerslist

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jsaumell.wamfcodechallenge.data.model.ApiPhotographer
import com.example.jsaumell.wamfcodechallenge.data.repository.PhotographersRepository
import com.example.jsaumell.wamfcodechallenge.ui.model.Photographer

class PhotographerListViewModel internal constructor(repo: PhotographersRepository) :
        ViewModel(), PhotographersRepository.PhotographersListCallback {

    val photographerList = MediatorLiveData<List<Photographer>>()
    private val viewList = MutableLiveData<ArrayList<Photographer>>()
    val state = MutableLiveData<Int>()

    init {
        state.postValue(STATE_LOADING)
        repo.getPhotographersList(this)
    }

    override fun onListReady(list: List<ApiPhotographer>?) {
        viewList.value?.clear()

        if (list == null) onListError()
        else {
            val viewArrayList = ArrayList(list.map { it.toViewPhotographer() })
            viewList.value = viewArrayList

            state.postValue(STATE_LOADED)
            photographerList.addSource(viewList, photographerList::setValue)
        }
    }

    override fun onListError() { state.postValue(STATE_ERROR) }

    private fun ApiPhotographer.toViewPhotographer() =
            Photographer(
                    id = id,
                    name = name ?: "",
                    username = username ?: "",
                    city = address?.city ?: "",
                    zipcode = address?.zipcode ?: "",
                    phone = phone ?: "",
                    website = website ?: "",
                    catchphrase = company?.catchPhrase ?: ""
            )

    companion object {
        const val STATE_LOADING = 0
        const val STATE_LOADED = 1
        const val STATE_ERROR = 2
    }
}