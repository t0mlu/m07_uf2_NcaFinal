package com.example.nca_final

import android.app.Application
import com.example.nca_final.model.AppDatabase

class DuckListApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this)}
}