package com.example.task2

import android.os.Bundle
import android.util.Log
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
    class MyPagerAdapter(fragmentManager: FragmentManager) :
        FragmentPagerAdapter(fragmentManager) {
        // Returns total number of pages
        override fun getCount(): Int {
            return NUM_ITEMS
        }

        // Returns the fragment to display for that page
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                    HabitsFragment.newInstance(Constants.HabitType.GOOD, position)
                }
                else -> {
                    HabitsFragment.newInstance(Constants.HabitType.BAD, position)
                }
            }
        }

        // Returns the page title for the top indicator
        override fun getPageTitle(position: Int): CharSequence? {
            if (position == 0)
                return goodPageTitle
            return badPageTitle
        }

        companion object {
            private const val NUM_ITEMS = 2
            private val goodPageTitle = "Полезные"
            private val badPageTitle = "Вредные"
        }
    }

    companion object {
        private const val pageIdKey = "pageId"
        fun newInstance(id: Int?) : MainFragment {
            val fragment = MainFragment()
            val bundle = Bundle()
            if (id != null) {
                bundle.putInt(pageIdKey, id)
            }
            fragment.arguments = bundle
            return fragment
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
        vpPager.adapter = MyPagerAdapter(childFragmentManager)
        if (arguments!!.containsKey(pageIdKey)) {
            vpPager.currentItem = arguments!!.getInt(pageIdKey)
        }
        tabLayout.setupWithViewPager(vpPager)
    }

}