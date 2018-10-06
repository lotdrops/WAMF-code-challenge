package com.example.jsaumell.wamfcodechallenge.data.repository

import com.example.jsaumell.wamfcodechallenge.data.api.PhotographersService
import com.example.jsaumell.wamfcodechallenge.utils.callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PhotographersRepositoryImpl : PhotographersRepository {

    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val service = retrofit.create(PhotographersService::class.java)

    override fun getPhotographersList(photographersListener: PhotographersRepository.PhotographersListCallback?) {
        val call = service.getListOfPhotographers()

        call.enqueue(callback(
                { r -> photographersListener?.onListReady(r.body()) },
                { photographersListener?.onListError() }
        ))
    }

    override fun getPhotographerPhotoList(photographerId: Int, photosListener: PhotographersRepository.PhotosListCallback?) {
        val call = service.getListOfPhotos(photographerId)

        call.enqueue(callback(
                { r -> photosListener?.onListReady(r.body()) },
                { photosListener?.onListError() }
        ))
    }
}