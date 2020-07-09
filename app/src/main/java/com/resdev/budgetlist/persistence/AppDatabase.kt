package com.resdev.budgetlist.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.resdev.budgetlist.persistence.budget_item.BudgetItemDao
import com.resdev.budgetlist.persistence.budget_item.BudgetItemEntity

@Database(entities = [BudgetItemEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun budgetDao(): BudgetItemDao

    companion object {
        const val TABLE_NAME_BUDGET = "budget"
    }
}