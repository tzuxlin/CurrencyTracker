package com.connie.currencytracker.presentation.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.connie.currencytracker.presentation.ext.getParcelableCompat
import com.connie.currencytracker.presentation.theme.CurrencyTrackerTheme
import com.connie.currencytracker.presentation.ui.BottomNavBarController
import com.connie.currencytracker.presentation.ui.NavController
import com.connie.currencytracker.presentation.ui.search.SearchScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class CurrencyListFragment : Fragment() {

    companion object {
        private const val ARG_TYPE = "ARG_TYPE"

        fun newInstance(type: ListType): CurrencyListFragment {
            return CurrencyListFragment().apply {
                arguments = bundleOf(ARG_TYPE to type)
            }
        }
    }

    private val listType: ListType
        get() = arguments?.getParcelableCompat(ARG_TYPE)
            ?: error("require argument: $ARG_TYPE")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                CurrencyTrackerTheme {
                    val navController = rememberNavController()
                    val navControllerImpl = object : NavController {
                        override fun popBackStack() {
                            navController.popBackStack()
                        }

                        override fun navigate(destination: NavController.Destination) {
                            navController.navigate(destination.path)
                        }
                    }

                    NavHost(
                        navController = navController,
                        startDestination = NavController.Destination.LIST.path,
                    ) {
                        composable(NavController.Destination.LIST.path) {
                            (requireActivity() as? BottomNavBarController)?.toggleVisibility(true)
                            CurrencyListScreen(navControllerImpl, listType)
                        }
                        composable(NavController.Destination.SEARCH.path) {
                            (requireActivity() as? BottomNavBarController)?.toggleVisibility(false)
                            SearchScreen(navControllerImpl)
                        }
                    }
                }
            }
        }
    }
}