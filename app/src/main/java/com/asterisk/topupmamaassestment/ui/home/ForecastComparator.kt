package com.asterisk.topupmamaassestment.ui.home

import androidx.recyclerview.widget.DiffUtil
import com.asterisk.topupmamaassestment.data.models.local.ForecastResponse

class ForecastComparator : DiffUtil.ItemCallback<ForecastResponse>() {
    override fun areItemsTheSame(oldItem: ForecastResponse, newItem: ForecastResponse) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ForecastResponse, newItem: ForecastResponse) =
        oldItem == newItem

}