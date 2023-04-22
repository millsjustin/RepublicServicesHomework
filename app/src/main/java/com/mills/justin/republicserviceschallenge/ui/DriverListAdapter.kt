package com.mills.justin.republicserviceschallenge.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mills.justin.republicserviceschallenge.data.Driver
import com.mills.justin.republicserviceschallenge.databinding.ViewHolderDriverItemBinding

class DriverListAdapter(
    private val onClickDriver: (String) -> Unit,
) : ListAdapter<Driver, DriverListAdapter.DriverListViewHolder>(DIFFER) {

    companion object {
        private val DIFFER = object : DiffUtil.ItemCallback<Driver>() {
            override fun areItemsTheSame(oldItem: Driver, newItem: Driver): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Driver, newItem: Driver): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class DriverListViewHolder(
        parent: ViewGroup,
        private val binding: ViewHolderDriverItemBinding = ViewHolderDriverItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Driver) {
            binding.name.text = item.name
            binding.root.setOnClickListener { onClickDriver(item.id) }
        }

        fun recycle() {
            binding.name.text = null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverListViewHolder {
        return DriverListViewHolder(parent)
    }

    override fun onBindViewHolder(holder: DriverListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewRecycled(holder: DriverListViewHolder) {
        holder.recycle()
    }
}