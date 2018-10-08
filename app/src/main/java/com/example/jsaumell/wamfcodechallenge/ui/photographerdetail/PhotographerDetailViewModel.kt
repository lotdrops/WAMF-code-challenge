package com.example.jsaumell.wamfcodechallenge.ui.photographerdetail

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jsaumell.wamfcodechallenge.data.model.PhotoInfo
import com.example.jsaumell.wamfcodechallenge.data.repository.PhotographersRepository
import com.example.jsaumell.wamfcodechallenge.ui.model.Photographer

class PhotographerDetailViewModel internal constructor(
    repo: PhotographersRepository,
    val photographer: Photographer?
) : ViewModel(), PhotographersRepository.PhotosListCallback {

    val photoInfoList = MediatorLiveData<List<PhotoInfo>>()
    private val viewList = MutableLiveData<ArrayList<PhotoInfo>>()
    val state = MutableLiveData<Int>()

    init {
        state.postValue(STATE_LOADING)
        if (photographer == null) state.postValue(STATE_ERROR)
        else repo.getPhotographerPhotoList(photographer.id, this)
    }

    override fun onListReady(list: List<PhotoInfo>?) {
        viewList.value?.clear()

        if (list == null) onListError()
        else {
            val viewArrayList = ArrayList(list.map {
                it.toCoolerPhotosUrl(photographer?.id ?: 0) })
            viewList.value = viewArrayList

            state.postValue(STATE_LOADED)
            photoInfoList.addSource(viewList, photoInfoList::setValue)
        }
    }

    override fun onListError() { state.postValue(STATE_ERROR) }

    private fun PhotoInfo.toCoolerPhotosUrl(photographerId: Int) = this.copy(
            url = COOLER_PICS_URL + (photographerId * 50 + (this.id ?: 0))
    )

    companion object {
        const val STATE_LOADING = 0
        const val STATE_LOADED = 1
        const val STATE_ERROR = 2

        const val COOLER_PICS_URL = "https://picsum.photos/600/600?image="
    }
}