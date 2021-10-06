package com.parkerstevens.squarechallenge_parkerstevens.ui.main

import androidx.recyclerview.widget.DiffUtil
import com.parkerstevens.squarechallenge_parkerstevens.data.models.Stock

class StocksDiff(val oldStocks: List<Stock>, val newStocks: List<Stock>) : DiffUtil.Callback() {
  override fun getOldListSize(): Int {
    return oldStocks.size
  }

  override fun getNewListSize(): Int {
    return newStocks.size
  }

  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    return oldStocks[oldItemPosition].ticker == newStocks[newItemPosition].ticker
  }

  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    return oldStocks[oldItemPosition] == newStocks[newItemPosition]
  }
}