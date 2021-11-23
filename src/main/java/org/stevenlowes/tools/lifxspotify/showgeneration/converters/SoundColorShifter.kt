package org.stevenlowes.tools.lifxspotify.showgeneration.converters

import org.stevenlowes.tools.lifxcontroller.commands.request.light.RequestSetColor
import org.stevenlowes.tools.lifxcontroller.values.Color
import org.stevenlowes.tools.lifxcontroller.values.Hue
import org.stevenlowes.tools.lifxcontroller.values.Level
import org.stevenlowes.tools.lifxspotify.lifx.Event
import org.stevenlowes.tools.lifxspotify.utils.EventTimeline
import org.stevenlowes.tools.lifxspotify.spotify.analysis.interfaces.AnalysedSound
import java.net.InetAddress
import java.util.*

class SoundColorShifter {
    companion object {
        fun convert(sounds: SortedSet<out AnalysedSound>,
                    lights: Set<InetAddress>,
                    timeline: EventTimeline): EventTimeline {
            val pitches = sounds.map { sound -> sound.averagePitch }
            val minPitch = pitches.min()!!
            val maxPitch = pitches.max()!!
            val pitchRange = maxPitch - minPitch
            val factor = 1 / pitchRange

            for (sound in sounds) {
                val start = (sound.soundStart * 1000).toLong()
                val duration = (sound.soundDuration * 1000).toLong()

                val pitch = sound.averagePitch
                val adjPitch = (pitch - minPitch) * factor
                val hue = Hue((adjPitch * 330).toInt())
                val brightness = Level(Math.min(1.0,
                                                Math.pow((Math.min(0.0, sound.loudnessMax.toDouble()) + 60) / 60,
                                                         5.0) * 1.17))
                val color = Color(hue = hue, brightness = brightness)
                val event = Event(RequestSetColor(color = color,
                                                                                         duration = duration),
                                                                         lights,
                                                                         start)
                timeline.add(event)
            }

            return timeline
        }
    }
}