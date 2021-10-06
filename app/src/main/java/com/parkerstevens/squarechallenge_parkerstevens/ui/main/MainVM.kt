package com.parkerstevens.squarechallenge_parkerstevens.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.parkerstevens.squarechallenge_parkerstevens.BaseVM

class MainVM : BaseVM() {

  private val _status: MutableLiveData<StockStatus> = MutableLiveData()

  // immutable status is exposed so the view can't change the value directly
  val status: LiveData<StockStatus> get() = _status

  fun displayPortfolio() {
    // TODO: 10/4/21 The recruiter told me not to implement a cache, but a simple memory cache could be used here or in the repository to not call the stocks api again on rotation
    repository.getStocks(_status)?.let {
      disposables.add(it)
    }
  }
}