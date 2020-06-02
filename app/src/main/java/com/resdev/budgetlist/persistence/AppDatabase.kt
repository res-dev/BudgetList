package com.resdev.budgetlist.persistence

import com.resdev.budgetlist.persistence.budget_item.BudgetItemDao
import com.resdev.budgetlist.persistence.budget_item.BudgetItemEntity

@Database(entities = [BudgetItemEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun budgetDao(): BudgetItemDao
}