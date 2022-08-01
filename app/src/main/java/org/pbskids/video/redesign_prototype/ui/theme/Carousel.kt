package org.pbskids.video.redesign_prototype.ui.theme

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.accompanist.pager.ExperimentalPagerApi
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.SnapOffsets
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import kotlinx.coroutines.launch
import org.pbskids.video.redesign_prototype.MainActivity
import org.pbskids.video.redesign_prototype.R
import org.pbskids.video.redesign_prototype.model.SingleBox

@OptIn(ExperimentalSnapperApi::class, ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun CarouselList(
    data: List<SingleBox>,
    windowSize: MainActivity.WindowSizeClass,
    isEndless: Boolean = true,
) {


    val listState = rememberLazyListState(
        if (isEndless) Int.MAX_VALUE / 2 else 0
    )
    val lightBlue = Color(0xFF77D8F0)


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

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        ConstraintLayout(
            modifier = Modifier
                .background(lightBlue)
        ) {
            val (prevButton, nextButton, lazyRow, title) = createRefs()
            val configuration = LocalConfiguration.current
            var index2: Int

            LazyRow(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Transparent)
                    .padding(horizontal = 16.dp)
                    .constrainAs(lazyRow) {
                        height = Dimension.ratio("16:7")
                    },
                verticalAlignment = Alignment.CenterVertically,
                flingBehavior = rememberSnapperFlingBehavior(
                    listState,
                    SnapOffsets.Center,
                    snapIndex = { _, startIndex, targetIndex ->
                        targetIndex.coerceIn(startIndex - 7, startIndex + 7)
                    },
                )
            )
            {

                items(
                    count = if (isEndless) Int.MAX_VALUE else data.size,
                    itemContent = { dataIndex: Int ->
                        val index = dataIndex % data.size
                        index2 = index
                        val itemWidth =
                            if (index == midIndex) screenWidth / 8 * 6 else screenWidth / 8 * 6
                        val isMiddleIndex = (index == midIndex)
                        Box(

                            modifier = Modifier
                                .background(Color.Transparent)
                                //makes corners round
                                .animateContentSize { initialValue, targetValue -> }
                                .onGloballyPositioned { coordinates ->
                                    if (isMiddleIndex) {
                                        size = coordinates.size.toSize()
                                    }
                                },


                            ) {
                            CarouselCustomItem(data[index], itemWidth.dp, isMiddleIndex)
                        }
                    })
            }

            if (windowSize >= MainActivity.WindowSizeClass.MEDIUM) {
                val buttonVectorSize = if (windowSize == MainActivity.WindowSizeClass.MEDIUM) {
                    (screenWidth / 16).dp
                } else {
                    (screenWidth / 16).dp
                }


                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White
                    ),
                    onClick = {
                        coroutineScope.launch {
                            // Animate scroll to the 10th item
                            listState.animateScrollToItem(
                                listState.firstVisibleItemIndex,
                                scrollOffset = -340
                            )
                        }
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .width(buttonVectorSize)
                        .height(buttonVectorSize * 2)
                        .constrainAs(prevButton) { centerVerticallyTo(lazyRow) },

                    ) {
                    Image(
                        painterResource(R.drawable.ic_baseline_keyboard_arrow_left_24),
                        "content description",
                        contentScale = ContentScale.Crop
                    )
                }

                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White
                    ),
                    onClick = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(
                                listState.firstVisibleItemIndex + 2,
                                scrollOffset = -340
                            )
                        }
                    }, modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .width(buttonVectorSize)
                        .height(buttonVectorSize * 2)
                        .constrainAs(nextButton) {
                            centerVerticallyTo(lazyRow)
                            end.linkTo(lazyRow.end)
                        }) {
                    Image(
                        painterResource(R.drawable.ic_baseline_keyboard_arrow_right_24),
                        "content description",
                        contentScale = ContentScale.Crop
                    )
                }
            }
            if (midIndex != -1) {
                Text(
                    text = "Title: ${data[midIndex].name}",
                    modifier = Modifier.constrainAs(title) {
                        centerHorizontallyTo(lazyRow)
                        top.linkTo(lazyRow.bottom)
                    },
                )
            }

        }
    }

    Log.v("!!!!", size.width.toString())
    Log.v("!!!!", screenWidthPixels.toString())

}


//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    RedesignPrototypeTheme {
//        val windowSize = rememberWindowSize()
//        val singleBoxRepository = SingleBoxRepository()
//        val data = singleBoxRepository.getAllData()
//        CarouselList(data = data, windowSizeClass)
//    }
//}