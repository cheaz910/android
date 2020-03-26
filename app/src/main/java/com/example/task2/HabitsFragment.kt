package com.example.task2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_habits.*
import kotlin.collections.ArrayList


class HabitsFragment: Fragment() {
    private var myAdapter: MainAdapter? = null;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habits, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            myAdapter = MainAdapter(it.getParcelableArrayList<Habit>("habits") as ArrayList<Habit>)
            val myRecycler: RecyclerView = view.findViewById(R.id.myRecycler)
            myRecycler.adapter = myAdapter
        }
        floatingActionButton2.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.container, AddHabit.newInstance("title"))
                .commit()
        }
    }

    class MainAdapter(var items: List<Habit>) :
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
        }

        inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val title = itemView.findViewById<TextView>(R.id.title)
            private val description = itemView.findViewById<TextView>(R.id.description)
            private val priority = itemView.findViewById<TextView>(R.id.priority)
            private val frequency = itemView.findViewById<TextView>(R.id.frequency)
            private val count = itemView.findViewById<TextView>(R.id.count)
            fun bind(item: Habit) {
                title.text = item.title
                description.text = item.description
                priority.text = item.priority
                frequency.text = item.frequency
                count.text = item.count
            }
        }
    }
}