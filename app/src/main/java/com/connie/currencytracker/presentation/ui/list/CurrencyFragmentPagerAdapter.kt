package com.connie.currencytracker.presentation.ui.list

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class CurrencyFragmentPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val listTypes: List<ListType>,
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = listTypes.size

    override fun createFragment(position: Int): Fragment {
        return CurrencyListFragment.newInstance(listTypes[position])
    }
}