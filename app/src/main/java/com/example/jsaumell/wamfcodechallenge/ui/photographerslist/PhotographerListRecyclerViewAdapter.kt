package com.example.jsaumell.wamfcodechallenge.ui.photographerslist

import androidx.recyclerview.widget.DiffUtil
import com.example.jsaumell.wamfcodechallenge.R
import com.example.jsaumell.wamfcodechallenge.ui.base.BaseAdapter
import com.example.jsaumell.wamfcodechallenge.ui.model.Photographer

class PhotographerListRecyclerViewAdapter(itemClickListener: OnItemClickListener<Photographer>)
    : BaseAdapter<Photographer>(DiffCallback(), itemClickListener) {

    class DiffCallback : DiffUtil.ItemCallback<Photographer>() {
        override fun areItemsTheSame(oldItem: Photographer, newItem: Photographer) =
                oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Photographer, newItem: Photographer): Boolean {
            return oldItem.name == newItem.name
        }
    }

    override fun getItemViewType(position: Int) = R.layout.photographer_list_element
}
