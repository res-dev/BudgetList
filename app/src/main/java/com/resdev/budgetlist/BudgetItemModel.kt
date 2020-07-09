package com.resdev.budgetlist

//TODO Delete this model
data class remove_BudgetItemModel(
    var id: Int,
    var priority: Int?,
    var title: String,
    var description: String,
    var isComplete: Boolean,
    val createdAt: Long,
    var updatedAt: Long = 0
)