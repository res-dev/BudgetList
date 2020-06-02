package com.resdev.budgetlist

import android.text.Editable
import android.view.LayoutInflater
import androidx.databinding.InverseMethod
import com.resdev.budgetlist.persistence.budget_item.BudgetItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.resdev.budgetlist.databinding.BudgetItemActionsheetBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class BudgetItemActionSheet(val budgetItem: BudgetItem) : BottomSheetDialogFragment() {
    private lateinit var binding: BudgetItemActionsheetBinding
    private val budgetViewModel: BudgetViewModel by viewModel()

    companion object {
        const val TAG = "BudgetItemActionSheet"
    }

    object Formatter {
        fun convertPriority(value: Int) : String {
            return if(value < 0) { "" } else { value.toString() }
        }

        @InverseMethod
        fun convertToPriority(value: String) : Int? {
            return if(value.isEmpty()) { 0 } else { value.toIntOrNull() }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        binding = BudgetItemActionSheetBinging.inflate(inflater, container, false)
        binding.budgetItem = budgetItem
        binding.converter = Formatter
        binding.inputTitle.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChagned(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO Before
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //TODO OnTextChanged
            }
            override fun afterTextChanged(s: Editable?) {
                budgetItem.title = s.toString()
                budgetItem.updatedAt = System.currentTimeMillis()
            }
        })
        binding.inputDescription.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChagned(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO Before
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //TODO OnTextChanged
            }
            override fun afterTextChanged(s: Editable?) {
                budgetItem.description = s.toString()
                budgetItem.updatedAt = System.currentTimeMillis()
            }
        })
        //binding.inputPriority.addTextChangedListener // TODO don't think we'll need priority
        binding.inputIsComplete.setOnCheckedChangeListener { _, isChecked ->
            budgetItem.isComplete = isChecked
        }
        binding.inputSave.setOnClickListener {
            GlobalScope.launch {
                budgetViewModel.saveBudgetItem(budgetItem)
                dismiss()
            }
        }
        return binding.root
    }
}