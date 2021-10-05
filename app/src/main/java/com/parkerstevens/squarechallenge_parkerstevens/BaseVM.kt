package com.parkerstevens.squarechallenge_parkerstevens

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import com.parkerstevens.squarechallenge_parkerstevens.data.StockService
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseVM : ViewModel() {
  protected val disposables = CompositeDisposable()
  @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
  val api : StockService = StockService.create()

  override fun onCleared() {
    super.onCleared()
    disposables.clear()
  }

}