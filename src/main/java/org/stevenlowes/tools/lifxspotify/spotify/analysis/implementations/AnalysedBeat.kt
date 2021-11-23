package org.stevenlowes.tools.lifxspotify.spotify.analysis.implementations

import com.wrapper.spotify.model_objects.miscellaneous.AudioAnalysisMeasure
import org.stevenlowes.tools.lifxspotify.spotify.analysis.interfaces.AnalysedSoundContainer
import org.stevenlowes.tools.lifxspotify.spotify.analysis.interfaces.AnalysedSound
import org.stevenlowes.tools.lifxspotify.spotify.analysis.interfaces.children
import java.util.*

class AnalysedBeat(beat: AudioAnalysisMeasure,
                   allTatums: SortedSet<AnalysedTatum>) : AnalysedSoundContainer<AnalysedTatum>(during(allTatums, beat), beat) {
    val tatums = children
    val segments = tatums.children()
}