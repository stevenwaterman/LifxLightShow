package org.stevenlowes.tools.lifxspotify.spotify.analysis.interfaces

import com.wrapper.spotify.model_objects.miscellaneous.AudioAnalysisMeasure
import java.util.*

abstract class AnalysedSound(val importance: Double, measure: AudioAnalysisMeasure) : Comparable<AnalysedSound> {
    val soundStart = measure.start
    val soundDuration = measure.duration
    val soundEnd = measure.start + measure.duration
    val soundConfidence = measure.confidence

    abstract val loudnessStart: Float
    abstract val loudnessEnd: Float
    abstract val loudnessMax: Float
    abstract val loudnessMaxTime: Float

    abstract val pitches: List<Float>
    abstract val timbre: List<Float>

    override fun compareTo(other: AnalysedSound): Int {
        return soundStart.compareTo(other.soundStart)
    }

    companion object {
        fun <T : AnalysedSound> during(all: Iterable<T>, filterStart: Float, filterEnd: Float): SortedSet<T> {
            return all.filter { it.soundStart < filterEnd && it.soundEnd >= filterStart }.toSortedSet()
        }

        fun <T : AnalysedSound> during(all: Iterable<T>, measure: AudioAnalysisMeasure): SortedSet<T> {
            return during(all,
                          measure.start,
                          measure.start + measure.duration)
        }
    }

    val averagePitch
        get() = pitches.asSequence().withIndex().map { (index, amount) ->
            (index + 1) * amount
        }.average() / 12
}