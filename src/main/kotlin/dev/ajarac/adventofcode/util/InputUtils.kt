package dev.ajarac.adventofcode.util

import java.net.URL

object InputUtils {
    fun getInput(year:Int, day: Int): URL {
        return ResourceUtils.getResource("/$year/day_${day}")
    }
}
