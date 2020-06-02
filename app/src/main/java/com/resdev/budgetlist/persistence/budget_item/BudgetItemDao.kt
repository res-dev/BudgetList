package com.resdev.budgetlist.persistence.budget_item

@Dao
abstract class BudgetItemDao {

    @Insert(onCOnflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: TodoEntity)

    @Query("SELECT * FROM budget WHERE record_id = :id")
    abstract suspend fun getBudgetItemById(id: Int): BudgetItemEntity

    @Query("SELECT * FROM budget ORDER BY is_complete DESC")
    abstract fun getAllBudgetItemsAsLiveData(): Flow<List<BudgetItemEntity>>

    @Delete
    abstract suspend fun deleteBudgetItem(budgetItem: BudgetItemEntity)
}