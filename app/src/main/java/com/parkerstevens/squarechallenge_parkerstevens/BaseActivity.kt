package com.parkerstevens.squarechallenge_parkerstevens

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VM : ViewModel, VB : ViewBinding>(private val viewModelClass: Class<VM>) : AppCompatActivity() {
  protected lateinit var viewModel: VM
  private var _binding: ViewBinding? = null
  abstract val bindingInflater: (LayoutInflater) -> VB

  @Suppress("UNCHECKED_CAST")
  protected val binding: VB
    get() = _binding as VB

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = bindingInflater.invoke(layoutInflater)
    setContentView(binding.root)
    viewModel = ViewModelProvider(this).get(viewModelClass)
    initViewModel()
  }

  abstract fun initViewModel()
}