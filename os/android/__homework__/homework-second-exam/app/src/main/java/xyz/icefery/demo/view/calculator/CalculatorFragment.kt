package xyz.icefery.demo.view.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import xyz.icefery.demo.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment() {
    private lateinit var binding: FragmentCalculatorBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCalculatorBinding.inflate(inflater, container, false)

        binding.webView.loadUrl("file:///android_asset/calculator/index.html")
        binding.webView.settings.javaScriptEnabled = true

        return binding.root
    }
}