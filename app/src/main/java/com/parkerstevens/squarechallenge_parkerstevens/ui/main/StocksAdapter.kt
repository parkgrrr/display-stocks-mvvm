package com.parkerstevens.squarechallenge_parkerstevens.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.parkerstevens.squarechallenge_parkerstevens.R
import com.parkerstevens.squarechallenge_parkerstevens.data.models.Stock
import com.parkerstevens.squarechallenge_parkerstevens.databinding.StockItemBinding
import com.parkerstevens.squarechallenge_parkerstevens.toCurrency
import com.parkerstevens.squarechallenge_parkerstevens.toTime

class StocksAdapter : RecyclerView.Adapter<StocksAdapter.StocksViewHolder>() {
  lateinit var binding : StockItemBinding
  private var stocksList : List<Stock> = listOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StocksViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    binding = StockItemBinding.inflate(inflater, parent, false)
    return StocksViewHolder(binding.root)
  }

  override fun onBindViewHolder(holder: StocksViewHolder, position: Int) {
    val stock = stocksList[position]
    holder.apply {
      tickerText.text = stock.ticker
      companyText.text = stock.name
      priceText.text = stock.cents.toCurrency(stock.currency)
      quantityText.text = stock.quantity?.toString() ?: binding.root.context.getText(R.string.quantity_not_available)
      currencyText.text = stock.currency
      timeText.text = stock.timestamp.toTime()

    }

  }

  override fun getItemCount(): Int {
    return stocksList.size
  }

  fun updateStocks(stocks : List<Stock>) {
    stocksList = stocks
    notifyDataSetChanged()
    // TODO: 10/4/21 add diffutil to update prices/values that have changes more efficiently
  }

  inner class StocksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tickerText = binding.tickerTv
    val priceText = binding.priceTv
    val quantityText = binding.quantityTv
    val companyText = binding.companyTv
    val currencyText = binding.currencyTv
    val timeText = binding.timeTv
  }
}

