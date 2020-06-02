package com.resdev.budgetlist.persistence.budget_item

data class BudgetItem(
    var id: Int,
    var title: String,
    var description: String,
    var categoryId: Int,
    var isIncome: Boolean = false,
    var isComplete: Boolean = false,
    val createdAt: Long,
    var updatedAt: Long = 0
)