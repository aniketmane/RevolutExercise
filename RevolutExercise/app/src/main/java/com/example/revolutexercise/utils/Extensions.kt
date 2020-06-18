package com.example.revolutexercise.utils

import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.revolutexercise.data.api.repository.RevolutApiRepository
import com.example.revolutexercise.data.local.RatesDatabaseHelper

fun Fragment.getViewModelFactory(
    dbHelper: RatesDatabaseHelper,
    apiRepository: RevolutApiRepository
): ViewModelFactory {
    val application = requireNotNull(activity).application
    return ViewModelFactory(dbHelper,  apiRepository, application)
}

fun EditText.placeCursorToProperDigitPosition() {
    this.setSelection(this.text.split(".")[0].length)
}