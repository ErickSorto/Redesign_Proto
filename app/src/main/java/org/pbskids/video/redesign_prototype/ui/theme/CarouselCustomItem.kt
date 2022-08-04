package org.pbskids.video.redesign_prototype.ui.theme

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import org.pbskids.video.redesign_prototype.R
import org.pbskids.video.redesign_prototype.model.SingleBox


@Composable
fun CarouselCustomItem(singleBox: SingleBox, itemWidth: Dp, isMiddleIndex: Boolean) {
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp



    ConstraintLayout() {
        val (layoutItem, item, logo, itemTitle) = createRefs()
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .constrainAs(layoutItem) {
                    height = if (isMiddleIndex) {
                        Dimension.ratio("16:9")
                    } else {
                        Dimension.ratio("16:7")
                    }
                }
                .width(itemWidth),
        ) {
            val painter2 = rememberAsyncImagePainter(model = R.drawable.pbs_rebrand)
            val painter = rememberAsyncImagePainter(model = singleBox.imageID)
            val painterState = painter.state
            val painterState2 = painter2.state
            Column(
                modifier = Modifier.clip(
                    RoundedCornerShape(
                        bottomEndPercent = 25,
                        bottomStartPercent = 25
                    ),
                )
            ) {

            }
            Image(
                painter = painter,
                modifier = Modifier
                    .constrainAs(item) {
                    }
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(
                        if (isMiddleIndex) {
                            RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                        } else {
                            RoundedCornerShape(0.dp)
                        }
                    )
                    .animateContentSize { initialValue, targetValue -> },
                contentScale = ContentScale.Crop,
                contentDescription = null,

                )



            if (painterState is AsyncImagePainter.State.Loading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }

            }


            if (isMiddleIndex) {
                Image(
                    painter = painter2,
                    modifier = Modifier
                        .constrainAs(logo) {
                            top.linkTo(item.top)
                            end.linkTo(item.end)
                        }
                        .width(screenWidth / 8)
                        .height(screenWidth / 8)
                        .padding(8.dp),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                )
            }

        }

    }
}