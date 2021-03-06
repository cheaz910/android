package com.example.task2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_habits.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlin.collections.ArrayList


class BottomSheetFragment: Fragment() {
    companion object {
        private const val habitTypeKey = "habitType"
        fun newInstance(type: Constants.HabitType) : BottomSheetFragment {
            val fragment = BottomSheetFragment()
            val bundle = Bundle()
            bundle.putSerializable(habitTypeKey, type)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var habitsViewModel: HabitsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("LIFECYCLE", "bottomsheet1")
        habitsViewModel = ViewModelProvider(parentFragment!!).get(HabitsViewModel::class.java)
        Log.i("LIFECYCLE", "bottomsheet2")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        formSortBy.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                habitsViewModel.selectItemSelected(Constants.SortType.values()[id.toInt()])
            }
        }
        btnSortAsc.setOnClickListener {
            habitsViewModel.sortBy(true, getSortType())
        }
        btnSortDesc.setOnClickListener {
            habitsViewModel.sortBy(false, getSortType())
        }
        formSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                habitsViewModel.filterByString(s.toString(), getSortType())
            }
        })
    }

    private fun getSortType() : Constants.SortType {
        return Constants.SortType.values()[formSortBy.selectedItemId.toInt()]
    }
}