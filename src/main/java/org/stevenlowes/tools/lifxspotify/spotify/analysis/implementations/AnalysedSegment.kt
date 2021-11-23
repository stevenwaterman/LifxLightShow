package org.stevenlowes.tools.lifxspotify.spotify.analysis.implementations

import com.wrapper.spotify.model_objects.miscellaneous.AudioAnalysisSegment
import org.stevenlowes.tools.lifxspotify.spotify.analysis.interfaces.AnalysedSound

class AnalysedSegment(segment: AudioAnalysisSegment, importance: Double, override val loudnessEnd: Float): AnalysedSound(importance, segment.measure){
    override val loudnessStart: Float = segment.loudnessStart
    override val loudnessMax: Float = segment.loudnessMax
    override val loudnessMaxTime: Float = segment.loudnessMaxTime
    override val pitches: List<Float> = segment.pitches.toList()
    override val timbre: List<Float> = segment.timbre.toList()
}