package com.resdev.budgetlist

import android.app.Application
import androidx.room.Room
import com.resdev.budgetlist.persistence.AppDatabase
import com.resdev.budgetlist.persistence.budget_item.BudgetItemRepo
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            this.modules(databaseModule)
        }
    }

    private val databaseModule = module {
        viewModel { BudgetViewModel(get()) }
        //database Singleton for Koin Dependency Injection
        single {
            Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "room_database")
                .build()
        }
        single { get<AppDatabase>().budgetDao()}
        factory { BudgetItemRepo(get()) }
    }
}