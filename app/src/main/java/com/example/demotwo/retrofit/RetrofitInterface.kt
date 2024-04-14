package com.example.demotwo.retrofit

import com.example.demotwo.retrofit.ServerConstants.Companion.FOOD_INFO_URL
import com.example.demotwo.retrofit.ServerConstants.Companion.HOME_PAGE_URL
import com.example.demotwo.ui.food_info.model.FoodInfoResponse
import com.example.demotwo.ui.home.model.HomePageResponse
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitInterface {

    @GET(HOME_PAGE_URL)
    suspend fun getHomePageData(): Response<HomePageResponse?>?

    @GET(FOOD_INFO_URL)
    suspend fun getFoodInfoData(): Response<FoodInfoResponse?>?
}