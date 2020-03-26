package com.example.task2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_habits.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlin.collections.ArrayList


class MainFragment: Fragment() {
    class MyPagerAdapter(fragmentManager: FragmentManager, private val bundle: Bundle) :
        FragmentPagerAdapter(fragmentManager) {
        // Returns total number of pages
        override fun getCount(): Int {
            return NUM_ITEMS
        }

        // Returns the fragment to display for that page
        override fun getItem(position: Int): Fragment {
            val fragmentInstance = HabitsFragment()
            val newBundle = Bundle()
            when (position) {
                0 -> {
                    newBundle.putParcelableArrayList("habits",
                        bundle.getParcelableArrayList<Habit>("goodHabits"))
                    fragmentInstance.arguments = newBundle
                }
                else -> {
                    newBundle.putParcelableArrayList("habits",
                        bundle.getParcelableArrayList<Habit>("badHabits"))
                    fragmentInstance.arguments = newBundle
                }
            }
            return fragmentInstance
        }

        // Returns the page title for the top indicator
        override fun getPageTitle(position: Int): CharSequence? {
            if (position == 0)
                return "Полезная"
            return "Вредная"
        }

        companion object {
            private const val NUM_ITEMS = 2
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            vpPager.adapter = MyPagerAdapter(childFragmentManager, it)
            tabLayout.setupWithViewPager(vpPager)
        }
    }

}