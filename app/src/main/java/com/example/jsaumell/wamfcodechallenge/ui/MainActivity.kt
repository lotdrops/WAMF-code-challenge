package com.example.jsaumell.wamfcodechallenge.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.jsaumell.wamfcodechallenge.R
import com.example.jsaumell.wamfcodechallenge.ui.photographerdetail.PhotographerDetailFragment
import com.example.jsaumell.wamfcodechallenge.ui.photographerslist.PhotographerListFragment
import kotlinx.android.synthetic.main.activity_main.toolbar

class MainActivity : AppCompatActivity() {

    private val sharedViewModel: SharedViewModel by lazy { ViewModelProviders.of(this, null).get(SharedViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        PhotographerListFragment.newInstance().navigate(LIST_FRAGMENT_TAG, true)

        sharedViewModel.photographerSelectedEvent.observe(this, Observer {
            PhotographerDetailFragment.newInstance(it).navigate(DETAIL_FRAGMENT_TAG, true) })
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
