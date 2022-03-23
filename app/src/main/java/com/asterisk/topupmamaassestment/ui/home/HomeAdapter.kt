package com.asterisk.topupmamaassestment.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.asterisk.topupmamaassestment.data.models.ForecastResponse
import com.asterisk.topupmamaassestment.databinding.ForecastListItemBinding

class HomeAdapter : ListAdapter<ForecastResponse, ForecastViewHolder>(ForecastComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val layout = ForecastListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ForecastViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class ForecastComparator : DiffUtil.ItemCallback<ForecastResponse>() {
        override fun areItemsTheSame(oldItem: ForecastResponse, newItem: ForecastResponse) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ForecastResponse, newItem: ForecastResponse) =
            oldItem == newItem

    }

}