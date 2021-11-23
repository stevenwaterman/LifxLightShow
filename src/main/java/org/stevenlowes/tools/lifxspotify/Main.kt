package org.stevenlowes.tools.lifxspotify
import org.stevenlowes.tools.lifxspotify.showgeneration.converters.ConverterSpeedChangingPulser
import org.stevenlowes.tools.lifxspotify.showgeneration.importance.ConstantImportance
import org.stevenlowes.tools.lifxspotify.showgeneration.importance.ImportanceAnalyser
import org.stevenlowes.tools.lifxspotify.spotify.Spotify
import org.stevenlowes.tools.lifxspotify.spotify.SpotifyAuth
import org.stevenlowes.tools.lifxspotify.utils.EventTimeline

fun main(args: Array<String>) {
    //SpotifyAuth.newAuth()
    SpotifyAuth.manualAuth("API_KEY_HERE")

    while(true) {
        Spotify.seekToStart()

        val importanceAnalyser = ConstantImportance(0.5)

        val track = Spotify.currentlyPlaying()
        val analysedTrack = Spotify.analyseTrack(track, importanceAnalyser)

        val timeline = EventTimeline()
        ConverterSpeedChangingPulser.convert(analysedTrack, Lights.allLights, timeline)

        val lightDelay: Long = 44

        //Increase to make song earlier
        val songDelay: Long = 550

        val start = timeline.start() + lightDelay - songDelay
        while (System.currentTimeMillis() < start) {
            Thread.sleep(1)
        }
        Spotify.play()
        //|Media.playPause()
        Thread.sleep(track.durationMs.toLong() + 1000)
    }
}
