package com.example.task2

import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(),
    AddHabitCallback,
    NavigationView.OnNavigationItemSelectedListener,
    AddHabitButtonPressedCallback,
    EditHabitCallback {
    companion object {
        const val fragmentHomeKey = "homeFragment"
        const val fragmentAboutKey = "aboutFragment"
    }

    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppDatabase.init(applicationContext)

        setContentView(R.layout.activity_main)
        navigationDrawer.setNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, MainFragment.newInstance(0))
                .commit()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_home -> {
                val fragmentPopped = supportFragmentManager.popBackStackImmediate(fragmentHomeKey, 0)
                if (!fragmentPopped) {
                    supportFragmentManager.beginTransaction()
                        .addToBackStack(fragmentHomeKey)
                        .replace(R.id.container, MainFragment.newInstance(0))
                        .commit()
                }
            }
            R.id.menu_item_about -> {
                val fragmentPopped = supportFragmentManager.popBackStackImmediate(fragmentAboutKey, 0)
                if (!fragmentPopped) {
                    supportFragmentManager.beginTransaction()
                        .addToBackStack(fragmentAboutKey)
                        .replace(R.id.container, About())
                        .commit()
                }
            }
        }
        navigationDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onHabitAdded(pageId: Int?) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance(pageId))
            .commit()
    }

    override fun onAddHabitButtonPressed(pageId: Int?) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container, AddHabit.newInstance(null, pageId))
            .commit()
    }

    override fun onEditHabitCallback(id: Int, pageId: Int?) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container, AddHabit.newInstance(id, pageId))
            .commit()
    }
}