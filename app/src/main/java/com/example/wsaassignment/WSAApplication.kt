package com.example.wsaassignment

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.example.wsaassignment.database.FavoritesDatabase
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class WSAApplication : MultiDexApplication() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
        if (base != null) {
            FavoritesDatabase.initandGetAppDatabase(base)
        }
    }
}