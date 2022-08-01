package org.pbskids.video.redesign_prototype.ui.theme

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import org.pbskids.video.redesign_prototype.model.SingleBox


    @Composable
    fun GridCustomItem(singleBox: SingleBox, itemWidth: Dp) {
        val configuration = LocalConfiguration.current

        val screenHeight = configuration.screenHeightDp.dp
        val screenWidth = configuration.screenWidthDp.dp



        ConstraintLayout {
            val (layoutItem, item, title) = createRefs()
            ConstraintLayout(
                modifier = Modifier
                    .background(Color.Transparent)
                    .constrainAs(layoutItem) {
                        height = Dimension.ratio("16:9")
                    }
                    .width(
                        when (configuration.orientation) {
                            Configuration.ORIENTATION_LANDSCAPE -> {
                                itemWidth
                            }
                            else -> {
                                itemWidth
                            }
                        }
                    ),
            ) {

                val painter = rememberAsyncImagePainter(model = singleBox.imageID)
                val painterState = painter.state
                Image(
                    painter = painter,
                    modifier = Modifier
                        .constrainAs(item) {
                        }
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                    ,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,

                    )
            }
            Text(text = singleBox.name, modifier = Modifier
                .padding(bottom = 8.dp)
                .constrainAs(title){
                centerHorizontallyTo(parent)
                top.linkTo(layoutItem.bottom)
            })

        }
    }
