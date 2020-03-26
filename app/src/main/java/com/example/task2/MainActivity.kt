package com.example.task2

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), AddHabitCallback, NavigationView.OnNavigationItemSelectedListener {
    private val badHabits = mutableListOf<Habit>(
        Habit(
            "Привычка1", "Описание привычки1", "Высокий", "Вредная",
            "", "1 раз в день"
        )
    )

    private val goodHabits = mutableListOf<Habit>(
        Habit(
            "Привычка2", "Описание привычки2", "Низкий", "Полезная",
            "", "1 раз в неделю"
        )
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<NavigationView>(R.id.navigationDrawer).setNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, getFragmentHabbits())
                .commit()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.menu_item_home -> {
                Log.i("TAG", "item_home")
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, getFragmentHabbits())
                    .commit()
            }
            R.id.menu_item_about -> {
                Log.i("TAG", "item_about")
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, About())
                    .commit()
            }
        }
        navigationDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun getFragmentHabbits(): MainFragment {
        val bundle = Bundle()
        val fragmentInstance = MainFragment()
        fragmentInstance.arguments = bundle
        bundle.putParcelableArrayList("goodHabits", goodHabits as ArrayList<out Parcelable>)
        bundle.putParcelableArrayList("badHabits", badHabits as ArrayList<out Parcelable>)
        return fragmentInstance
    }

    override fun onHabitAdded(item: Habit) {
        if (item.type == "полезная")
            goodHabits.add(item)
        else
            badHabits.add(item)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, getFragmentHabbits())
            .commit()
    }
}