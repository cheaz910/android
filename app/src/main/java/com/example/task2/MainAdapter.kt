package com.example.task2

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainAdapter(var items: List<Habit>, var editHabitCallback: EditHabitCallback?,
                  private val pageId: Int?, val context: Context) :
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
            editHabitCallback?.onEditHabitCallback(it.tag as String, pageId)
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun getResourcesByLocale(): Resources? {
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(Locale("ru"))
        return context.createConfigurationContext(configuration).resources
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
            priority.text = Constants.intToPriorities[item.priority]
            frequency.text = getResourcesByLocale()!!.getQuantityString(R.plurals.frequencyPlurals,
                item.frequency!!,
                item.frequency!!)
            count.text = getResourcesByLocale()!!.getQuantityString(R.plurals.countPlurals,
                item.count!!,
                item.count!!)
        }
    }
}