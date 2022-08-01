package org.pbskids.video.redesign_prototype.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowColumn
import org.pbskids.video.redesign_prototype.MainActivity
import org.pbskids.video.redesign_prototype.model.SingleBox

@Composable
fun ItemColumn(data: List<SingleBox>, windowSizeWidth: MainActivity.WindowSizeClass,
               windowSizeHeight: MainActivity.WindowSizeClass) {
    val listState = rememberLazyListState()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val magenta = Color(0xFF9C27B0)


    LazyColumn(
        modifier = Modifier.fillMaxWidth().background(magenta).padding(horizontal = 16.dp),
        state = listState,
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(count = data.size, itemContent = { dataIndex: Int ->
            FlowColumn(modifier = Modifier) {
                Box(modifier = Modifier.fillMaxWidth())
                {
                    ColumnCustomItem(singleBox = data[dataIndex], itemWidth = screenWidth, windowSizeWidth, windowSizeHeight)
                }
            }

        })
    }
}

