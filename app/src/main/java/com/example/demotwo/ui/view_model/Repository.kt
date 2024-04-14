package com.example.demotwo.ui.view_model

import android.content.Context
import com.example.demotwo.retrofit.RetrofitInterface
import com.example.demotwo.retrofit.RetrofitUtils.callRetrofit
import com.example.demotwo.ui.food_info.model.FoodInfoResponse
import com.example.demotwo.ui.home.model.HomePageResponse
import retrofit2.Response

class Repository(private val retrofitInterface: RetrofitInterface) {

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: Repository? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance
                    ?: Repository(callRetrofit(context)).also {
                        instance = it
                    }
            }
    }

    suspend fun getHomePageData(): Response<HomePageResponse?>? {
        return retrofitInterface.getHomePageData()
    }

    suspend fun getFoodInfoData(): Response<FoodInfoResponse?>? {
        return retrofitInterface.getFoodInfoData()
    }
}