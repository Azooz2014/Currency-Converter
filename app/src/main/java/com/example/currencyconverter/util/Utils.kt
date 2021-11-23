package com.example.currencyconverter.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object Utils {

    /*
    * Credits to https://stackoverflow.com/questions/53532406/activenetworkinfo-type-is-deprecated-in-api-level-28
    * Utility function to check if device connected to network.
    * */

    fun Context.isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var result = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val network =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

            result = when {
                network.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                network.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                network.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }
                }
            }
        }
        return result
    }
}