package com.parkerstevens.squarechallenge_parkerstevens.data

import androidx.lifecycle.MutableLiveData
import com.parkerstevens.squarechallenge_parkerstevens.ui.main.StockStatus
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

// TODO: 10/5/21 change this to a dagger singleton with DI
object StocksRepository {
  private lateinit var api: StockService

  operator fun invoke(stockService: StockService): StocksRepository {
    api = stockService
    return this
  }

  fun getStocks(_status: MutableLiveData<StockStatus>): Disposable? {
    // TODO: 10/5/21 add caching logic here
    return api.getPortfolio()
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
  }
}