package com.example.jsaumell.wamfcodechallenge.ui.photographerdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jsaumell.wamfcodechallenge.R
import com.example.jsaumell.wamfcodechallenge.databinding.PhotographerDetailFragmentBinding
import com.example.jsaumell.wamfcodechallenge.ui.SharedViewModel
import com.example.jsaumell.wamfcodechallenge.ui.model.Photographer
import com.example.jsaumell.wamfcodechallenge.utils.Constants.Companion.KOIN_PROPERTY_PHOTOGRAPHER
import kotlinx.android.synthetic.main.photographer_detail_fragment.*
import org.koin.android.ext.android.setProperty
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotographerDetailFragment : Fragment() {

    private val viewModel: PhotographerDetailViewModel by viewModel()
    private val sharedViewModel: SharedViewModel by sharedViewModel()
    private var photographer: Photographer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = PhotographerDetailFragmentBinding.inflate(inflater, container, false)
        val context = context ?: return binding.root

        photographer = arguments?.getParcelable(PHOTOGRAPHER_KEY)
        photographer?.let { setProperty(KOIN_PROPERTY_PHOTOGRAPHER, photographer!!) }

        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        val photoInfoAdapter = PhotographerDetailRecyclerViewAdapter()
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = photoInfoAdapter
        }
        subscribeUi(photoInfoAdapter)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.onDetailOpened()
    }

    private fun subscribeUi(adapter: PhotographerDetailRecyclerViewAdapter) {
        viewModel.photoInfoList.observe(this, Observer { photoInfoList ->
            photoInfoList?.let(adapter::submitList)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPhotographerInfo()
    }

    private fun setupPhotographerInfo() {
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
