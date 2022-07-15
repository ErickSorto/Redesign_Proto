package org.pbskids.video.redesign_prototype

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.SnapOffsets
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import org.pbskids.video.redesign_prototype.model.SingleBox
import org.pbskids.video.redesign_prototype.repository.SingleBoxRepository
import org.pbskids.video.redesign_prototype.ui.theme.CustomItem
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

@OptIn(ExperimentalSnapperApi::class, ExperimentalFoundationApi::class)
@Composable
fun CircularList(
    data: List<SingleBox>,
    modifier: Modifier = Modifier,
    isEndless: Boolean = true,
) {
    val listState = rememberLazyListState(
        if (isEndless) Int.MAX_VALUE / 2 else 0
    )

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val contentPadding = PaddingValues()

    val midIndex by remember(listState.firstVisibleItemIndex) {
        derivedStateOf {
            listState.layoutInfo.visibleItemsInfo.run {
                val firstVisibleIndex = listState.firstVisibleItemIndex
                if (isEmpty()) -1
                else if (isEndless) {
                    if (firstVisibleIndex % data.size + 1 == data.size) {
                        0
                    } else {
                        firstVisibleIndex % data.size + 1
                    }
                } else firstVisibleIndex
            }
        }
    }

    BoxWithConstraints {
        LazyRow(
            state = listState,
            modifier = Modifier.padding(horizontal = 16.dp),
            contentPadding = contentPadding,
            flingBehavior = rememberSnapperFlingBehavior(
                listState,
                SnapOffsets.Center,
                snapIndex = { _, startIndex, targetIndex ->
                    targetIndex.coerceIn(startIndex - 7, startIndex + 7)
                })

        )
        {
            items(
                count = if (isEndless) Int.MAX_VALUE else data.size,
                itemContent = {
                    val index = it % data.size
                    val padding = if (index == midIndex) 24.dp else 40.dp
                    val itemHeight =
                        if (index == midIndex) screenHeight / 8 * 6 else screenHeight / 8 * 5
                    Box(
                        Modifier
                            .background(Color.Transparent, RoundedCornerShape(8.dp))
                            .padding(top = padding, bottom = 32.dp)
                    ) {
                        CustomItem(data[index], itemHeight)
                    }
                }
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

