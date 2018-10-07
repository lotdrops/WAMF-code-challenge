package com.example.jsaumell.wamfcodechallenge.ui.photographerslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jsaumell.wamfcodechallenge.databinding.PhotographerListFragmentBinding
import com.example.jsaumell.wamfcodechallenge.ui.SharedViewModel
import com.example.jsaumell.wamfcodechallenge.ui.base.BaseAdapter
import com.example.jsaumell.wamfcodechallenge.ui.model.Photographer
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
        val binding = PhotographerListFragmentBinding.inflate(inflater, container, false)
        val context = context ?: return binding.root

        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        val photographerAdapter = PhotographerListRecyclerViewAdapter(this)
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = photographerAdapter
            addItemDecoration(DividerItemDecoration(context, VERTICAL))
        }
        subscribeUi(photographerAdapter)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.onListOpened()
    }

    private fun subscribeUi(adapter: PhotographerListRecyclerViewAdapter) {
        viewModel.photographerList.observe(this, Observer { photographerList ->
            photographerList?.let(adapter::submitList)
        })
    }

    override fun onItemClick(item: Photographer) {
        sharedViewModel.onListItemClicked(item)
    }

    companion object {
        fun newInstance() = PhotographerListFragment()
    }
}
