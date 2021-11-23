package org.stevenlowes.tools.lifxspotify.spotify.analysis.interfaces

import com.wrapper.spotify.model_objects.miscellaneous.AudioAnalysisMeasure
import org.stevenlowes.tools.lifxspotify.spotify.analysis.implementations.AnalysedSegment
import java.util.*

abstract class AnalysedSoundContainer<T: AnalysedSound>(val children: SortedSet<T>,
                                      measure: AudioAnalysisMeasure) : AnalysedSound(children.asSequence().map { it.importance }.average(),
                                                                                     measure) {
    final override val loudnessStart: Float = this.children.first().loudnessStart
    final override val loudnessMax: Float
    final override val loudnessMaxTime: Float
    final override val loudnessEnd: Float = this.children.last().loudnessEnd

    init {
        val loudest = this.children.asSequence().map { it.loudnessMax to it.loudnessMaxTime }.maxBy { it.first }!!
        loudnessMax = loudest.first
        loudnessMaxTime = loudest.second
    }

    /**
     * Each child is mapped to the decimal representation of how much of the parent it makes up.
     */
    val childRelevancy: Map<T, Float> = this.children.map { child ->
        val childStart = child.soundStart
        val childEnd = child.soundEnd

        val actualStart = Math.max(soundStart, childStart)
        val actualEnd = Math.min(soundEnd, childEnd)
        val actualDuration = actualEnd - actualStart

        val totalDuration = soundDuration
        val percent = actualDuration / totalDuration
        return@map child to percent
    }.toMap()

    override val pitches = proportionalAverageByMappedColumns(childRelevancy) { it.pitches }
    override val timbre = proportionalAverageByMappedColumns(childRelevancy) { it.timbre }

    companion object {
        /**
         * Map each element in childRelevancy according to the function passed in, and multiply the result by the element's relevancy, then sum them. This has the effect of doing a proportional average
         */
        private fun <T> proportionalAverageByMappedColumns(elementRelevancy: Map<T, Float>,
                                                           function: (T) -> List<Float>): List<Float> {
            return elementRelevancy //Map of each segment and how relevant it is
                    .map { (element: T, relevancy) ->
                        function(element) //Replace each segment in the list with that segment's pitches
                                .asSequence()
                                .map { timbre -> timbre * relevancy } //But multiply each pitch by that segment's relevancy
                                .withIndex()
                                .toList()
                    }.flatten().groupBy { it.index } //Rotate the list of lists, so rows become columns
                    .mapValues { (_, column) -> column.sumByDouble { it.value.toDouble() } } //Sum the pitches in each group
                    .toList().sortedBy { it.first }.map { it.second.toFloat() } //Convert to a list, sort by the index, and then drop the index leaving only the summed pitches
        }
    }
}

fun <T: AnalysedSound> Collection<AnalysedSoundContainer<T>>.children(): SortedSet<T>{
    return flatMapTo(sortedSetOf<T>()) {it.children}
}