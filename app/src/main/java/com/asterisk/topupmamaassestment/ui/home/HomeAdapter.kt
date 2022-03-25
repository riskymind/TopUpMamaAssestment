package com.asterisk.topupmamaassestment.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.asterisk.topupmamaassestment.data.models.local.ForecastResponse
import com.asterisk.topupmamaassestment.databinding.ForecastListItemBinding

class HomeAdapter(
    private val onItemClicked: (ForecastResponse) -> Unit,
    private val onFavClicked: (ForecastResponse) -> Unit
) : ListAdapter<ForecastResponse, ForecastViewHolder>(ForecastComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val layout = ForecastListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ForecastViewHolder(
            layout,
            onFavIconClicked = { position ->
                val forecast = getItem(position)
                if (forecast != null) {
                    onFavClicked(forecast)
                }
            },
            itemClicked = { position ->
                val forecast = getItem(position)
                if (forecast != null) {
                    onItemClicked(forecast)
                }
            }
        )
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

}