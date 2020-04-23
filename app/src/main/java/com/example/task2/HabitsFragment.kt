package com.example.task2

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_habits.*
import kotlin.collections.ArrayList

interface AddHabitButtonPressedCallback {
    fun onAddHabitButtonPressed(pageId: Int?)
}

interface EditHabitCallback {
    fun onEditHabitCallback(id: String, pageId: Int?)
}

class HabitsFragment: Fragment() {
    companion object {
        private const val habitTypeKey = "habitType"
        private const val pageIdKey = "pageId"
        fun newInstance(type: Constants.HabitType, pageId: Int?) : HabitsFragment {
            val fragment = HabitsFragment()
            val bundle = Bundle()
            if (pageId != null)
                bundle.putInt(pageIdKey, pageId)
            bundle.putSerializable(habitTypeKey, type)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var addHabitButtonPressedCallback: AddHabitButtonPressedCallback? = null
    var editHabitCallback: EditHabitCallback? = null
    private var myAdapter: MainAdapter? = null;
    private lateinit var habitsViewModel: HabitsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        addHabitButtonPressedCallback = activity as AddHabitButtonPressedCallback
        editHabitCallback = activity as EditHabitCallback
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("LIFECYCLE", "DESTROY")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        habitsViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HabitsViewModel(arguments?.getSerializable(habitTypeKey) as Constants.HabitType) as T
            }
        }).get(HabitsViewModel::class.java)
        childFragmentManager.beginTransaction()
            .replace(R.id.bottom_sheet_container,
                BottomSheetFragment.newInstance(arguments?.getSerializable(habitTypeKey) as Constants.HabitType))
            .commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habits, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        habitsViewModel.habits.observe(this, Observer {
            myAdapter = MainAdapter(it as ArrayList<Habit>,
                editHabitCallback, arguments?.getInt(pageIdKey), context!!)
            myRecycler.adapter = myAdapter
        })
        floatingActionButton2.setOnClickListener {
            addHabitButtonPressedCallback?.onAddHabitButtonPressed(arguments?.getInt(pageIdKey))
        }
    }
}