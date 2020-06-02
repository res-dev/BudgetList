package com.resdev.budgetlist

import androidx.lifecycle.LiveData

class BudgetViewModel(private var budgetItemRepo: BudgetItemRep) : ViewModel() {
    val budgetData: LiveData<List<BudgetItem>> = budgetItemRepo.getLiveData

    fun saveBudgetItem(budgetItem: BudgetItem) {
        viewModelScope.launch {
            budgetItemRepo.createBudgetItem(budgetItem)
        }
    }

    fun deleteBudgetItem(budgetItem: BudgetItem) {
        budgetItemRepo.deleteBudgetItem(budgetItem)
    }
}