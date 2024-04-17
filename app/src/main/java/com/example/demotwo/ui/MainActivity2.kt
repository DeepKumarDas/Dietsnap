package com.example.demotwo.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.demotwo.R
import com.example.demotwo.databinding.ActivityMainBinding
import com.example.demotwo.model.ResponseEvent
import com.example.demotwo.ui.food_info.model.Data
import com.example.demotwo.ui.food_info.model.FoodInfoResponse
import com.example.demotwo.ui.home.model.HomePageData
import com.example.demotwo.ui.home.model.HomePageResponse
import com.example.demotwo.ui.view_model.DataViewModel
import com.example.demotwo.ui.view_model.VMFactory
import com.example.demotwo.utilities.Utilities
import kotlinx.coroutines.launch

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: DataViewModel
    private lateinit var foodName: TextView
    private lateinit var foodRating: TextView
    private lateinit var foodDescription: AppCompatTextView
    private lateinit var energyAmount: TextView
    private lateinit var proteinAmount: TextView
    private lateinit var transFatAmount: TextView
    private lateinit var saturatedFatAmount: TextView
    private lateinit var carbohydratesAmount: TextView
    private lateinit var fiberAmount: TextView
    private lateinit var fact1: TextView
    private lateinit var fact2: TextView
    private lateinit var fact3: TextView
    private lateinit var fact4: TextView
    private lateinit var fact5: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        foodName = findViewById(R.id.food_name)
        foodRating = findViewById(R.id.food_rating)
        foodDescription = findViewById(R.id.food_description)
        energyAmount = findViewById(R.id.energy_amount)
        proteinAmount = findViewById(R.id.protein_amount)
        transFatAmount = findViewById(R.id.trans_fat_amount)
        saturatedFatAmount = findViewById(R.id.saturated_fat_amount)
        carbohydratesAmount = findViewById(R.id.carbohydrates_amount)
        fiberAmount = findViewById(R.id.fiber_amount)
        fact1 = findViewById(R.id.fact1)
        fact2 = findViewById(R.id.fact2)
        fact3 = findViewById(R.id.fact3)
        fact4 = findViewById(R.id.fact4)
        fact5 = findViewById(R.id.fact5)

        viewModel = ViewModelProvider(this, VMFactory(this))[DataViewModel::class.java]

        collectionDataUI()
    }

    override fun onResume() {
        super.onResume()
        if (Utilities.checkForInternet(this)){
            getFoodInfoData()
        } else {
            Utilities.showToast(this, getString(R.string.no_internet))
        }
    }

    private fun getFoodInfoData() {
        viewModel.getFoodInfoData()
    }

    private fun collectionDataUI() {
        lifecycleScope.launch {
            viewModel.uiStateFlow.collect {
                when (it) {
                    is ResponseEvent.NoData -> {}

                    is ResponseEvent.ResponseEventData -> {
                        if (it.data is FoodInfoResponse) {
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

    private fun updateUI(data: Data) {
        foodName.text = data.name
        foodDescription.text = data.description
        foodRating.text = data.health_rating.toString()
        energyAmount.text = String.format("%.2f", data.nutrition_info_scaled[0].value) +"  "+ data.nutrition_info_scaled[0].units
        proteinAmount.text = String.format("%.2f", data.nutrition_info_scaled[1].value) +"  "+ data.nutrition_info_scaled[1].units
        transFatAmount.text = String.format("%.2f", data.nutrition_info_scaled[6].value) +"  "+ data.nutrition_info_scaled[6].units
        saturatedFatAmount.text = String.format("%.2f", data.nutrition_info_scaled[4].value) +"  "+ data.nutrition_info_scaled[4].units
        carbohydratesAmount.text = String.format("%.2f", data.nutrition_info_scaled[2].value) +"  "+ data.nutrition_info_scaled[2].units
        fiberAmount.text = String.format("%.2f", data.nutrition_info_scaled[7].value) +"  "+ data.nutrition_info_scaled[7].units
        fact1.text = data.generic_facts[0]
        fact2.text = data.generic_facts[1]
        fact3.text = data.generic_facts[2]
        fact4.text = data.generic_facts[3]
        fact5.text = data.generic_facts[4]
    }
}
