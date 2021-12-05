package dev.ajarac.adventofcode.util

import java.net.URL

object ResourceUtils {
    fun getResource(path: String): URL {
        return object {}.javaClass.getResource(path)
    }
}
