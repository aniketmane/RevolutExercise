package com.example.revolutexercise.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.revolutexercise.data.api.NetworkBuilder
import com.example.revolutexercise.data.api.NetworkStatus
import com.example.revolutexercise.data.api.repository.RevolutApiRepositoryImpl
import com.example.revolutexercise.data.local.DatabaseBuilder
import com.example.revolutexercise.data.local.RatesDatabaseHelperImpl
import com.example.revolutexercise.databinding.FragmentCurrencyConverterBinding
import com.example.revolutexercise.ui.adapter.CurrencyRatesAdapter
import com.example.revolutexercise.ui.adapter.RateClickListener
import com.example.revolutexercise.ui.base.BaseFragment
import com.example.revolutexercise.utils.ViewModelFactory
import com.example.revolutexercise.utils.getViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class CurrencyConverterFragment : BaseFragment<CurrencyConverterViewModel>() {

    private lateinit var viewDataBinding: FragmentCurrencyConverterBinding

    private lateinit var currencyAdapter: CurrencyRatesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupUI(inflater)
        observeNetworkStatus()
        observeDBStatus()
        observeRateList()
        return viewDataBinding.root
    }

    private fun observeNetworkStatus() {
        viewModel.getNetworkStatus().observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it == NetworkStatus.LOADING) {
                    currencyAdapter.updateConnectStatus(true)
                } else if (it == NetworkStatus.SUCCESS_WITHOUT_CONNECTION || it == NetworkStatus.ERROR) {
                    currencyAdapter.updateConnectStatus(false)
                }
            }
        })
    }

    private fun observeDBStatus() {
        viewModel.getRateListFromDB().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                viewModel.onGetRateListFromDBorResponse(it)
            }
        })
    }

    private fun observeRateList() {
        viewModel.getRateList().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                currencyAdapter.updateData(it)
            }
        })
    }

    private fun setupUI(inflater: LayoutInflater) {
        viewDataBinding = FragmentCurrencyConverterBinding.inflate(inflater)

        viewDataBinding.lifecycleOwner = this

        viewDataBinding.viewModel = viewModel

        currencyAdapter =
            CurrencyRatesAdapter(
                requireContext(),
                mutableListOf(),
                RateClickListener { rateList, moveToTop ->
                    viewModel.onUpdateRates(rateList, moveToTop)
                })
        viewDataBinding.ratesListRecyclerView.adapter = currencyAdapter
        (viewDataBinding.ratesListRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
            false
    }


    override fun onResume() {
        super.onResume()
        viewModel.resumeJob()
    }

    override fun onPause() {
        super.onPause()
        viewModel.pauseJob()
    }

    override val viewModelClass: Class<CurrencyConverterViewModel>
        get() = CurrencyConverterViewModel::class.java

    override val vmFactory: ViewModelFactory
        get() = getViewModelFactory(
            dbHelper =
            RatesDatabaseHelperImpl(
                DatabaseBuilder.getInstance(requireNotNull(activity).application)
            ),
            apiRepository =
            RevolutApiRepositoryImpl(NetworkBuilder.apiService)
        )
}