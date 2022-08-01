package org.pbskids.video.redesign_prototype

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import org.pbskids.video.redesign_prototype.ui.theme.HomeScreen


class HomeFragment(
) : Fragment() {

    companion object {
        open fun newInstance(
            windowSizeWidth: MainActivity.WindowSizeClass,
            windowSizeHeight: MainActivity.WindowSizeClass
        ): HomeFragment {
            return HomeFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("windowSizeWidth", windowSizeWidth)
                    putSerializable("windowSizeHeight", windowSizeHeight)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {

            setContent {

                HomeScreen(
                    windowSizeWidth = requireArguments().getSerializable("windowSizeWidth") as MainActivity.WindowSizeClass,
                    windowSizeHeight = requireArguments().getSerializable("windowSizeHeight") as MainActivity.WindowSizeClass
                )
            }
        }
    }
}





