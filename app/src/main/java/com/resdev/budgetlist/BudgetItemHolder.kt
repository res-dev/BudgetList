package com.resdev.budgetlist

import android.view.MotionEvent
import android.view.View
import com.resdev.budgetlist.persistence.budget_item.BudgetItem

class BudgetItemHolder (
    private val binding: BudgeItemViewBinding,
    private val interactHandler: BudgetListFragment.BudgetInteractHandler
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(budgetItem: BudgetItem, isFirstInteractive: Boolean) {
        binding.budgetItem = budgetItem
        binding.content.setOnClickListener { interactHandler.onClick(adapterPosition); }
        binding.inactiveDivider.visibility = if (isFirstInactive) {
            View.VISIBLE
        } else {
            View.GONE
        }
        binding.dragHandle.setOnTouchListener { _, event ->
            if (event.actionMAsked == MotionEvent.ACTION_DOWN) {
                interactHandler.onReorder(this)
            }
            return@setToucListener true
        }
        binding.executePendingBindings()
    }
}