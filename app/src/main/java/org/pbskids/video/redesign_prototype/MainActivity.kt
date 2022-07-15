package org.pbskids.video.redesign_prototype

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.SnapOffsets
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import org.pbskids.video.redesign_prototype.model.SingleBox
import org.pbskids.video.redesign_prototype.repository.SingleBoxRepository
import org.pbskids.video.redesign_prototype.ui.theme.CustomItem
import org.pbskids.video.redesign_prototype.ui.theme.RedesignPrototypeTheme
import org.w3c.dom.Text


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
    val contentPadding = PaddingValues()

    val midIndex by remember(listState.firstVisibleItemIndex) {
        derivedStateOf {
            listState.layoutInfo.visibleItemsInfo.run {
                val firstVisibleIndex = listState.firstVisibleItemIndex
                if (isEmpty()) -1
                else if(isEndless){
                    firstVisibleIndex % data.size + 1
                }
                else firstVisibleIndex
            }
        }
    }
    BoxWithConstraints {
        LazyRow(
            state = listState,
            modifier = Modifier,
            contentPadding = contentPadding,
            flingBehavior = rememberSnapperFlingBehavior(listState, SnapOffsets.Center, snapIndex = { _, startIndex, targetIndex ->
                targetIndex.coerceIn(startIndex - 7, startIndex + 7)
            })

        )
        {
            items(
                count = if (isEndless) Int.MAX_VALUE else data.size,
                itemContent = {
                    val index = it % data.size
                    val padding = if (index == midIndex) 13.dp else 1.dp
                    Box(
                        Modifier
                            .background(Color.Gray, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = padding)
                    ){
                        CustomItem(data[index])
                    }
                    Text(
                        text = index.toString(),
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.subtitle1
                    )

                }
            )


        }

    }
    Text(
        text = listState.firstVisibleItemIndex.toString(),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.subtitle1
    )

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



@ExperimentalAnimationApi
fun addAnimation(duration: Int = 800): ContentTransform {
    return slideInVertically(animationSpec = tween(durationMillis = duration)) { height -> height } + fadeIn(
        animationSpec = tween(durationMillis = duration)
    ) with slideOutVertically(animationSpec = tween(durationMillis = duration)) { height -> -height } + fadeOut(
        animationSpec = tween(durationMillis = duration)
    )
}

