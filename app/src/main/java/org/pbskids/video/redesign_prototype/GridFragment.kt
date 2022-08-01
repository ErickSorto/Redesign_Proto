package org.pbskids.video.redesign_prototype


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import org.pbskids.video.redesign_prototype.model.SingleBox
import org.pbskids.video.redesign_prototype.repository.SingleBoxRepository
import org.pbskids.video.redesign_prototype.ui.theme.GridCustomItem
import org.pbskids.video.redesign_prototype.ui.theme.RedesignPrototypeTheme
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

class GridFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setContent {

                RedesignPrototypeTheme {
                    val singleBoxRepository = SingleBoxRepository()
                    val data = singleBoxRepository.getAllData()
                    GridLayout(data = data)
                }
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun GridLayout(
        data: List<SingleBox>,
    ) {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp

        ConstraintLayout() {
            val (gridFrame, item) = createRefs()
            ConstraintLayout(modifier = Modifier
                .constrainAs(gridFrame) { height = Dimension.ratio("16:8") }
                .fillMaxWidth()
                .height(100.dp)
                .background(color = Color.Magenta)) {
                LazyHorizontalGrid(
                    rows = GridCells.Adaptive(2.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    content = {
                        items(data.size) { index ->
                            Box(modifier = Modifier
                                .padding()
                                .constrainAs(item) {}) {
                                GridCustomItem(data[index], screenWidth / 8 * 3)
                            }
                        }
                    })
                Text(text = "Hello")
            }
        }


    }
}



