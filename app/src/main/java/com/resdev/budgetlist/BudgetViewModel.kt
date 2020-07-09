package com.resdev.budgetlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.asLiveData
import com.resdev.budgetlist.persistence.budget_item.BudgetItemRepo
import com.resdev.budgetlist.persistence.budget_item.BudgetItem
import kotlinx.coroutines.launch

class BudgetViewModel(private var budgetItemRepo: BudgetItemRepo) : ViewModel() {
    val budgetData: LiveData<List<BudgetItem>> = budgetItemRepo.getAllBudgetItemsAsLiveData().asLiveData()

    fun saveBudgetItem(budgetItem: BudgetItem) {
        viewModelScope.launch {
            budgetItemRepo.createBudgetItem(budgetItem)
        }
    }

    fun deleteBudgetItem(budgetItem: BudgetItem) {
        viewModelScope.launch {
            budgetItemRepo.deleteBudgetItem(budgetItem)
        }
    }
}