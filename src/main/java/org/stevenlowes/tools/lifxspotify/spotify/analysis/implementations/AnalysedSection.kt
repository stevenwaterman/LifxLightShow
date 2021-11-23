package org.stevenlowes.tools.lifxspotify.spotify.analysis.implementations

import com.wrapper.spotify.enums.Modality
import com.wrapper.spotify.model_objects.miscellaneous.AudioAnalysisSection
import org.stevenlowes.tools.lifxspotify.spotify.analysis.interfaces.AnalysedSoundContainer
import org.stevenlowes.tools.lifxspotify.spotify.analysis.interfaces.children
import java.util.*

class AnalysedSection(section: AudioAnalysisSection,
                      allBars: SortedSet<AnalysedBar>) : AnalysedSoundContainer<AnalysedBar>(during(allBars, section.measure),
                                                                                section.measure) {
    val key: Int = section.key
    val keyConfidence: Float = section.keyConfidence
    val sectionLoudness: Float = section.loudness

    val mode: Modality? = section.mode
    val modeConfidence: Float = section.modeConfidence
    val tempo: Float = section.tempo
    val tempoConfidence: Float = section.tempoConfidence
    val timeSignature: Int = section.timeSignature
    val timeSignatureConfidence: Float = section.timeSignatureConfidence

    val bars = children
    val beats = bars.children()
    val tatums = beats.children()
    val segments = tatums.children()
}