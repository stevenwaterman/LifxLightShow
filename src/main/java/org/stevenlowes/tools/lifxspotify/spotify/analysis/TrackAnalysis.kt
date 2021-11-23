package org.stevenlowes.tools.lifxspotify.spotify.analysis

import com.wrapper.spotify.model_objects.miscellaneous.AudioAnalysis
import org.stevenlowes.tools.lifxspotify.spotify.analysis.implementations.*
import org.stevenlowes.tools.lifxspotify.showgeneration.importance.ImportanceAnalyser
import java.util.*

class TrackAnalysis(importanceAnalyser: ImportanceAnalyser, simpleAnalysis: AudioAnalysis){
    val sections: SortedSet<AnalysedSection>
    val bars: SortedSet<AnalysedBar>
    val beats: SortedSet<AnalysedBeat>
    val tatums: SortedSet<AnalysedTatum>
    val segments: SortedSet<AnalysedSegment>

    init {
        val simpleSections = simpleAnalysis.sections
        val simpleBars = simpleAnalysis.bars
        val simpleBeats = simpleAnalysis.beats
        val simpleTatums = simpleAnalysis.tatums
        val simpleSegments = simpleAnalysis.segments

        segments = simpleSegments.withIndex().map { (index, segment) ->
            val loudnessEnd = if (index + 1 < simpleSegments.size) {
                simpleSegments[index + 1].loudnessStart
            }
            else {
                -60f
            }

            val importance = importanceAnalyser.getImportance(segment)

            return@map AnalysedSegment(segment, importance, loudnessEnd)
        }.toSortedSet()

        tatums = simpleTatums.map {
            AnalysedTatum(it, segments)
        }.toSortedSet()

        beats = simpleBeats.map {
            AnalysedBeat(it, tatums)
        }.toSortedSet()

        bars = simpleBars.map {
            AnalysedBar(it, beats)
        }.toSortedSet()

        sections = simpleSections.map {
            AnalysedSection(it, bars)
        }.toSortedSet()
    }
}

