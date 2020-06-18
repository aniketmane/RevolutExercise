package com.example.revolutexercise.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.revolutexercise.data.api.repository.RevolutApiRepository
import com.example.revolutexercise.data.local.RatesDatabaseHelper
import com.example.revolutexercise.ui.view.CurrencyConverterViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val dbHelper: RatesDatabaseHelper,
    private val apiRepository: RevolutApiRepository,
    private val app: Application
) : ViewModelProvider.NewInstanceFactory() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            if (isAssignableFrom(CurrencyConverterViewModel::class.java)) {
                CurrencyConverterViewModel(dbHelper, apiRepository, app)
            } else {
                throw IllegalArgumentException("Unable to construct ${modelClass.name}")
            }
        } as T
}
