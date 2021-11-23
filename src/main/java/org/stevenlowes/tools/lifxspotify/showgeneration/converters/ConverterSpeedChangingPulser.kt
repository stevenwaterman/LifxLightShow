package org.stevenlowes.tools.lifxspotify.showgeneration.converters

import org.stevenlowes.tools.lifxspotify.utils.Percentile
import org.stevenlowes.tools.lifxspotify.utils.EventTimeline
import org.stevenlowes.tools.lifxspotify.spotify.analysis.TrackData
import org.stevenlowes.tools.lifxspotify.spotify.analysis.interfaces.AnalysedSound
import java.net.InetAddress
import java.util.SortedSet

class ConverterSpeedChangingPulser{
    companion object: TrackConverter {
        override fun convert(track: TrackData, lights: Set<InetAddress>, timeline: EventTimeline): EventTimeline {
            val analysis = track.analysis
            analysis.bars.forEach { bar ->
                val segments = bar.segments
                if(canUse(segments)){
                    println("segments")
                    SoundPulser.convert(segments, lights, timeline)
                    return@forEach
                }

                val tatums = bar.tatums
                if(canUse(tatums)){
                    println("tatums")
                    SoundPulser.convert(tatums, lights, timeline)
                    return@forEach
                }

                val beats = bar.beats
                if(canUse(beats)){
                    println("beats")
                    SoundPulser.convert(beats, lights, timeline)
                    return@forEach
                }

                println("bars")
                SoundPulser.convert(sortedSetOf(bar), lights, timeline)
            }

            return timeline
        }

        private fun canUse(segments: SortedSet<out AnalysedSound>): Boolean{
            val segmentDurations = segments.map { it.soundDuration }
            val quartile = Percentile.percentile(segmentDurations, 10)
            //println(quartile)
            return quartile > 0.15
        }
    }
}