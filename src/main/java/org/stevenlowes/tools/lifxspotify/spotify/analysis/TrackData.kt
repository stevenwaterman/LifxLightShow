package org.stevenlowes.tools.lifxspotify.spotify.analysis

import com.wrapper.spotify.model_objects.specification.AudioFeatures
import com.wrapper.spotify.model_objects.specification.Track

data class TrackData(val track: Track,
                     val features: AudioFeatures,
                     val analysis: TrackAnalysis){
    val id = track.id
    val name = track.name
    val artist = track.artists.joinToString(", ") { it.name }
    val album = track.album.name

    override fun toString(): String {
        return "TrackData(name='$name', artist='$artist')"
    }
}