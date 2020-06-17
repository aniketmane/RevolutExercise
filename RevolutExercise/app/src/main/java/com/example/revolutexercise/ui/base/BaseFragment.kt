package com.example.revolutexercise.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.revolutexercise.utils.ViewModelFactory

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {
    abstract val viewModelClass: Class<VM>
    lateinit var viewModel: VM
    abstract val vmFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(
            this.activity!!,
            vmFactory
        ).get(viewModelClass)
    }
}
