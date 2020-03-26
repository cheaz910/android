package com.example.task2

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_add_habit.*
import kotlinx.android.synthetic.main.fragment_habits.*
import java.util.*

interface AddHabitCallback {
    fun onHabitAdded(item: Habit)
}

class AddHabit : Fragment() {
    companion object {
        private const val ARGS_NAME = "args_name"
        fun newInstance(name: String) : AddHabit {
            val fragment = AddHabit()
            val bundle = Bundle()
            bundle.putString(name, ARGS_NAME)
            fragment.arguments = bundle
            return fragment
        }
    }

    var name: String = ""
    var callback: AddHabitCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = activity as AddHabitCallback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_add_habit, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnCreateHabit.setOnClickListener {
//            activity!!.supportFragmentManager.beginTransaction()
//                .replace(R.id.container, MainFragment(), "tag")
//                .commit()
            var formType: String;
            if (radioUseful.isChecked)
                formType = "полезная"
            else
                formType = "вредная"
            val item = Habit(formTitle.text.toString(),
                formDescription.text.toString(),
                formPriorities.selectedItem.toString(),
                formType,
                formCount.text.toString(),
                formFrequency.text.toString())
            callback?.onHabitAdded(item)
        }
    }
}