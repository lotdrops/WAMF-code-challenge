package com.example.jsaumell.wamfcodechallenge.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.example.jsaumell.wamfcodechallenge.ui.model.Photographer
import com.example.jsaumell.wamfcodechallenge.ui.model.ToolbarState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SharedViewModelTest {

    @Rule
    @JvmField // for livedata's postValue thread execution
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock lateinit var stateObserver: Observer<ToolbarState>
    @Mock lateinit var selectionObserver: Observer<Photographer>

    private val lifecycle = LifecycleRegistry(Mockito.mock(LifecycleOwner::class.java))

    private val photographer = Photographer(
            0,
            "phName",
            null,
            null,
            null,
            null,
            null,
            null)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    @Test
    fun onListItemClicked() {
        val viewModel = SharedViewModel()
        viewModel.toolbarState.observeForever(stateObserver)
        viewModel.photographerSelectedEvent.observeForever(selectionObserver)
        viewModel.onListItemClicked(photographer)

        assert(viewModel.photographerName == photographer.name)
        assert(viewModel.photographerSelectedEvent.value == photographer)
    }

    @Test
    fun onListOpened() {
        val viewModel = SharedViewModel()
        viewModel.toolbarState.observeForever(stateObserver)

        viewModel.onListOpened()
        assert(viewModel.toolbarState.value?.showUpButton == false)
        assert(viewModel.toolbarState.value?.showAppNameAsTitle == true)
        assert(viewModel.toolbarState.value?.state == SharedViewModel.TOOLBAR_STATE_LIST)
    }

    @Test
    fun onDetailOpened() {
        val viewModel = SharedViewModel()
        viewModel.toolbarState.observeForever(stateObserver)
        viewModel.photographerName = photographer.name

        viewModel.onDetailOpened()
        assert(viewModel.toolbarState.value?.showUpButton == true)
        assert(viewModel.toolbarState.value?.showAppNameAsTitle == false)
        assert(viewModel.toolbarState.value?.title == photographer.name)
        assert(viewModel.toolbarState.value?.state == SharedViewModel.TOOLBAR_STATE_DETAIL)
    }
}