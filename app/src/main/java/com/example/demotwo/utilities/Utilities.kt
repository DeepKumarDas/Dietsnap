package com.example.demotwo.utilities

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.demotwo.R

class Utilities {

    companion object{

        fun checkForInternet(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val network = connectivityManager.activeNetwork ?: return false

            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(network) ?: return false

            return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }

        fun showToast(context: Context, text: String){
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }

        fun transitionOpen(activity : Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                activity.overrideActivityTransition (
                    Activity.OVERRIDE_TRANSITION_OPEN,
                    R.anim.slide_from_right,
                    R.anim.slide_to_left)
            } else {
                activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }
        }

        fun loadAnimationTopIn(activity : Activity, duration: Long): Animation {
            val animation = AnimationUtils.loadAnimation(activity, R.anim.top_in)
            animation.duration = duration
            return animation
        }
    }
}