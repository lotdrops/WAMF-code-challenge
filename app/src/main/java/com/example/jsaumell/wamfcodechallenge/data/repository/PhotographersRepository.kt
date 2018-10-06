package com.example.jsaumell.wamfcodechallenge.data.repository

import com.example.jsaumell.wamfcodechallenge.data.model.ApiPhotographer
import com.example.jsaumell.wamfcodechallenge.data.model.PhotoInfo

interface PhotographersRepository {

    fun getPhotographersList(photographersListener: PhotographersListCallback?)
    fun getPhotographerPhotoList(photographerId: Int, photosListener: PhotosListCallback?)

    interface PhotographersListCallback {
        fun onListReady(list: List<ApiPhotographer>?)
        fun onListError()
    }

    interface PhotosListCallback {
        fun onListReady(list: List<PhotoInfo>?)
        fun onListError()
    }
}