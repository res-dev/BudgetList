package com.resdev.budgetlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class BudgetListFragment: Fragment() {

    companion object {
        fun newInstance() = BudgetListFragment
    }

    private lateinit var budgetListAdapter: BudgetListAdapter
    private val budgetViewModel: BudgetViewModel by viewModel()
    private lateinit var binding: MainFragmentBinding
    private lateinit var touchHandlerCallback: TouchCallback
    private lateinit var touchHelper: ItemTouchHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        budgetListAdapter = BudgetListAdapter(
            emptyList(),
            object: BudgetInteractHandler {
                override fun onReorder(viewHolder: RecyclerView.ViewHolder) {

                }
            }
        )
    }
}