package com.example.jsaumell.wamfcodechallenge.ui.photographerslist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jsaumell.wamfcodechallenge.R
import com.example.jsaumell.wamfcodechallenge.ui.SharedViewModel
import com.example.jsaumell.wamfcodechallenge.ui.base.BaseAdapter
import com.example.jsaumell.wamfcodechallenge.ui.model.Photographer
import com.example.jsaumell.wamfcodechallenge.ui.photographerslist.PhotographerListViewModel.Companion.STATE_ERROR
import com.example.jsaumell.wamfcodechallenge.ui.photographerslist.PhotographerListViewModel.Companion.STATE_LOADED
import com.example.jsaumell.wamfcodechallenge.ui.photographerslist.PhotographerListViewModel.Companion.STATE_LOADING
import kotlinx.android.synthetic.main.photographer_list_fragment.*
import kotlinx.android.synthetic.main.photographer_list_fragment.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotographerListFragment : Fragment(), BaseAdapter.OnItemClickListener<Photographer> {

    private val viewModel: PhotographerListViewModel by viewModel()
    private val sharedViewModel: SharedViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.photographer_list_fragment, container, false)
        val context = context ?: return view

        val photographerAdapter = PhotographerListRecyclerViewAdapter(this)

        with(view.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = photographerAdapter
            addItemDecoration(DividerItemDecoration(context, VERTICAL))
        }

        subscribeUi(photographerAdapter)

        return view
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.onListOpened()
    }

    private fun subscribeUi(adapter: PhotographerListRecyclerViewAdapter) {
        viewModel.photographerList.observe(this, Observer { photographerList ->
            Log.d("phlist", "list observed, size: ${photographerList.size}")
            photographerList?.let(adapter::submitList)
        })

        viewModel.state.observe(this, Observer { setViewsState(it) })
    }

    private fun setViewsState(state: Int) {
        when (state) {
            STATE_LOADING -> {
                progressBar.visibility = View.VISIBLE
                errorTextView.visibility = View.GONE
                recyclerView.visibility = View.GONE
            }
            STATE_LOADED -> {
                progressBar.visibility = View.GONE
                errorTextView.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
            STATE_ERROR -> {
                progressBar.visibility = View.GONE
                errorTextView.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }
        }
    }

    override fun onItemClick(item: Photographer) {
        sharedViewModel.onListItemClicked(item)
    }

    companion object {
        fun newInstance() = PhotographerListFragment()
    }
}
