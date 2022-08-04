package org.pbskids.video.redesign_prototype.repository

import org.pbskids.video.redesign_prototype.R
import org.pbskids.video.redesign_prototype.model.SingleBox

class SingleBoxRepository {
    fun getAllData(): List<SingleBox> {
        return listOf(
            SingleBox(9, "Video1", "This is the ninth video", R.drawable.queen_sara_explains_voting, true, true),
            SingleBox(10, "Video2", "This is the tenth video", R.drawable.sleepingsomewherenew, false, true),
            SingleBox(1, "Video3", "This is the first video", R.drawable.dtig101, false, false),
            SingleBox(1, "Video4", "This is the first video", R.drawable.dtig102, true, false),
            SingleBox(2, "Video5", "This is the second video", R.drawable.dtig103, false, false),
            SingleBox(3, "Video6", "This is the third video", R.drawable.dtig104, false, false),
            SingleBox(4, "Video7", "This is the fourth video", R.drawable.dtig105, false, false),
            SingleBox(5, "Video8", "This is the fifth video", R.drawable.dtig106, false, false),
            SingleBox(6, "Video9", "This is the sixth video", R.drawable.dtig107, true, true),
            SingleBox(7, "Video10", "This is the seventh video", R.drawable.dtig108, true, true),
            SingleBox(8, "Video11", "This is the eighth video", R.drawable.meet_the_new_baby_movie, false, true),
            SingleBox(9, "Video12", "This is the ninth video", R.drawable.wont_you_be_neighbor_movie, true, false),
            SingleBox(10, "Video13", "This is the tenth video", R.drawable.wont_you_sing_along_with_me_movie, false, true),
        )
    }

}

