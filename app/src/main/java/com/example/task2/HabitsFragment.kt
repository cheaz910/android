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
    fun onEditHabitCallback(id: Int, pageId: Int?)
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
        habitsViewModel.getHabits().observe(this, Observer {
            myAdapter = MainAdapter(it as ArrayList<Habit>,
                editHabitCallback, arguments?.getInt(pageIdKey))
            myRecycler.adapter = myAdapter
        })
        floatingActionButton2.setOnClickListener {
            addHabitButtonPressedCallback?.onAddHabitButtonPressed(arguments?.getInt(pageIdKey))
        }
    }

    class MainAdapter(var items: List<Habit>, var editHabitCallback: EditHabitCallback?,
                      private val pageId: Int?) :
        RecyclerView.Adapter<MainAdapter.MainHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            MainHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.main_item,
                    parent,
                    false
                )
            )

        override fun getItemCount() = items.size
        override fun onBindViewHolder(holder: MainHolder, position: Int) {
            holder.bind(items[position])
            holder.itemView.setOnClickListener {
                editHabitCallback?.onEditHabitCallback(it.tag as Int, pageId)
            }
        }

        inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val title = itemView.findViewById<TextView>(R.id.title)
            private val description = itemView.findViewById<TextView>(R.id.description)
            private val priority = itemView.findViewById<TextView>(R.id.priority)
            private val frequency = itemView.findViewById<TextView>(R.id.frequency)
            private val count = itemView.findViewById<TextView>(R.id.count)
            fun bind(item: Habit) {
                itemView.tag = item.id
                title.text = item.title
                description.text = item.description
                priority.text = item.priority
                frequency.text = if (item.frequency != "") item.frequency + " раз(а) в день" else ""
                count.text = if (item.count != "") item.count + " дней" else ""
            }
        }
    }
}