package com.example.task2

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


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
    private var mainFragment = MainFragment.newInstance(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppDatabase.init(applicationContext)

        db = AppDatabase.getInstance()
        val t = NetworkService.instance.jSONApi
        GlobalScope.launch {
            val z = t.getAll()
            db.habitDao().deleteAll()
            db.habitDao().insertAll(z)
            val t = z.get(0)
        }

        setContentView(R.layout.activity_main)
        navigationDrawer.setNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, mainFragment, "mainFragment")
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
                        .replace(R.id.container, mainFragment, "mainFragment")
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
            .show(supportFragmentManager.findFragmentByTag("mainFragment")!!)
            .hide(supportFragmentManager.findFragmentByTag("addHabit")!!)
            .commit()
    }

    override fun onAddHabitButtonPressed(pageId: Int?) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .hide(supportFragmentManager.findFragmentByTag("mainFragment")!!)
            .add(R.id.container, AddHabit.newInstance(null, pageId), "addHabit")
            .commit()
    }

    override fun onEditHabitCallback(id: String, pageId: Int?) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .hide(supportFragmentManager.findFragmentByTag("mainFragment")!!)
            .add(R.id.container, AddHabit.newInstance(id, pageId), "addHabit")
            .commit()
    }
}