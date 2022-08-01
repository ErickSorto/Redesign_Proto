package org.pbskids.video.redesign_prototype

import androidx.fragment.app.Fragment
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.ContentProviderCompat.requireContext
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.SnapOffsets
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import kotlinx.coroutines.launch
import org.pbskids.video.redesign_prototype.model.SingleBox
import org.pbskids.video.redesign_prototype.repository.SingleBoxRepository
import org.pbskids.video.redesign_prototype.ui.theme.CarouselCustomItem
import org.pbskids.video.redesign_prototype.ui.theme.RedesignPrototypeTheme


class CarouselFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         return ComposeView(requireContext()).apply {

             setContent {

            RedesignPrototypeTheme {
                val singleBoxRepository = SingleBoxRepository()
                val data = singleBoxRepository.getAllData()
                CircularList(data = data)
            }
        }
        }
    }}


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
        val screenWidth = configuration.screenWidthDp
        val screenWidthPixels = screenWidth.dp * (configuration.densityDpi/160)
        val screenHeight = configuration.screenHeightDp
        val contentPadding = PaddingValues()
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


        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ConstraintLayout(
                modifier = Modifier
                    .background(Color.Green)
            ) {
                val (prevButton, nextButton, lazyRow) = createRefs()
                val configuration = LocalConfiguration.current
                LazyRow(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Green)
                        .padding(horizontal = 16.dp)
                        .constrainAs(lazyRow) {
                            height = Dimension.ratio("16:7")
                        },
                    contentPadding = (contentPadding),
                    verticalAlignment = Alignment.CenterVertically,
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
                        itemContent = {dataIndex: Int ->
                            val index = dataIndex % data.size
                            val state = rememberSaveable(dataIndex) { mutableStateOf(listState) }
                            val itemWidth =
                                if (index == midIndex) screenWidth / 8 * 6 else screenWidth / 8 * 6
                            val isMiddleIndex = (index == midIndex)
                            Box(
                                modifier = Modifier
                                    .animateContentSize { initialValue, targetValue -> }
                                    .onGloballyPositioned { coordinates ->
                                        if(isMiddleIndex){
                                            size = coordinates.size.toSize()
                                        }
                                    },

                                ) {
                                CarouselCustomItem(data[index], itemWidth.dp, isMiddleIndex)
                            }
                        })
                }

                when (configuration.orientation) {
                    Configuration.ORIENTATION_LANDSCAPE -> {
                        Button(onClick = {
                            coroutineScope.launch {
                                // Animate scroll to the 10th item
                                listState.animateScrollToItem(
                                    listState.firstVisibleItemIndex,
                                    scrollOffset = -340
                                )
                            }
                        }, modifier = Modifier
                            .height(screenHeight.dp / 2)
                            .constrainAs(prevButton) { centerVerticallyTo(lazyRow) }) {
                            Text("Prev")
                        }

                        Button(onClick = {
                            coroutineScope.launch {
                                listState.animateScrollToItem(
                                    listState.firstVisibleItemIndex + 2,
                                    scrollOffset = -340
                                )
                            }
                        }, modifier = Modifier
                            .height(screenHeight.dp / 2)
                            .constrainAs(nextButton) {
                                centerVerticallyTo(lazyRow)
                                end.linkTo(lazyRow.end)
                            }) {
                            Text("next")
                        }
                    }
                }
            }

            Log.v("!!!!", size.width.toString())
            Log.v("!!!!", screenWidthPixels.toString())

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


