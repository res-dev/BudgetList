package com.resdev.budgetlist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.InverseMethod
import com.resdev.budgetlist.persistence.budget_item.BudgetItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.resdev.budgetlist.databinding.BudgetItemActionsheetBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BudgetItemActionSheet(val budgetItem: BudgetItem) : BottomSheetDialogFragment() {
    private lateinit var binding: BudgetItemActionsheetBinding
    private val budgetViewModel: BudgetViewModel by viewModel()

    companion object {
        const val TAG = "BudgetItemActionSheet"
    }

    //TODO I don't think I'm actually going to use priority, so I could probably remove the Formatter...
    object Formatter {
        fun convertPriority(value: Int) : String {
            return if(value < 0) { "" } else { value.toString() }
        }

        @InverseMethod("convertPriority")
        fun convertToPriority(value: String) : Int? {
            return if(value.isEmpty()) { 0 } else { value.toIntOrNull() }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        binding = BudgetItemActionsheetBinding.inflate(inflater, container, false)
        binding.budgetItem = budgetItem
        binding.converter = Formatter
        binding.inputTitle.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //do nothing
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //do nothing
            }
            override fun afterTextChanged(s: Editable?) {
                budgetItem.title = s.toString()
                budgetItem.updatedAt = System.currentTimeMillis()
            }
        })
        binding.inputDescription.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //do nothing
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //do nothing
            }
            override fun afterTextChanged(s: Editable?) {
                budgetItem.description = s.toString()
                budgetItem.updatedAt = System.currentTimeMillis()
            }
        })
        binding.buttonSave.setOnClickListener {
            GlobalScope.launch {
                budgetViewModel.saveBudgetItem(budgetItem)
                dismiss()
            }
        }
        return binding.root
    }
}