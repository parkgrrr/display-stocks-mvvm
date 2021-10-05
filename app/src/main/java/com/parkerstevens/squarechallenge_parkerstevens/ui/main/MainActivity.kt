package com.parkerstevens.squarechallenge_parkerstevens.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.parkerstevens.squarechallenge_parkerstevens.BaseActivity
import com.parkerstevens.squarechallenge_parkerstevens.R
import com.parkerstevens.squarechallenge_parkerstevens.databinding.ActivityMainBinding

class MainActivity : BaseActivity<MainVM, ActivityMainBinding>(MainVM::class.java) {
  private val adapter = StocksAdapter()
  private val SUCCESS_SCREEN = 0
  private val ERROR_SCREEN = 1
  override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
    get() = ActivityMainBinding::inflate


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initRecyclerView()
  }

  private fun initRecyclerView() {
    binding.stockList.layoutManager = LinearLayoutManager(this)
    binding.stockList.adapter = adapter
  }

  override fun initViewModel() {
    viewModel.status.observe(this, { status -> observeStatus(status) })
    viewModel.displayPortfolio()
  }

  private fun observeStatus(status: StockStatus) {
    when (status) {
      is StockStatus.Success -> {
        binding.root.displayedChild = SUCCESS_SCREEN
        binding.stocksLoading.visibility = View.GONE
        adapter.updateStocks(status.data.stocks)
      }
      is StockStatus.Loading -> {
        binding.root.displayedChild = SUCCESS_SCREEN
        binding.stocksLoading.visibility = View.VISIBLE
      }
      is StockStatus.Error -> {
        binding.apply {
          root.displayedChild = ERROR_SCREEN
          stocksLoading.visibility = View.GONE
          errorText.text = getText(R.string.stock_error)
        }
      }
      is StockStatus.Empty -> {
        binding.root.displayedChild = ERROR_SCREEN
        binding.stocksLoading.visibility = View.GONE
        binding.root.nextView
        binding.errorText.text = getText(R.string.empty_list)
      }
    }
  }

}