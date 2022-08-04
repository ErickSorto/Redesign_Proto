package org.pbskids.video.redesign_prototype.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowColumn
import org.pbskids.video.redesign_prototype.MainActivity
import org.pbskids.video.redesign_prototype.repository.SingleBoxRepository

@Composable
fun HomeScreen(windowSizeWidth: MainActivity.WindowSizeClass, windowSizeHeight: MainActivity.WindowSizeClass) {
    val singleBoxRepository = SingleBoxRepository()
    val compact = windowSizeWidth == MainActivity.WindowSizeClass.COMPACT
    val medium = windowSizeWidth == MainActivity.WindowSizeClass.MEDIUM
    val large = windowSizeWidth == MainActivity.WindowSizeClass.EXPANDED
    val data = singleBoxRepository.getAllData()
    val state = rememberScrollState()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Column(modifier = Modifier.verticalScroll(state)) {
        CarouselList(data = data, windowSizeWidth, windowSizeHeight)
        if(compact){
            Box(modifier = Modifier.height(screenHeight)){
                FlowColumn() {
                    ItemColumn(data = data, windowSizeWidth, windowSizeHeight)
                }
            }


        }
        if (medium || large) {
            GridLayout(data = data, windowSizeWidth, windowSizeHeight)
        }
    }

}
