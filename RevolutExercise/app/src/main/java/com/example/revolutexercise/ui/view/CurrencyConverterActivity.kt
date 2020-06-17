package com.example.revolutexercise.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.revolutexercise.R
import com.example.revolutexercise.databinding.ActivityCurrencyConverterBinding

class CurrencyConverterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Rates"
        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<ActivityCurrencyConverterBinding>(
            this,
            R.layout.activity_currency_converter
        )
    }
}
