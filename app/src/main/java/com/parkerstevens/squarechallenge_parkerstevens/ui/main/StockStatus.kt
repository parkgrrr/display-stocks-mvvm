package com.parkerstevens.squarechallenge_parkerstevens.ui.main

import com.parkerstevens.squarechallenge_parkerstevens.data.models.PortfolioResponse

sealed class StockStatus {
  class Loading : StockStatus()
  data class Error(val Error: String) : StockStatus()
  data class Success(val data : PortfolioResponse) : StockStatus()
  class Empty : StockStatus()
}
