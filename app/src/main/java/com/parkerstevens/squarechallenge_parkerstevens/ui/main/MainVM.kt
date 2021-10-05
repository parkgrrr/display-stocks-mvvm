package com.parkerstevens.squarechallenge_parkerstevens.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.parkerstevens.squarechallenge_parkerstevens.BaseVM
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainVM : BaseVM() {

  private val _status: MutableLiveData<StockStatus> = MutableLiveData()

  // immutable status is exposed so the view can't change the value directly
  val status: LiveData<StockStatus> get() = _status

  fun displayPortfolio() {
    // TODO: 10/4/21 The recruiter told me not to implement a cache, but a simple memory cache could be used here to not fetch the stocks again on rotation
    fetchStocks()
  }

  private fun fetchStocks() {
    disposables.addAll(
        // change the api method to test error/empty
        api.getPortfolio()
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .doOnSubscribe {
            _status.postValue(StockStatus.Loading())
          }
          .subscribe(
              {
                if (it.isSuccessful) {
                  if (it.body() != null && it.body()!!.stocks.isNotEmpty()) {
                    _status.value = StockStatus.Success(it.body()!!)
                  } else {
                    // response body is empty or null
                    _status.value = StockStatus.Empty()
                  }
                } else {
                  // response code is not successful
                  _status.value = StockStatus.Error(it.toString())
                }
              },
              {
                // error other than http (malformed json)
                _status.value = StockStatus.Error(it.toString())
              }
          )
    )
  }
}