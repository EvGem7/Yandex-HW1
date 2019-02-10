package org.evgem.android.drachukeugenesapp.util

import java.util.*

fun colorToString(color: Int, alpha: Boolean = false) = if (alpha) {
    String.format("#%08X", color)
} else {
    String.format("#%06X", 0xFFFFFF and color)
}

fun getRandomColor(randomizeAlpha: Boolean = false) : Int {
    val random = Random()
    var color = random.nextInt()
    if (!randomizeAlpha) {
        color = color or 0xFF_00_00_00.toInt()
    }
    return color
}