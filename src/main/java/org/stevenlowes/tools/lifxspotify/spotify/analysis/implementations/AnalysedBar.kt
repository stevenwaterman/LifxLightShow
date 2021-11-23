package org.stevenlowes.tools.lifxspotify.spotify.analysis.implementations

import com.wrapper.spotify.model_objects.miscellaneous.AudioAnalysisMeasure
import org.stevenlowes.tools.lifxspotify.spotify.analysis.interfaces.AnalysedSoundContainer
import org.stevenlowes.tools.lifxspotify.spotify.analysis.interfaces.children
import java.util.*

class AnalysedBar(bar: AudioAnalysisMeasure,
                  allBeats: SortedSet<AnalysedBeat>) : AnalysedSoundContainer<AnalysedBeat>(during(allBeats, bar), bar) {
    val beats = children
    val tatums = beats.children()
    val segments = tatums.children()
}