package com.example.loweschallenge.utility

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.annotation.RequiresPermission

class NetworkUtils
@RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
constructor(private val app: Application) {

    fun startNetworkCallback() {
        val connMan: ConnectivityManager =
            app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder: NetworkRequest.Builder = NetworkRequest.Builder()

        connMan.registerDefaultNetworkCallback(networkCallback)
    }

    fun stopNetworkCallback() {
        val connMan: ConnectivityManager =
            app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connMan.unregisterNetworkCallback(networkCallback)
    }

    private val networkCallback = object: ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            Variables.isNetConnected = true
        }

        override fun onLost(network: Network) {
            Variables.isNetConnected = false
        }
    }
}