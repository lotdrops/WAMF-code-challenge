package com.example.jsaumell.wamfcodechallenge.ui.photographerdetail

import androidx.recyclerview.widget.DiffUtil
import com.example.jsaumell.wamfcodechallenge.R
import com.example.jsaumell.wamfcodechallenge.data.model.PhotoInfo
import com.example.jsaumell.wamfcodechallenge.ui.base.BaseAdapter

class PhotographerDetailRecyclerViewAdapter : BaseAdapter<PhotoInfo>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<PhotoInfo>() {
        override fun areItemsTheSame(oldItem: PhotoInfo, newItem: PhotoInfo) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PhotoInfo, newItem: PhotoInfo): Boolean {
            return oldItem.url == newItem.url
        }
    }

    override fun getItemViewType(position: Int) = R.layout.photo_element
}
