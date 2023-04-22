package com.mills.justin.republicserviceschallenge.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mills.justin.republicserviceschallenge.R
import com.mills.justin.republicserviceschallenge.data.Route
import com.mills.justin.republicserviceschallenge.databinding.ViewHolderRouteItemBinding

class RouteListAdapter(
) : ListAdapter<Route, RouteListAdapter.RouteListViewHolder>(DIFFER) {

    companion object {
        private val DIFFER = object : DiffUtil.ItemCallback<Route>() {
            override fun areItemsTheSame(oldItem: Route, newItem: Route): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Route, newItem: Route): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class RouteListViewHolder(
        parent: ViewGroup,
        private val binding: ViewHolderRouteItemBinding = ViewHolderRouteItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        private val context: Context get() = itemView.context

        fun bind(item: Route) {
            binding.name.text = item.name
            binding.type.text = context.getString(R.string.route_type, item.type)
        }

        fun recycle() {
            binding.name.text = null
            binding.type.text = null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteListViewHolder {
        return RouteListViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RouteListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewRecycled(holder: RouteListViewHolder) {
        holder.recycle()
    }
}