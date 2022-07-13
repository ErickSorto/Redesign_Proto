package org.pbskids.video.redesign_prototype.repository

import org.pbskids.video.redesign_prototype.R
import org.pbskids.video.redesign_prototype.model.SingleBox

class SingleBoxRepository {
    fun getAllData(): List<SingleBox>{
        return listOf(
            SingleBox(1, "Video1", "This is the first video", R.drawable.arthur),
            SingleBox(2, "Video2", "This is the second video", R.drawable.curious_george),
            SingleBox(3, "Video3", "This is the third video", R.drawable.cyberchase),
            SingleBox(4, "Video4", "This is the fourth video", R.drawable.daniel_tiger),
        )
    }

}