package com.example.revolutexercise.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(private val app: Application) : AndroidViewModel(app),
    CoroutineScope {

    protected var viewModelJob = Job()

    override val coroutineContext: CoroutineContext = viewModelJob + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}