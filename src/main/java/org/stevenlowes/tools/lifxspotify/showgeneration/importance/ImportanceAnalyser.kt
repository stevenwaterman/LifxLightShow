package org.stevenlowes.tools.lifxspotify.showgeneration.importance

import com.wrapper.spotify.model_objects.miscellaneous.AudioAnalysisSegment

interface ImportanceAnalyser {
    fun getImportance(segment: AudioAnalysisSegment): Double
}
