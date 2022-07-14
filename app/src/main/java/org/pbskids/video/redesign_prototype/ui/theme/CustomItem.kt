package org.pbskids.video.redesign_prototype.ui.theme

import android.util.Size
import androidx.annotation.Dimension
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.pbskids.video.redesign_prototype.model.SingleBox

@Composable
fun CustomItem(singleBox: SingleBox){
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val itemWidth = 300.dp

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.DarkGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(80.dp),
    ){

        Image(painter = painterResource(id = singleBox.imageID), modifier = Modifier.height(200.dp).width(300.dp).padding(32.dp), contentDescription = null, contentScale = ContentScale.Crop)
    }
}