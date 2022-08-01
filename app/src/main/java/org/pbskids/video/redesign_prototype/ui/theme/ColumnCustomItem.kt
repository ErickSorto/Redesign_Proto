package org.pbskids.video.redesign_prototype.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import org.pbskids.video.redesign_prototype.MainActivity
import org.pbskids.video.redesign_prototype.R
import org.pbskids.video.redesign_prototype.model.SingleBox
import org.pbskids.video.redesign_prototype.repository.SingleBoxRepository


@Composable
fun ColumnCustomItem(
    singleBox: SingleBox,
    itemWidth: Dp,
    windowSizeWidth: MainActivity.WindowSizeClass,
    windowSizeHeight: MainActivity.WindowSizeClass
) {
    val configuration = LocalConfiguration.current
    val compactWidth = windowSizeWidth == MainActivity.WindowSizeClass.COMPACT
    val mediumWidth = windowSizeWidth == MainActivity.WindowSizeClass.MEDIUM
    val expandedWidth = windowSizeWidth == MainActivity.WindowSizeClass.EXPANDED
    val compactHeight = windowSizeHeight == MainActivity.WindowSizeClass.COMPACT

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val pbsSansFamily = FontFamily(
        Font(R.font.pbssansblack),
        Font(R.font.pbssansbold),
        Font(R.font.pbssanslight, FontWeight.Light),
        Font(R.font.pbssansmedium)

    )



    ConstraintLayout(
        modifier = Modifier
            .width(itemWidth)
            .background(color = Color.Transparent)
    ) {
        val (layoutItem, item, title) = createRefs()
        ConstraintLayout(
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .constrainAs(layoutItem) {
                    height = Dimension.ratio("16:9")
                    centerHorizontallyTo(parent)
                },
        ) {
            val painter = rememberAsyncImagePainter(model = singleBox.imageID)
            val painterState = painter.state
            Image(
                painter = painter,
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .constrainAs(item) {}
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
                contentDescription = null,

                )
        }
        Column(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .width(itemWidth)
                .constrainAs(item) {
                    centerHorizontallyTo(layoutItem)
                    top.linkTo(layoutItem.bottom)
                }, horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (compactWidth || mediumWidth || compactHeight) {
                Text(
                    text = singleBox.name,
                    modifier = Modifier
                        .padding(horizontal = 16.dp), //make textsize 32
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White,
                    fontFamily = pbsSansFamily
                )
            }

            Text(
                text = "In this episode, we take a look at how to make a simple filler description.",
                modifier = Modifier
                    .padding(horizontal = 16.dp), //make text go to next line instead of leaving screen
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White,
                fontFamily = pbsSansFamily,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ColumnItemPreview() {
    RedesignPrototypeTheme {
        val singleBoxRepository = SingleBoxRepository()
        val data = singleBoxRepository.getAllData()
        // ColumnItem(data[6], 200.dp)
    }
}
