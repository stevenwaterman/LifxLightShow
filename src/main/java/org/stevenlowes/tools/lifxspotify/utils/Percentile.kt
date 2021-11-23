package org.stevenlowes.tools.lifxspotify.utils

class Percentile{
    companion object {
        fun <T: Comparable<T>> percentile(list: List<T>, percentile: Int): T {
            val size = list.size
            val sorted = list.sorted()
            val select = size * percentile / 100
            return sorted[select]
        }

        fun <T: Comparable<T>> median(list: List<T>): T{
            return percentile(list, 50)
        }
    }
}