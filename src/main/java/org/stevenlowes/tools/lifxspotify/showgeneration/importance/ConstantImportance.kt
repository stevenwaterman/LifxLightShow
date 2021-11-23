package org.stevenlowes.tools.lifxspotify.showgeneration.importance

import com.wrapper.spotify.model_objects.miscellaneous.AudioAnalysisSegment

class ConstantImportance(val value: Double): ImportanceAnalyser{
    override fun getImportance(segment: AudioAnalysisSegment): Double {
        return value
    }
}