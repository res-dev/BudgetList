package com.resdev.budgetlist.persistence.budget_item

import org.intellij.lang.annotations.Flow

class BudgetItemRepo(private val dao: BudgetItemDao) {
    suspend fun createBudgetItem(record: BudgetItem) {
        return dao.insert(BudgetItemEntity.from(record))
    }

    suspend fun getBudgetItemById(id: Int): BudgetItem {
        return dao.getBudgetItemById(id).toBudgetItem()
    }

    suspend fun deleteBudgetItem(budgetItem: BudgetItem) {
        return dao.deleteBudgetItem(BudgetItemEntity.from(budgetItem))
    }

    fun getAllBudgetItemsAsLiveData(): Flow<List<BudgetItem>> {
        return dao.getAllBudgetItemsAsLiveData()
            .map {
                budgetList -> budgetList.map { it.toBudgetItem() }
            }
    }
}