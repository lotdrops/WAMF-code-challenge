package com.example.jsaumell.wamfcodechallenge.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.jsaumell.wamfcodechallenge.R
import com.example.jsaumell.wamfcodechallenge.ui.model.ToolbarState
import com.example.jsaumell.wamfcodechallenge.ui.photographerdetail.PhotographerDetailFragment
import com.example.jsaumell.wamfcodechallenge.ui.photographerslist.PhotographerListFragment
import kotlinx.android.synthetic.main.activity_main.toolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val sharedViewModel: SharedViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        PhotographerListFragment.newInstance().navigate(LIST_FRAGMENT_TAG, false)

        sharedViewModel.photographerSelectedEvent.observe(this, Observer {
            PhotographerDetailFragment.newInstance(it).navigate(DETAIL_FRAGMENT_TAG, true) })

        subscribeUi()
    }

    private fun subscribeUi() {
        sharedViewModel.toolbarState.observe(this, Observer { setToolbarState(it) })
    }

    private fun setToolbarState(state: ToolbarState) {
        supportActionBar?.setDisplayHomeAsUpEnabled(state.showUpButton)
        if (state.showAppNameAsTitle or (state.title == null)) toolbar.setTitle(R.string.app_name)
        else toolbar.title = state.title
    }

    private fun Fragment.navigate(tag: String, addToBackStack: Boolean = true) {
        val transaction = supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentFrameLayout, this, tag)
        if (addToBackStack) transaction.addToBackStack(tag)
        transaction.commit()
    }

    companion object {
        const val LIST_FRAGMENT_TAG = "LIST_FRAGMENT_TAG"
        const val DETAIL_FRAGMENT_TAG = "DETAIL_FRAGMENT_TAG"
    }
}
