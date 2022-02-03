package com.example.testappl.view.activity

import android.os.Bundle
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testappl.App
import com.example.testappl.R
import com.example.testappl.databinding.ActivityWebBinding
import com.example.testappl.utils.Constants.URL
import com.example.testappl.viewModel.MainViewModel
import javax.inject.Inject

class WebActivity : AppCompatActivity() {

    private var _binding: ActivityWebBinding? = null
    val binding get() = _binding!!
    private var url = ""

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).appComponent.inject(this)
        _binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)
        url = intent.getStringExtra(URL) ?: ""
        mainViewModel.result.observe(this, {
            when (it) {
                false -> {
                    Toast.makeText(this, resources.getString(R.string.invalid_url), Toast.LENGTH_LONG).show()
                }
                else -> {
                    binding.webView.webViewClient = WebViewClient()
                    binding.webView.loadUrl(url)
                }
            }
        })
        mainViewModel.checkURL(url)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}