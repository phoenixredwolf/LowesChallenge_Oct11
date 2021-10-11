package com.example.loweschallenge.utility

import android.util.Log
import kotlin.properties.Delegates

object Variables {
    var isNetConnected: Boolean by Delegates.observable(false) {
            property, oldValue, newValue ->  Log.i("Network connectivity", "$newValue")
    }
}