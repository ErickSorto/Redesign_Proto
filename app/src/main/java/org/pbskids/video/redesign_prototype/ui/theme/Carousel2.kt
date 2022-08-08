package org.pbskids.video.redesign_prototype.ui.theme

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.LayoutInfo
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.util.lerp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.SnapOffsets
import dev.chrisbanes.snapper.SnapperLayoutInfo
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import kotlinx.coroutines.launch
import org.pbskids.video.redesign_prototype.model.SingleBox
import org.pbskids.video.redesign_prototype.repository.SingleBoxRepository
import kotlin.math.absoluteValue

@OptIn(ExperimentalSnapperApi::class, ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun CarouselList2(
    data: List<SingleBox>,
    modifier: Modifier = Modifier,
    isEndless: Boolean = true,
) {


    val listState = rememberLazyListState(
        if (isEndless) Int.MAX_VALUE / 2 else 0
    )

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenWidthPixels = screenWidth.dp * (configuration.densityDpi / 160)
    val screenHeight = configuration.screenHeightDp

    val coroutineScope = rememberCoroutineScope()

    var size by remember { mutableStateOf(Size.Zero) }

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

    ConstraintLayout(
        modifier = Modifier
            .background(Color.Green).fillMaxWidth()
    ) {
        val (prevButton, nextButton, lazyRow) = createRefs()



        HorizontalPager(count = 10, itemSpacing = 0.dp) { page ->
            // Our page content
            Text(
                text = "Page: $page",
                modifier = Modifier.padding(16.dp).wrapContentSize()
            )
        }
        HorizontalPager(
            count = data.size,
            modifier = Modifier.background(Color.Black).constrainAs(lazyRow) {
                height = Dimension.ratio("16:7")
            }) { index ->
            // Our page content
            Box(
                Modifier
                    .height(200.dp)
                    .width(300.dp)
                    .padding(0.dp)
                    .graphicsLayer {
                        // Calculate the absolute offset for the current page from the
                        // scroll position. We use the absolute value which allows us to mirror
                        // any effects for both directions
                        val pageOffset = calculateCurrentOffsetForPage(index).absoluteValue

                        // We animate the scaleX + scaleY, between 85% and 100%
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        // We animate the alpha, between 50% and 100%
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }


            ) {
               // CarouselCustomItem(data[index], 100.dp, true, windowSizeWidth = screenWidthPixels)
            }
        }
    }
}