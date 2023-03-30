package ru.gustavo.webview.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.webkit.WebView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import ru.gustavo.webview.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    var prefs: SharedPreferences? = null
    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val webView: WebView? = findViewById(R.id.webView)
                if (webView != null) {
                    if (webView.canGoBack()) {
                        webView.goBack()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.getString("url")
        remoteConfig.fetchAndActivate()
        remoteConfig.getString("url")
        prefs = getSharedPreferences("urlPrefs", Context.MODE_PRIVATE)
        URL = URL.ifBlank { prefs?.getString("URL", "")!! }
        if (URL.isBlank()) {
            URL = remoteConfig.getString("url")
            sync("URL", URL)
        }
    }

    private fun sync(key: String, str: String) {
        val editor = prefs?.edit()
        editor?.putString(key, str)
        editor?.apply()
    }
}