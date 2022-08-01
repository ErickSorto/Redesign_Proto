package org.pbskids.video.redesign_prototype.ui.theme


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.pbskids.video.redesign_prototype.MainActivity
import org.pbskids.video.redesign_prototype.model.SingleBox

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridLayout(
    data: List<SingleBox>,
    windowSizeWidth: MainActivity.WindowSizeClass,
    windowSizeHeight: MainActivity.WindowSizeClass
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val Magenta = Color(0xFF9C27B0)

    ConstraintLayout() {
        val (gridFrame, item) = createRefs()
        ConstraintLayout(modifier = Modifier
            .constrainAs(gridFrame) {
                if (windowSizeWidth == MainActivity.WindowSizeClass.MEDIUM ) {
                    height = Dimension.ratio("16:11")
                } else {
                    height = Dimension.ratio("16:7")
                }

            }
            .fillMaxWidth()
            .height(100.dp)
            .background(Magenta)) {
            LazyHorizontalGrid(
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 24.dp),
                rows = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                content = {

                    items(data.size) { index ->
                        Box(modifier = Modifier
                            .constrainAs(item) {}) {
                            ColumnCustomItem(
                                data[index],
                                if (windowSizeWidth == MainActivity.WindowSizeClass.MEDIUM
                                ) {
                                    screenWidth / 64 * 30
                                } else {
                                    screenWidth / 32 * 10
                                },
                                windowSizeWidth,
                                windowSizeHeight
                            )
                        }
                    }
                })
            Text(text = "Hello")
        }
    }


}




