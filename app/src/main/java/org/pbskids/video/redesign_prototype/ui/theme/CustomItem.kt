package org.pbskids.video.redesign_prototype.ui.theme

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.pbskids.video.redesign_prototype.model.SingleBox


@Composable
fun CustomItem(singleBox: SingleBox, height: Dp) {
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val itemWidth = screenWidth / 8 * 6
    val itemHeightHorizontal = screenHeight / 8 * 6
    val itemHeightVertical = screenHeight / 8 * 3

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding()
            .background(Color.DarkGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(80.dp),
    ) {

        Image(
            painter = painterResource(id = singleBox.imageID),
            modifier = Modifier
                .height(
                    when (configuration.orientation) {
                        Configuration.ORIENTATION_LANDSCAPE -> {
                            height
                        }
                        else -> {
                            height
                        }
                    }
                )
                .width(itemWidth)
                .padding(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}