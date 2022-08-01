package org.pbskids.video.redesign_prototype

import android.accounts.AccountManager.get
import android.app.Activity
import android.app.Fragment
import android.view.ViewConfiguration.get
import androidx.appcompat.view.ActionBarPolicy.get
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.toComposeRect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewTreeLifecycleOwner.get
import androidx.lifecycle.ViewTreeViewModelStoreOwner.get
import androidx.window.layout.WindowMetricsCalculator
import java.lang.reflect.Array.get

enum class WindowSize {
    SMALL,
    MEDIUM,
    LARGE
}

@Composable
fun Activity.rememberWindowSizeClass():WindowSize {
    val windowSize = rememberWindowSize()
    val windowDpSize = with(LocalDensity.current){
        windowSize.toDpSize()
    }

    return getWindowSizeClass(windowSizeDpSize = windowDpSize)

}

@Composable
fun Activity.rememberWindowSize(): Size {
    val configuration = LocalConfiguration.current

    val windowMetric = remember(configuration){
        WindowMetricsCalculator.getOrCreate().computeMaximumWindowMetrics(this)
    }

    return windowMetric.bounds.toComposeRect().size
}

@Composable
fun getWindowSizeClass(windowSizeDpSize: DpSize) = when {

    windowSizeDpSize.width < 0.dp -> {
        throw IllegalArgumentException("Window width must be smaller than 0")
    }
    windowSizeDpSize.width < 600.dp -> {
        WindowSize.SMALL
    }
    windowSizeDpSize.width < 840.dp -> {
        WindowSize.MEDIUM
    }
    else -> {
        WindowSize.LARGE
    }

}