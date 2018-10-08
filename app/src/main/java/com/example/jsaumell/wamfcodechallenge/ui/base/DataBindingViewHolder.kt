package com.example.jsaumell.wamfcodechallenge.ui.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.jsaumell.wamfcodechallenge.BR

class DataBindingViewHolder<T>(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

    fun bind(item: T, onItemClickListener: BaseAdapter.OnItemClickListener<T>? = null) {
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
        onItemClickListener?.let { _ ->
            binding.root.setOnClickListener { onItemClickListener.onItemClick(item) } }
    }
}