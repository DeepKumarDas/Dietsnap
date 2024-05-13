package com.example.demotwo.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.demotwo.R
import com.example.demotwo.databinding.ActivityMainBinding
import com.example.demotwo.model.ResponseEvent
import com.example.demotwo.ui.home.model.HomePageData
import com.example.demotwo.ui.home.model.HomePageResponse
import com.example.demotwo.ui.view_model.DataViewModel
import com.example.demotwo.ui.view_model.VMFactory
import com.example.demotwo.utilities.Utilities.Companion.checkForInternet
import com.example.demotwo.utilities.Utilities.Companion.showToast
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: DataViewModel
    private lateinit var dateTime : TextView
    private lateinit var caloriesBurned : TextView
    private lateinit var workoutDuration : TextView
    private lateinit var reps : TextView
    private lateinit var homePageScrollview : ScrollView
    private lateinit var progressBar : ProgressBar
    private lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        dateTime = findViewById(R.id.date_time)
        caloriesBurned = findViewById(R.id.calories_burned)
        workoutDuration = findViewById(R.id.workout_duration)
        reps = findViewById(R.id.reps)
        homePageScrollview = findViewById(R.id.home_page_scrollview)
        progressBar = findViewById(R.id.progress_bar)
        bottomNav = findViewById(R.id.bottomNavigationView)

        setDateTime()

        val toolbar: Toolbar? = findViewById(R.id.tool_bar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Dietsnap"
        toolbar?.setTitleTextColor(Color.parseColor("#F8B645"))

        viewModel = ViewModelProvider(this, VMFactory(this))[DataViewModel::class.java]

        setUIViews()
        collectionDataUI()

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.feed -> {
                    val intent = Intent(this,MainActivity2::class.java)
                    startActivity(intent)
                    true
                }

                else -> {
                    true
                }
            }
        }
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
            homePageScrollview.visibility = View.VISIBLE
            progressBar.visibility = View.INVISIBLE
        } else {
            homePageScrollview.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
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
        caloriesBurned.text = homePageData.section_2.calories_burned.toString()
        workoutDuration.text = homePageData.section_2.workout_duration
        reps.text = homePageData.section_2.reps.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.appbar_menu_item, menu)
        return true
    }

    private fun setDateTime(){
        val format = SimpleDateFormat("EEEE, dd MMM", Locale.getDefault())
        dateTime.text = format.format(Date())
    }
}