package com.resdev.budgetlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.resdev.budgetlist.databinding.MainFragmentBinding
import com.resdev.budgetlist.persistence.budget_item.BudgetItem
import com.resdev.budgetlist.persistence.budget_item.createDummyBudgetItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class BudgetListFragment: Fragment() {

    companion object {
        fun newInstance() = BudgetListFragment()
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
                    touchHelper.startDrag(viewHolder)
                }
                override fun onClick(position: Int) {
                    showBudgetActionSheet(budgetListAdapter.data[position])
                }
            }
        )

        binding = MainFragmentBinding.inflate(inflater, container, false).apply {
            budgetListContainer.layoutManager = LinearLayoutManager(context)
            budgetListContainer.adapter = budgetListAdapter
            fabAddNewItem.setOnClickListener {
                showBudgetActionSheet(
                    BudgetItem(
                        id = 0,
                        title = "",
                        description = "",
                        categoryId = -1,
                        isIncome = false,
                        isComplete = false,
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()
                    )
                )
            }
        }
        touchHandlerCallback = TouchCallback(budgetListAdapter, budgetViewModel)
        touchHelper = ItemTouchHelper(touchHandlerCallback).also {
            it.attachToRecyclerView(binding.budgetListContainer)
        }
        budgetViewModel.budgetData.observe(viewLifecycleOwner, Observer { b ->
            budgetListAdapter.data = b
            budgetListAdapter.notifyDataSetChanged()
        })
        return binding.root
    }

    fun showBudgetActionSheet(budgetItem: BudgetItem) {
        this@BudgetListFragment.parentFragmentManager.beginTransaction()
            .add(
                BudgetItemActionSheet(budgetItem),
                BudgetItemActionSheet.TAG
            ).commit()
    }

    interface BudgetInteractHandler {
        fun onClick(position: Int)
        fun onReorder(viewHolder: RecyclerView.ViewHolder)
    }

    class TouchCallback(
        private val adapter: BudgetListAdapter,
        private val viewModel: BudgetViewModel
    ) : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP
                or ItemTouchHelper.DOWN
                or ItemTouchHelper.LEFT
                or ItemTouchHelper.RIGHT,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        private var pendingUpdate = createDummyBudgetItem()
        private var pendingDelete = createDummyBudgetItem()

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            pendingUpdate = adapter.data[viewHolder.adapterPosition]
            pendingUpdate.updatedAt = System.currentTimeMillis()
            adapter.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            when (direction) {
                ItemTouchHelper.RIGHT -> {
                    val update = adapter.data[viewHolder.adapterPosition]
                    update.isComplete = !update.isComplete
                    update.updatedAt = System.currentTimeMillis()
                    viewModel.saveBudgetItem(update)
                    adapter.notifyDataSetChanged()
                }
                ItemTouchHelper.LEFT -> {
                    pendingDelete = adapter.data[viewHolder.adapterPosition]
                    pendingDelete.updatedAt = System.currentTimeMillis()
                    viewModel.deleteBudgetItem(pendingDelete)
                    val snackbar = Snackbar.make(
                        viewHolder.itemView,
                        "Budget Item Deleted",
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.setAction("Undo") { undoDelete() }
                    snackbar.show()
                }
            }
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            super.clearView(recyclerView, viewHolder)
            if (pendingUpdate.id >= 0) {
                viewModel.saveBudgetItem(pendingUpdate)
                pendingUpdate = createDummyBudgetItem()
                adapter.notifyDataSetChanged()
            }
        }

        private fun undoDelete() {
            if (pendingDelete.id >= 0) {
                viewModel.saveBudgetItem(pendingDelete)
                pendingDelete = createDummyBudgetItem()
            }
        }
    }
}