package com.example.jsaumell.wamfcodechallenge.ui.photographerslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.example.jsaumell.wamfcodechallenge.data.model.Address
import com.example.jsaumell.wamfcodechallenge.data.model.ApiPhotographer
import com.example.jsaumell.wamfcodechallenge.data.model.Company
import com.example.jsaumell.wamfcodechallenge.data.repository.PhotographersRepository
import com.example.jsaumell.wamfcodechallenge.ui.model.Photographer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PhotographerListViewModelTest {

    @Rule
    @JvmField // for livedata's postValue thread execution
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock lateinit var stateObserver: Observer<Int>
    @Mock lateinit var listObserver: Observer<List<Photographer>>

    private val lifecycle = LifecycleRegistry(Mockito.mock(LifecycleOwner::class.java))

    @Mock
    private lateinit var repo: PhotographersRepository

    private val photographersList = listOf(
            ApiPhotographer(
                    id = 0,
                    website = "site.com",
                    address = Address("123", null, null, "city", null),
                    phone = "123",
                    name = "name",
                    company = Company(null, "catchphrase", "company name"),
                    email = "email",
                    username = "username"
            ),
            ApiPhotographer(
                    id = 1,
                    website = null,
                    address = null,
                    phone = null,
                    name = null,
                    company = null,
                    email = null,
                    username = null
            )
    )

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    @Test
    fun init() {
        val viewModel = PhotographerListViewModel(repo)
        viewModel.state.observeForever(stateObserver)

        assert(viewModel.state.value == PhotographerListViewModel.STATE_LOADING)
    }

    @Test
    fun onListReady_correctList() {
        val viewModel = PhotographerListViewModel(repo)
        viewModel.state.observeForever(stateObserver)
        viewModel.photographerList.observeForever(listObserver)

        viewModel.onListReady(photographersList)
        assert(viewModel.state.value == PhotographerListViewModel.STATE_LOADED)
        assert(viewModel.photographerList.value?.size == photographersList.size)
    }

    @Test
    fun onListReady_emptyList() {
        val viewModel = PhotographerListViewModel(repo)
        viewModel.state.observeForever(stateObserver)

        viewModel.onListReady(null)
        assert(viewModel.state.value == PhotographerListViewModel.STATE_ERROR)
    }

    @Test
    fun onListReady_ElementMapping() {
        val viewModel = PhotographerListViewModel(repo)
        viewModel.state.observeForever(stateObserver)
        viewModel.photographerList.observeForever(listObserver)

        viewModel.onListReady(photographersList)

        // check non null values are properly converted
        assert(viewModel.photographerList.value?.get(0)?.name == photographersList[0].name)
        assert(viewModel.photographerList.value?.get(0)?.username == photographersList[0].username)
        assert(viewModel.photographerList.value?.get(0)?.city == photographersList[0].address!!.city)
        assert(viewModel.photographerList.value?.get(0)?.zipcode == photographersList[0].address!!.zipcode)
        assert(viewModel.photographerList.value?.get(0)?.phone == photographersList[0].phone)
        assert(viewModel.photographerList.value?.get(0)?.website == photographersList[0].website)
        assert(viewModel.photographerList.value?.get(0)?.catchphrase == photographersList[0].company!!.catchPhrase)
        // check null becomes empty string
        assert(viewModel.photographerList.value?.get(1)?.name == "")
        assert(viewModel.photographerList.value?.get(1)?.username == "")
        assert(viewModel.photographerList.value?.get(1)?.city == "")
        assert(viewModel.photographerList.value?.get(1)?.zipcode == "")
        assert(viewModel.photographerList.value?.get(1)?.phone == "")
        assert(viewModel.photographerList.value?.get(1)?.website == "")
        assert(viewModel.photographerList.value?.get(1)?.catchphrase == "")
    }

    @Test
    fun onListError() {
        val viewModel = PhotographerListViewModel(repo)
        viewModel.state.observeForever(stateObserver)

        viewModel.onListError()
        assert(viewModel.state.value == PhotographerListViewModel.STATE_ERROR)
    }
}