package org.pbskids.video.redesign_prototype.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.pbskids.video.redesign_prototype.R

@Composable
fun ButtonLayout(itemWidth: Dp) {
    val pbsSansFamily = FontFamily(
        Font(R.font.pbssansblack),
        Font(R.font.pbssansbold, FontWeight.Bold),
        Font(R.font.pbssanslight, FontWeight.Light),
        Font(R.font.pbssansmedium)
    )
    val darkerBlue = Color(0xFF0081CA)
    Row(modifier = Modifier.padding(horizontal = 8.dp).width(itemWidth), verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = darkerBlue
            ),
            onClick = {},
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .width(itemWidth/10)
                .height(itemWidth/10)
        ) {
            Row(){
                Image(
                    painterResource(R.drawable.ic_baseline_home_24),
                    "content description",
                    contentScale = ContentScale.Fit
                )
            }
        }
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = darkerBlue
            ),
            onClick = {},
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .width(itemWidth/4)
                ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
                Image(
                    painterResource(R.drawable.ic_baseline_videogame_asset_24),
                    "content description",
                    contentScale = ContentScale.Crop
                )
                Text("GAMES",fontSize = 10.sp,maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White,
                    fontFamily = pbsSansFamily)
            }
        }
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = darkerBlue
            ),
            onClick = {},
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .width(itemWidth/4)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
                Image(
                    painterResource(R.drawable.ic_baseline_play_circle_24),
                    "content description",
                    contentScale = ContentScale.Crop
                )
                Text("VIDEOS", fontSize =10.sp, maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White,
                    fontFamily = pbsSansFamily)
            }
        }
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = darkerBlue
            ),
            onClick = {},
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .width(itemWidth/4)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
                Image(
                    painterResource(R.drawable.ic_baseline_grid_view_24),
                    "content description",
                    contentScale = ContentScale.Crop
                )
                Text("SHOWS", fontSize = 10.sp, maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White,
                    fontFamily = pbsSansFamily)
            }
        }
    }
}