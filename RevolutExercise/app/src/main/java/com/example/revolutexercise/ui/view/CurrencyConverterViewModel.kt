package com.example.revolutexercise.ui.view

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.revolutexercise.data.api.NetworkErrors
import com.example.revolutexercise.data.api.NetworkStatus
import com.example.revolutexercise.data.api.model.Rates
import com.example.revolutexercise.data.api.repository.RevolutApiRepository
import com.example.revolutexercise.data.local.RatesDatabaseHelper
import com.example.revolutexercise.data.local.entity.Rate
import com.example.revolutexercise.ui.base.BaseViewModel
import com.example.revolutexercise.utils.CurrencyRatesUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlin.reflect.full.memberProperties

@ExperimentalCoroutinesApi
class CurrencyConverterViewModel(
    private val dbHelper: RatesDatabaseHelper,
    private val apiRepository: RevolutApiRepository,
    val app: Application
) : BaseViewModel(app) {

    companion object {
        const val DEFAULT_CURRENCY_CODE = "EUR"
        const val DEFAULT_CURRENCY_RATE = 1.0
        const val GET_RATE_INTERNAL = 1_000L
    }

    private var currencyCode: String = DEFAULT_CURRENCY_CODE

    private var currencyRate: Double = DEFAULT_CURRENCY_RATE

    private val networkStatus = MutableLiveData<NetworkStatus>()

    fun getNetworkStatus(): LiveData<NetworkStatus> {
        return networkStatus
    }

    private val rateList = MutableLiveData<MutableList<Rate>>()

    fun getRateList(): LiveData<MutableList<Rate>> {
        return rateList
    }

    private val rateListFromDB = MutableLiveData<MutableList<Rate>>()

    fun getRateListFromDB(): LiveData<MutableList<Rate>> {
        return rateListFromDB
    }

    private val errorMessage = MutableLiveData<String>()

    fun getErrorMessage(): LiveData<String> {
        return errorMessage
    }

    private var latestRates: Rates? = null

    private var apiConnection = false

    @ObsoleteCoroutinesApi
    private fun getLatestRates(showLoading: Boolean = true) {
        viewModelJob = Job()
        launch {
            if (showLoading) {
                networkStatus.value = NetworkStatus.LOADING
            }
            val tickerChannel = ticker(delayMillis = GET_RATE_INTERNAL, initialDelayMillis = 0)
            for (event in tickerChannel) {
                val getLatestRatesResponse =
                    apiRepository.getRates(currencyCode)
                getLatestRatesResponse.catch {
                    errorMessage.value = NetworkErrors(it).errorMessage
                    apiConnection = false
                    rateListFromDB.value = getRatesFromDatabase().toMutableList()
                    tickerChannel.cancel()
                }.collect {
                    networkStatus.value = NetworkStatus.SUCCESS
                    apiConnection = true
                    latestRates = it.rates
                    rateList.value = CurrencyRatesUtil.ratesResponseToRateList(
                        app.applicationContext,
                        latestRates!!,
                        currencyCode,
                        currencyRate
                    )
                    rateListFromDB.value = getRatesFromDatabase().toMutableList()
                }
            }
        }
    }

    fun onGetRateListFromDBorResponse(rateListFromDB: MutableList<Rate>) {
        launch {
            when {
                networkStatus.value == NetworkStatus.SUCCESS && apiConnection -> {
                    updateRatesToDB(
                        latestRates,
                        rateListFromDB
                    )
                }
                else -> when {
                    rateListFromDB.size != 0 -> {
                        if (!apiConnection) {
                            networkStatus.value = NetworkStatus.SUCCESS_WITHOUT_CONNECTION
                        }
                        when {
                            latestRates != null -> rateList.value =
                                CurrencyRatesUtil.ratesResponseToRateList(
                                    app.applicationContext,
                                    latestRates!!,
                                    currencyCode,
                                    currencyRate
                                )
                            else -> rateList.value = CurrencyRatesUtil.rateListFromDBToRateList(
                                app.applicationContext,
                                rateListFromDB,
                                currencyCode,
                                currencyRate
                            )
                        }
                    }
                    else -> {
                        networkStatus.value = NetworkStatus.ERROR
                        rateList.value = ArrayList()
                    }
                }
            }
        }
    }

    private suspend fun getRatesFromDatabase(): List<Rate> {
        return withContext(Dispatchers.IO) {
            val rateList = dbHelper.getAllRates()
            if (rateListFromDB.value == null && rateList.isNotEmpty()) {
                currencyCode = rateList[0].code
            }
            rateList
        }
    }

    private suspend fun updateRatesToDB(rates: Rates?, rateListFromDB: MutableList<Rate>) {
        rates?.let {
            withContext(Dispatchers.IO) {
                insertOrUpdateRatesToDB(DEFAULT_CURRENCY_RATE, currencyCode, rateListFromDB)
                Rates::class.memberProperties.forEach { member ->
                    val rate = member.get(rates) as Double?
                    if (rate != null) {
                        insertOrUpdateRatesToDB(rate, member.name, rateListFromDB)
                    }
                }
            }
        }
    }

    private suspend fun insertOrUpdateRatesToDB(
        currencyRate: Double,
        currencyCode: String,
        rateListFromDB: MutableList<Rate>
    ) {
        if (rateListFromDB.size != 0) {
            dbHelper.get(currencyCode)?.let { rateInDB ->
                rateInDB.rate = currencyRate
                dbHelper.update(rateInDB)
            }
        } else {
            dbHelper.insertRate(
                Rate(
                    code = currencyCode,
                    rate = currencyRate,
                    flagImageResId = CurrencyRatesUtil.getFlagImageResId(
                        app.applicationContext,
                        currencyCode
                    )
                )
            )
        }
    }

    @ObsoleteCoroutinesApi
    fun onUpdateRates(rateList: MutableList<Rate>, moveToTop: Boolean) {
        if (rateList.size > 0) {
            currencyCode = rateList[0].code
            currencyRate = rateList[0].rate
            if (moveToTop) {
                viewModelJob.cancel()
                getLatestRates(false)
            } else if (!apiConnection) {
                rateListFromDB.value = rateListFromDB.value
            }
        }
    }

    @ObsoleteCoroutinesApi
    fun resumeJob() {
        getLatestRates()
    }

    fun pauseJob() {
        viewModelJob.cancel()
    }

}