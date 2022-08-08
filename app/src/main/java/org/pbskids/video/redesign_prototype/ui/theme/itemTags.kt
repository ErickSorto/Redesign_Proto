package org.pbskids.video.redesign_prototype.ui.theme

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.pbskids.video.redesign_prototype.R

@Composable
fun fullEpisodeTag(
    itemWidth: Dp
) {
    val pbsBlue = Color(0xFF2638C4)


    val pbsSansFamily = FontFamily(
        Font(R.font.pbssansblack),
        Font(R.font.pbssansbold, FontWeight.Bold),
        Font(R.font.pbssanslight, FontWeight.Light),
        Font(R.font.pbssansmedium)
    )

    Surface(
        color = pbsBlue.copy(alpha = 0.6f),
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .padding(8.dp)
    ){
        Row(modifier = Modifier.padding(), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.padding(start = 4.dp, top = 2.dp, bottom = 2.dp)) {
                Image(
                    painterResource(id = R.drawable.ic_baseline_star_24),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                )
            }

            Text(text = "FULL EPISODE",
                modifier = Modifier
                    .padding(start = 4.dp, end = 8.dp),
                style = TextStyle(
                    fontSize = with(LocalDensity.current) {
                        itemWidth.toSp() / 24
                    },
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White,
                fontFamily = pbsSansFamily,)
        }
    }

}

@Composable
fun newEpisodeTag(
    itemWidth: Dp
) {

    val newTagSize = itemWidth / 8
    val Magenta = Color(0xFF9C27B0)
    val yellow = Color(0xFFFDD835)
    val pbsSansFamily = FontFamily(
        Font(R.font.pbssansblack),
        Font(R.font.pbssansbold, FontWeight.Bold),
        Font(R.font.pbssanslight, FontWeight.Light),
        Font(R.font.pbssansmedium)
    )
    Box(modifier = Modifier.offset(x = -newTagSize / 3, y = -newTagSize / 3).clip(CircleShape).background(Magenta).padding(8.dp)) {
        Box(modifier = Modifier
            .width(newTagSize)
            .height(newTagSize)
            .clip(CircleShape)
            .background(yellow)
            .animateContentSize { initialValue, targetValue ->  },
            contentAlignment = Alignment.Center) {
            Text(text = "NEW",
                modifier = Modifier
                    .padding(start = 4.dp, end = 4.dp),
                style = TextStyle(
                    fontSize = with(LocalDensity.current) {
                        itemWidth.toSp() / 26
                    },
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White,
                fontFamily = pbsSansFamily,
            )
        }
    }




}