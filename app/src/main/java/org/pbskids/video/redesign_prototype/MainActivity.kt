package org.pbskids.video.redesign_prototype

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.pbskids.video.redesign_prototype.model.SingleBox
import org.pbskids.video.redesign_prototype.repository.SingleBoxRepository
import org.pbskids.video.redesign_prototype.ui.theme.CustomItem
import org.pbskids.video.redesign_prototype.ui.theme.RedesignPrototypeTheme

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


@Composable
fun CircularList(
    data: List<SingleBox>,
    modifier: Modifier = Modifier,
    isEndless: Boolean = true
) {
    val listState = rememberLazyListState(
        if (isEndless) Int.MAX_VALUE / 2 else 0
    )

    LazyRow(
        state = listState,
        modifier = modifier
    ) {
        items(
            count = if (isEndless) Int.MAX_VALUE else data.size,
            itemContent = {
                val index = it % data.size
                CustomItem(data[index])    // item composable
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RedesignPrototypeTheme {

    }
}

