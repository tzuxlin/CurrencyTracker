package com.connie.currencytracker.presentation.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.connie.currencytracker.R
import com.connie.currencytracker.databinding.ActivityDemoBinding
import com.connie.currencytracker.presentation.ui.list.CurrencyFragmentPagerAdapter
import com.connie.currencytracker.presentation.ui.list.ListType


class DemoActivity : AppCompatActivity(), BottomNavBarController {

    private val binding: ActivityDemoBinding
        get() = _binding ?: error("View binding is only available after onCreate().")
    private var _binding: ActivityDemoBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        ActivityDemoBinding.inflate(layoutInflater).also {
            _binding = it
            setContentView(it.root)
        }
        setupViews()
        setupViewModel()
    }

    private fun setupViews() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_crypto -> {
                    binding.viewPager.setCurrentItem(0, true)
                    true
                }

                R.id.navigation_fiat -> {
                    binding.viewPager.setCurrentItem(1, true)
                    true
                }

                else -> false
            }
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNavigation.menu.getItem(position).isChecked = true
            }
        })
    }

    override fun toggleVisibility(isVisible: Boolean) {
        binding.bottomNavigation.isVisible = isVisible
    }

    private fun setupViewModel() {
        binding.viewPager.adapter =
            CurrencyFragmentPagerAdapter(
                this,
                listOf(ListType.CRYPTO, ListType.FIAT)
            )
    }

}