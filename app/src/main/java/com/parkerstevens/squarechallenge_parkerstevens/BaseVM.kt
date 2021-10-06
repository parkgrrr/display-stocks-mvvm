package com.parkerstevens.squarechallenge_parkerstevens

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import com.parkerstevens.squarechallenge_parkerstevens.data.StockService
import com.parkerstevens.squarechallenge_parkerstevens.data.StocksRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseVM : ViewModel() {
  protected val disposables = CompositeDisposable()

  // TODO: 10/5/21 user constructor injection for the repository with dagger. Viewmodel provider makes this tricky otherwise.
  @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
  val repository: StocksRepository = StocksRepository(StockService.create())

  override fun onCleared() {
    super.onCleared()
    disposables.clear()
  }
}