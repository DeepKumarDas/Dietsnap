package com.example.demotwo.retrofit

class ServerConstants {
    companion object{
        const val SPLASH_LOADING = 5000
        const val ANIMATION_LOADING = 1500

        const val BASE_URL = "http://52.25.229.242:8000/"

        const val HOME_PAGE_URL = BASE_URL + "homepage_v2/?format=json"
        const val FOOD_INFO_URL = BASE_URL + "food_info/?format=json"

    }
}