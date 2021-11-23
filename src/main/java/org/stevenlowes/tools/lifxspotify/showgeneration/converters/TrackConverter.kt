package org.stevenlowes.tools.lifxspotify.showgeneration.converters

import org.stevenlowes.tools.lifxspotify.utils.EventTimeline
import org.stevenlowes.tools.lifxspotify.spotify.analysis.TrackData
import java.net.InetAddress

interface TrackConverter {
    fun convert(track: TrackData, lights: Set<InetAddress>, timeline: EventTimeline): EventTimeline
}