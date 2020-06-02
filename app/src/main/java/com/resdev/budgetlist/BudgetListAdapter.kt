package com.resdev.budgetlist

import android.view.LayoutInflater
import android.view.ViewGroup
import com.resdev.budgetlist.persistence.budget_item.BudgetItem

class BudgetListAdapter(
    var data: List<BudgetItem>,
    val clickHandler: BudgetListFragment.BudgetInteractHandler
): RecyclerView.Adapter<BudgetItemHolder>() {
    private lateinit var binding: BudgetItemViewBinding

    //create new views - invoked by the layout manager
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : BudgetItemHolder {
        //create a new view
        binding = BudgetItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BudgetItemHolder(binding, clickHandler)
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            (data[position].isComplete) -> 1
            (!data[position].isComplete) -> 0
            else -> return super.getItemViewType(position)
        }
    }

    override fun onBindViewHolder(holder: BudgetItemHolder, position: Int) {
        holder.bind(
            data[position],
            position > 0 && !data[position].isComplete && data[position - 1].isComplete
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }
}