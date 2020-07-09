package com.resdev.budgetlist.persistence.budget_item

import androidx.room.*
import com.resdev.budgetlist.persistence.AppDatabase
import kotlinx.coroutines.flow.Flow

@Dao
abstract class BudgetItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: BudgetItemEntity)

    @Query("SELECT * FROM ${AppDatabase.TABLE_NAME_BUDGET} WHERE record_id = :id")
    abstract suspend fun getBudgetItemById(id: Int): BudgetItemEntity

    @Query("SELECT * FROM ${AppDatabase.TABLE_NAME_BUDGET} ORDER BY is_complete DESC")
    abstract fun getAllBudgetItemsAsLiveData(): Flow<List<BudgetItemEntity>>

    @Delete
    abstract suspend fun deleteBudgetItem(budgetItem: BudgetItemEntity)
}