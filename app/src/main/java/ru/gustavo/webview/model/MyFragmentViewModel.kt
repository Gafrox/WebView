package ru.gustavo.webview.model

import android.os.Bundle
import androidx.lifecycle.ViewModel

class MyFragmentViewModel : ViewModel() {
    private lateinit var viewModel: MyFragmentViewModel
    var webViewState: Bundle? = null
}
