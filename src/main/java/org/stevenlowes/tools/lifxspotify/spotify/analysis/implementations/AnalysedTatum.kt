package org.stevenlowes.tools.lifxspotify.spotify.analysis.implementations

import com.wrapper.spotify.model_objects.miscellaneous.AudioAnalysisMeasure
import org.stevenlowes.tools.lifxspotify.spotify.analysis.interfaces.AnalysedSoundContainer
import java.util.SortedSet
import javax.swing.text.Segment

class AnalysedTatum(tatum: AudioAnalysisMeasure,
                    allSegments: SortedSet<AnalysedSegment>) : AnalysedSoundContainer<AnalysedSegment>(during(allSegments, tatum), tatum){
    val segments = children
}