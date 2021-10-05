package com.parkerstevens.squarechallenge_parkerstevens.main

import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.parkerstevens.squarechallenge_parkerstevens.BaseTest
import com.parkerstevens.squarechallenge_parkerstevens.data.StockService
import com.parkerstevens.squarechallenge_parkerstevens.data.models.PortfolioResponse
import com.parkerstevens.squarechallenge_parkerstevens.ui.main.MainVM
import com.parkerstevens.squarechallenge_parkerstevens.ui.main.StockStatus
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.spyk
import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class MainVMTest : BaseTest() {

  @RelaxedMockK
  private lateinit var mapi: StockService
  private lateinit var viewModel: MainVM

  @Before
  fun setup() {

  }

  private fun setupVM(responseString : String, shouldError : Boolean = false) {
    mapi = object :StockService {
      override fun getPortfolio(): Single<Response<PortfolioResponse>> {
        val portfolio = Gson().fromJson(responseString, PortfolioResponse::class.java)
        val response = if (!shouldError) {
          Response.success(portfolio)
        } else {
          Response.error(400, "error".toResponseBody())
        }

        return Single.just(response)
      }

      override fun getPortfolioMalformed(): Single<Response<PortfolioResponse>> {
        TODO("This method wouldn't exist in production")
      }

      override fun getPortfolioEmpty(): Single<Response<PortfolioResponse>> {
        TODO("This method wouldn't exist in production")
      }
    }

    viewModel = spyk<MainVM> {
      every { api } returns mapi
    }
  }

  @Test
  fun `Check that good response from server returns a success status`() {
    val observer = Observer<StockStatus> {}
    setupVM(GOOD_RESPONSE)
    try {
      viewModel.displayPortfolio()
      viewModel.status.observeForever(observer)
      val value = viewModel.status.value
      assert(value is StockStatus.Success)

    }
    finally {
      viewModel.status.removeObserver(observer)
    }
  }

  @Test
  fun `Check that empty response from server returns an empty status`() {
    val observer = Observer<StockStatus> {}
    setupVM(EMPTY_RESPONSE)

    try {
      viewModel.displayPortfolio()
      viewModel.status.observeForever(observer)
      val value = viewModel.status.value
      assert(value is StockStatus.Empty)

    }
    finally {
      viewModel.status.removeObserver(observer)
    }
  }

  @Test
  fun `Check that error response from server returns an error status`() {
    val observer = Observer<StockStatus> {}
    setupVM(GOOD_RESPONSE, true)

    try {
      viewModel.displayPortfolio()
      viewModel.status.observeForever(observer)
      val value = viewModel.status.value
      assert(value is StockStatus.Error)

    }
    finally {
      viewModel.status.removeObserver(observer)
    }
  }

  companion object {
    const val GOOD_RESPONSE = """
      {"stocks":[{"ticker":"TWTR","name":"Twitter, Inc.","currency":"USD","current_price_cents":3833,"quantity":1,"current_price_timestamp":1597942580},{"ticker":"^GSPC","name":"S&P 500","currency":"USD","current_price_cents":318157,"quantity":null,"current_price_timestamp":1597942580},{"ticker":"BAC","name":"Bank of America Corporation","currency":"USD","current_price_cents":2393,"quantity":10,"current_price_timestamp":1597942580},{"ticker":"EXPE","name":"Expedia Group, Inc.","currency":"USD","current_price_cents":8165,"quantity":4,"current_price_timestamp":1597942580},{"ticker":"GRUB","name":"Grubhub Inc.","currency":"USD","current_price_cents":6975,"quantity":null,"current_price_timestamp":1597942580},{"ticker":"FIT","name":"Fitbit, Inc.","currency":"USD","current_price_cents":678,"quantity":null,"current_price_timestamp":1597942580},{"ticker":"UA","name":"Under Armour, Inc.","currency":"USD","current_price_cents":844,"quantity":null,"current_price_timestamp":1597942580},{"ticker":"VTI","name":"Vanguard Total Stock Market Index Fund ETF Shares","currency":"USD","current_price_cents":15994,"quantity":null,"current_price_timestamp":1597942580},{"ticker":"VWO","name":"Vanguard FTSE Emerging Markets","currency":"USD","current_price_cents":4283,"quantity":10,"current_price_timestamp":1597942580},{"ticker":"JNJ","name":"Johnson & Johnson","currency":"USD","current_price_cents":14740,"quantity":null,"current_price_timestamp":1597942580},{"ticker":"BRKA","name":"Berkshire Hathaway Inc.","currency":"USD","current_price_cents":28100000,"quantity":1,"current_price_timestamp":1597942580},{"ticker":"^DJI","name":"Dow Jones Industrial Average","currency":"USD","current_price_cents":2648154,"quantity":null,"current_price_timestamp":1597942580},{"ticker":"^TNX","name":"Treasury Yield 10 Years","currency":"USD","current_price_cents":61,"quantity":10,"current_price_timestamp":1597942580}]}
    """

    const val EMPTY_RESPONSE = """{"stocks":[]}"""

  }
}