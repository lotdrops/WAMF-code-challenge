package com.example.jsaumell.wamfcodechallenge.ui.photographerdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jsaumell.wamfcodechallenge.R
import com.example.jsaumell.wamfcodechallenge.ui.model.Photographer
import com.example.jsaumell.wamfcodechallenge.ui.photographerdetail.PhotographerDetailViewModel.Companion.STATE_ERROR
import com.example.jsaumell.wamfcodechallenge.ui.photographerdetail.PhotographerDetailViewModel.Companion.STATE_LOADED
import com.example.jsaumell.wamfcodechallenge.ui.photographerdetail.PhotographerDetailViewModel.Companion.STATE_LOADING
import kotlinx.android.synthetic.main.photographer_detail_fragment.*
import kotlinx.android.synthetic.main.photographer_detail_fragment.view.*
import org.koin.android.ext.android.setProperty
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotographerDetailFragment : Fragment() {

    private val viewModel: PhotographerDetailViewModel by viewModel()
    private var photographer: Photographer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.photographer_detail_fragment, container, false)
        val context = context ?: return view

        photographer = arguments?.getParcelable(PHOTOGRAPHER_KEY)
        photographer?.let { setProperty("photographer", photographer!!) }

        val photoInfoAdapter = PhotographerDetailRecyclerViewAdapter()
        with(view.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = photoInfoAdapter
        }

        subscribeUi(photoInfoAdapter)

        return view
    }

    private fun subscribeUi(adapter: PhotographerDetailRecyclerViewAdapter) {
        viewModel.getPhotoInfo().observe(this, Observer { photoInfoList ->
            photoInfoList?.let(adapter::submitList)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPhotographerInfo()
    }

    private fun setupPhotographerInfo() {
        name.text = photographer?.name
        username.text = photographer?.username
        address.text = getString(R.string.photographer_detail_address, photographer?.city, photographer?.zipcode)
        phone.text = photographer?.phone
        website.text = photographer?.website
        catchphrase.text = photographer?.catchphrase
    }

    companion object {

        const val PHOTOGRAPHER_KEY = "PHOTOGRAPHER"

        fun newInstance(photographer: Photographer) = PhotographerDetailFragment().apply {
            arguments = Bundle().apply { putParcelable(PHOTOGRAPHER_KEY, photographer) }
        }
    }
}
