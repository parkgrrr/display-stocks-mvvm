package com.parkerstevens.squarechallenge_parkerstevens

import android.icu.text.NumberFormat
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.Currency
import android.icu.util.TimeZone
import java.util.*

fun Long.toCurrency(currency: String) : String {
  val format = NumberFormat.getCurrencyInstance()
  format.maximumFractionDigits = 2
  format.minimumFractionDigits = 2
  format.minimumIntegerDigits = 1
  format.currency = Currency.getInstance(currency)
  return format.format(this /100.00)
}

fun Long.toTime() : String {
  val format = SimpleDateFormat("h:mm:ss a", Locale.US)
  format.timeZone = Calendar.getInstance().timeZone
  return format.format(Date(this))
}