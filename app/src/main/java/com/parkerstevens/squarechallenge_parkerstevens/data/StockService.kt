package com.parkerstevens.squarechallenge_parkerstevens.data

import com.parkerstevens.squarechallenge_parkerstevens.BuildConfig
import com.parkerstevens.squarechallenge_parkerstevens.data.models.PortfolioResponse
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface StockService {

  @GET("portfolio.json")
  fun getPortfolio(): Single<Response<PortfolioResponse>>

  @GET("portfolio_malformed.json")
  fun getPortfolioMalformed(): Single<Response<PortfolioResponse>>

  @GET("portfolio_empty.json")
  fun getPortfolioEmpty(): Single<Response<PortfolioResponse>>

  companion object {
    // change URL according to local server setup. This IP will point to localhost from an emulator
    private var stockService : StockService? = null
    private val baseUrl = "https://storage.googleapis.com/cash-homework/cash-stocks-api/"

    fun create(): StockService {
      stockService?.let {
        return it
      }
      val logging = HttpLoggingInterceptor()

      if (BuildConfig.DEBUG) logging.level = HttpLoggingInterceptor.Level.BODY

      val retrofit = Retrofit.Builder()
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder().addInterceptor(logging).build()
        )
        .baseUrl(baseUrl)
        .build()
      stockService = retrofit.create(StockService::class.java)
      return stockService!!
    }
  }
}