package com.example.myapplication

import android.app.Application
import com.example.myapplication.service.RetrofitClient

/*
 *  Created by Nikoloz Katsitadze on 25.05.21
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        RetrofitClient.initClient()
    }

}