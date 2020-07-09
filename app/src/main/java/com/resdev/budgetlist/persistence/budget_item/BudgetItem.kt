package com.resdev.budgetlist.persistence.budget_item

data class BudgetItem(
    var id: Int,
    var title: String,
    var description: String,
    var categoryId: Int,
    var isIncome: Boolean = false,
    var isComplete: Boolean = false,
    //createdAt and updatedAt are both represented by millis - System.currentTimeMillis() expected
    val createdAt: Long,
    var updatedAt: Long = 0
)

fun createDummyBudgetItem(): BudgetItem {
    return BudgetItem(
        id = -1,
        title = "Dummy Item",
        description = "",
        categoryId = -1,
        isIncome = false,
        isComplete = false,
        createdAt = 0L,
        updatedAt = 0L
    )
}