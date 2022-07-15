package org.pbskids.video.redesign_prototype.repository

import org.pbskids.video.redesign_prototype.R
import org.pbskids.video.redesign_prototype.model.SingleBox

class SingleBoxRepository {
    fun getAllData(): List<SingleBox>{
        return listOf(
            SingleBox(1, "Video1", "This is the first video", R.drawable.image1),
            SingleBox(2, "Video2", "This is the second video", R.drawable.image10),
            SingleBox(3, "Video3", "This is the third video", R.drawable.image4),
            SingleBox(4, "Video4", "This is the fourth video", R.drawable.image5),
        )
    }

}