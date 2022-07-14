package org.pbskids.video.redesign_prototype

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.SnapOffsets
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import org.pbskids.video.redesign_prototype.model.SingleBox
import org.pbskids.video.redesign_prototype.repository.SingleBoxRepository
import org.pbskids.video.redesign_prototype.ui.theme.CustomItem
import org.pbskids.video.redesign_prototype.ui.theme.ItemDimensions
import org.pbskids.video.redesign_prototype.ui.theme.RedesignPrototypeTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RedesignPrototypeTheme {
                val singleBoxRepository = SingleBoxRepository()
                val data = singleBoxRepository.getAllData()
                CircularList(data = data)

            }
        }
    }
}



@OptIn(ExperimentalSnapperApi::class)
@Composable
fun CircularList(
    data: List<SingleBox>,
    modifier: Modifier = Modifier,
    isEndless: Boolean = true
) {
    val listState = rememberLazyListState(
        if (isEndless) Int.MAX_VALUE / 2 else 0
    )

    val configuration = LocalConfiguration.current
    val itemDimensions = ItemDimensions()
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val contentPadding = PaddingValues(start = screenWidth/2 - (itemDimensions.itemWidth/2))
    BoxWithConstraints {
        LazyRow(
            state = listState,
            modifier = modifier,
            contentPadding = contentPadding,
            flingBehavior = rememberSnapperFlingBehavior(listState, SnapOffsets.Start, snapIndex = { _, startIndex, targetIndex ->
                targetIndex.coerceIn(startIndex - 7, startIndex + 7)
            }),

        ) {
            items(
                count = if (isEndless) Int.MAX_VALUE else data.size,
                itemContent = {
                    val index = it % data.size
                    CustomItem(data[index])    // item composable

                    //Animated content that makes items smaller when not selected


                },

            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RedesignPrototypeTheme {
        val singleBoxRepository = SingleBoxRepository()
        val data = singleBoxRepository.getAllData()
        CircularList(data = data)
    }
}

