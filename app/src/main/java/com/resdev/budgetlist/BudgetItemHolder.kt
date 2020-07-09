package com.resdev.budgetlist

import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.resdev.budgetlist.persistence.budget_item.BudgetItem
import com.resdev.budgetlist.databinding.BudgetItemViewBinding

class BudgetItemHolder (
    private val binding: BudgetItemViewBinding,
    private val interactHandler: BudgetListFragment.BudgetInteractHandler
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(budgetItem: BudgetItem, isFirstInteractive: Boolean) {
        binding.budgetItem = budgetItem
        binding.itemViewContainer.setOnClickListener { interactHandler.onClick(adapterPosition); }
        binding.divider.visibility = when (isFirstInteractive) {
            true -> View.VISIBLE
            else -> View.GONE
        }
        //TODO Investigate performClick override
        binding.dragHandle.setOnTouchListener { _, event ->
            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                interactHandler.onReorder(this)
            }
            return@setOnTouchListener true
        }
        binding.executePendingBindings()
    }
}