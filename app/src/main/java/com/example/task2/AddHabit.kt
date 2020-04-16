package com.example.task2

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.internal.ContextUtils
import kotlinx.android.synthetic.main.fragment_add_habit.*

interface AddHabitCallback {
    fun onHabitAdded(pageId: Int?)
}

class AddHabit : Fragment() {
    companion object {
        private const val habitIdKey = "habitId"
        private const val pageIdKey = "pageId"
        fun newInstance(id: Int?, pageId: Int?) : AddHabit {
            val fragment = AddHabit()
            val bundle = Bundle()
            if (id != null)
                bundle.putInt(habitIdKey, id)
            if (pageId != null)
                bundle.putInt(pageIdKey, pageId)
            fragment.arguments = bundle
            return fragment
        }
    }

    var addHabitCallback: AddHabitCallback? = null
    private lateinit var changeHabitViewModel: ChangeHabitViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        addHabitCallback = activity as AddHabitCallback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeHabitViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ChangeHabitViewModel(arguments?.getInt(habitIdKey)) as T
            }
        }).get(ChangeHabitViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_habit, container, false)
    }

    private fun fillFormsByExistedHabit(habit: Habit) {
        formTitle.setText(habit.title)
        formDescription.setText(habit.description)
        formPriorities.setSelection(Constants.priorities[habit.priority]!!)
        formCount.setText(habit.count)
        formFrequency.setText(habit.frequency)
        if (habit.isGoodHabit)
            radioUseful.isChecked = true
        else
            radioUseless.isChecked = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val habitId = if (arguments!!.containsKey(habitIdKey))
            arguments?.getInt(habitIdKey)
        else
            null
        changeHabitViewModel.getChangedHabit().observe(this, Observer { habit ->
            if (habitId != null) {
                if (habit != null) {
                    fillFormsByExistedHabit(habit)
                }
            }
        })
        btnCreateHabit.setOnClickListener {
            val formType = if (radioUseful.isChecked)
                Constants.HabitType.GOOD
            else
                Constants.HabitType.BAD
            if (habitId != null) {
                changeHabitViewModel.changeHabit(habitId,
                    formTitle.text.toString(),
                    formDescription.text.toString(),
                    formPriorities.selectedItem.toString(),
                    formType,
                    formCount.text.toString(),
                    formFrequency.text.toString()
                )
            } else {
                changeHabitViewModel.addHabit(
                    formTitle.text.toString(),
                    formDescription.text.toString(),
                    formPriorities.selectedItem.toString(),
                    formType,
                    formCount.text.toString(),
                    formFrequency.text.toString()
                )
            }
            addHabitCallback?.onHabitAdded(arguments?.getInt(pageIdKey))

            val imm = context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}