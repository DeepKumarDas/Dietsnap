package com.example.demotwo.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demotwo.R
import com.example.demotwo.databinding.ActivityMainBinding
import com.example.demotwo.model.ResponseEvent
import com.example.demotwo.ui.home.model.HomePageData
import com.example.demotwo.ui.home.model.HomePageResponse
import com.example.demotwo.ui.view_model.DataViewModel
import com.example.demotwo.ui.view_model.VMFactory
import com.example.demotwo.utilities.Utilities.Companion.checkForInternet
import com.example.demotwo.utilities.Utilities.Companion.showToast
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: DataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this, VMFactory(this))[DataViewModel::class.java]

        setUIViews()
        collectionDataUI()
    }

    override fun onResume() {
        super.onResume()
        if (checkForInternet(this)){
            getHomePageData()
        } else {
            showToast(this, getString(R.string.no_internet))
        }
    }

    private fun setUIViews() {
        if (checkForInternet(this)){
             getHomePageData()
         } else {
             showToast(this, getString(R.string.no_internet))
         }
    }

    private fun getHomePageData() {
        viewModel.getHomePageData()
    }

    private fun collectionDataUI() {
        lifecycleScope.launch {
            viewModel.uiStateFlow.collect {
                when (it) {
                    is ResponseEvent.NoData -> {}

                    is ResponseEvent.ResponseEventData -> {
                        if (it.data is HomePageResponse) {
                            updateUI(it.data.data)
                        }
                    }
                    is ResponseEvent.Failure -> {

                    }
                    is ResponseEvent.RemoveLoader -> {

                    }
                    else -> {}
                }
            }
        }
    }

    private fun updateUI(homePageData: HomePageData) {
        println(homePageData.section_1.plan_name)
        println(homePageData.section_2.workout_duration)
        showToast(this, homePageData.section_1.plan_name)
        showToast(this, homePageData.section_2.workout_duration)
    }
}