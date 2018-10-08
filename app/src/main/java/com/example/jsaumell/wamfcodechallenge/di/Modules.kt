package com.example.jsaumell.wamfcodechallenge.di

import com.example.jsaumell.wamfcodechallenge.data.repository.PhotographersRepository
import com.example.jsaumell.wamfcodechallenge.data.repository.PhotographersRepositoryImpl
import com.example.jsaumell.wamfcodechallenge.ui.SharedViewModel
import com.example.jsaumell.wamfcodechallenge.ui.photographerdetail.PhotographerDetailViewModel
import com.example.jsaumell.wamfcodechallenge.ui.photographerslist.PhotographerListViewModel
import com.example.jsaumell.wamfcodechallenge.utils.Constants
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {

    viewModel { SharedViewModel() }
    viewModel { PhotographerListViewModel(get()) }
    viewModel { PhotographerDetailViewModel(get(), getProperty(Constants.KOIN_PROPERTY_PHOTOGRAPHER)) }
    single<PhotographersRepository> { PhotographersRepositoryImpl() }
}