package org.pbskids.video.redesign_prototype.ui.theme

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
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
    windowSizeWidth: MainActivity.WindowSizeClass,
    windowSizeHeight: MainActivity.WindowSizeClass,
    isEndless: Boolean = true,
) {

    val compactWidth = windowSizeWidth == MainActivity.WindowSizeClass.COMPACT
    val mediumWidth = windowSizeWidth == MainActivity.WindowSizeClass.MEDIUM
    val expandedWidth = windowSizeWidth == MainActivity.WindowSizeClass.EXPANDED
    val compactHeight = windowSizeHeight == MainActivity.WindowSizeClass.COMPACT

    val listState = rememberLazyListState(
        if (isEndless) Int.MAX_VALUE / 2 else 0
    )
    val lightBlue = Color(0xFF2FC0EB)
    val darkerBlue = Color(0xFF0081CA)

    val pbsSansFamily = FontFamily(
        Font(R.font.pbssansblack),
        Font(R.font.pbssansbold, FontWeight.Bold),
        Font(R.font.pbssanslight, FontWeight.Light),
        Font(R.font.pbssansmedium)
    )

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenWidthPixels = screenWidth.dp * (configuration.densityDpi / 160)
    val screenHeight = configuration.screenHeightDp

    val coroutineScope = rememberCoroutineScope()

    var size by remember { mutableStateOf(Size.Zero) }

    val itemWidth = if(compactWidth) {
        screenWidth / 8 * 6
    }else{
        screenWidth / 8 * 4
    }

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

    ConstraintLayout() {
        val (carouselLayout) = createRefs()
        ConstraintLayout(
            modifier = Modifier
                .background(lightBlue)
                .constrainAs(carouselLayout) {
                    height = Dimension.ratio(
                        if (compactWidth) {
                            "16:9"
                        } else {
                            "16:7"
                        }
                    )
                }
        ) {
            val (prevButton, nextButton, lazyRow, title, logo, buttonLayout) = createRefs()
            val configuration = LocalConfiguration.current
            var index2: Int

            Image(
                painterResource(id = R.drawable.pbs_background),
                contentDescription = "",
                contentScale = ContentScale.Crop, // or some other scale
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                alpha = 0.8F
            )
            LazyRow(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Transparent)
                    .padding(
                        horizontal = if (compactWidth) {
                            16.dp
                        } else {
                            168.dp
                        }
                    )
                    .constrainAs(lazyRow) {
                        height = Dimension.ratio(
                            if (compactWidth) {
                                "16:9"
                            } else {
                                "16:7"
                            }
                        )
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

                        val isMiddleIndex = (index == midIndex)
                        Column(
                            modifier = Modifier
                                .background(Color.Transparent)
                                .focusable(true)
                                .onFocusChanged { focused ->
                                    if (focused.isFocused) {
                                        Log.d("CarouselList", "Focused $index")
                                    }
                                }
                                .animateContentSize { initialValue, targetValue -> }
                                .onGloballyPositioned { coordinates ->
                                    if (isMiddleIndex) {
                                        size = coordinates.size.toSize()
                                    }
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,


                            ) {
                            CarouselCustomItem(data[index], itemWidth.dp, isMiddleIndex, windowSizeWidth)
                            Box(
                                modifier = Modifier
                                    .width(itemWidth.dp)
                                    .clip(
                                        RoundedCornerShape(
                                            bottomEndPercent = 50,
                                            bottomStartPercent = 50
                                        )
                                    )
                                    .background(darkerBlue)
                                    .animateContentSize { initialValue, targetValue -> }){
                                if (midIndex != -1 && midIndex == index) {
                                    Text(
                                        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum accumsan viverra metus, non dictum mauris bibendum elementum.",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = 16.dp,
                                                end = 16.dp,
                                                top = 8.dp,
                                                bottom = 8.dp
                                            ),
                                        fontSize = 12.sp,
                                        textAlign = TextAlign.Center,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis,
                                        color = Color.White,
                                        fontFamily = pbsSansFamily,
                                        fontWeight = FontWeight.Light
                                    )
                                }
                            }
                        }
                    })
            }

            if (windowSizeWidth >= MainActivity.WindowSizeClass.MEDIUM) {
                val buttonVectorSize = if (windowSizeWidth == MainActivity.WindowSizeClass.MEDIUM) {
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
                        .padding(start = 168.dp - (buttonVectorSize / 2))
                        .clip(RoundedCornerShape(15.dp))
                        .width(buttonVectorSize)
                        .height(buttonVectorSize * 2)
                        .constrainAs(prevButton) {
                            centerVerticallyTo(lazyRow)
                        },

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
                        .padding(end = 168.dp - (buttonVectorSize / 2))
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
            val painter2 = rememberAsyncImagePainter(model = R.drawable.pbs_rebrand)
            val painterState2 = painter2.state

            if (!compactWidth) {
                Image(
                    painter = painter2,
                    modifier = Modifier
                        .constrainAs(logo) {

                            start.linkTo(carouselLayout.start)
                        }
                        .width((screenWidth / 8).dp)
                        .height((screenWidth / 8).dp)
                        .padding(8.dp),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                )
            }
            if(!compactWidth){
                Box(modifier = Modifier.constrainAs(buttonLayout){
                    centerHorizontallyTo(parent)
                }){
                    ButtonLayout(itemWidth = itemWidth.dp)
                }
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