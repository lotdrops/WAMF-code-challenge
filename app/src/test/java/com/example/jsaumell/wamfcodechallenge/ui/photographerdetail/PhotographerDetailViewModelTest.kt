package com.example.jsaumell.wamfcodechallenge.ui.photographerdetail

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.example.jsaumell.wamfcodechallenge.data.repository.PhotographersRepository
import com.example.jsaumell.wamfcodechallenge.data.repository.PhotographersRepositoryImpl
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.jsaumell.wamfcodechallenge.data.model.PhotoInfo
import com.example.jsaumell.wamfcodechallenge.ui.model.Photographer
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

class PhotographerDetailViewModelTest {

    @Rule @JvmField // for livedata's postValue thread execution
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock private lateinit var repo: PhotographersRepository

    @Mock lateinit var stateObserver: Observer<Int>
    @Mock lateinit var listObserver: Observer<List<PhotoInfo>>

    private val photographer = Photographer(0, "Name", "username", "City", "10100", "123456789", "site.com", "I am a catchphrase")
    private val photoList = listOf(
            PhotoInfo(albumId = 0, id = 0),
            PhotoInfo(albumId = 0, id = 1)
    )

    private val lifecycle = LifecycleRegistry(mock(LifecycleOwner::class.java))

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    @Test
    fun init_nullPhotographer() {
        val viewModel = PhotographerDetailViewModel(PhotographersRepositoryImpl(), null)
        viewModel.state.observeForever(stateObserver)

        assert(viewModel.state.value == PhotographerDetailViewModel.STATE_ERROR)
    }

    @Test
    fun init_nonNullPhotographer() {
        val viewModel = PhotographerDetailViewModel(repo, photographer)
        viewModel.state.observeForever(stateObserver)

        assert(viewModel.state.value == PhotographerDetailViewModel.STATE_LOADING)
        verify(repo, times(1)).getPhotographerPhotoList(photographer.id, viewModel)
    }

    @Test
    fun onListReady_correctList() {
        val viewModel = PhotographerDetailViewModel(PhotographersRepositoryImpl(), photographer)
        viewModel.state.observeForever(stateObserver)
        viewModel.getPhotoInfo().observeForever(listObserver)

        assert(viewModel.state.value == PhotographerDetailViewModel.STATE_LOADING)
        viewModel.onListReady(photoList)
        assert(viewModel.state.value == PhotographerDetailViewModel.STATE_LOADED)
        assert(viewModel.getPhotoInfo().value?.size == photoList.size)
    }

    @Test
    fun onListReady_emptyList() {
        val viewModel = PhotographerDetailViewModel(PhotographersRepositoryImpl(), photographer)
        viewModel.state.observeForever(stateObserver)

        assert(viewModel.state.value == PhotographerDetailViewModel.STATE_LOADING)
        viewModel.onListReady(null)
        assert(viewModel.state.value == PhotographerDetailViewModel.STATE_ERROR)
    }

    @Test
    fun onListError() {
        val viewModel = PhotographerDetailViewModel(PhotographersRepositoryImpl(), photographer)
        viewModel.state.observeForever(stateObserver)

        assert(viewModel.state.value == PhotographerDetailViewModel.STATE_LOADING)
        viewModel.onListError()
        assert(viewModel.state.value == PhotographerDetailViewModel.STATE_ERROR)
    }
}