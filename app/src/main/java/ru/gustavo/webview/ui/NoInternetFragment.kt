package ru.gustavo.webview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.gustavo.webview.databinding.NoInternetFragmentBinding

class NoInternetFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = NoInternetFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}