package org.stevenlowes.tools.lifxspotify.spotify

import com.wrapper.spotify.SpotifyApi
import com.wrapper.spotify.exceptions.detailed.ForbiddenException
import com.wrapper.spotify.model_objects.specification.AudioFeatures
import com.wrapper.spotify.model_objects.specification.Track
import org.stevenlowes.tools.lifxspotify.showgeneration.importance.ImportanceAnalyser
import org.stevenlowes.tools.lifxspotify.spotify.analysis.TrackAnalysis
import org.stevenlowes.tools.lifxspotify.spotify.analysis.TrackData

class Spotify {
    companion object {
        val api: SpotifyApi = SpotifyApi.builder()
                .setClientId(SpotifyAuth.clientId)
                .setClientSecret(SpotifyAuth.clientSecret)
                .setRedirectUri(SpotifyAuth.redirectUri).build()

        fun seekToStart() {
            try {
                api.pauseUsersPlayback().build().execute()
            }
            catch(ignore: ForbiddenException){}

            api.seekToPositionInCurrentlyPlayingTrack(0).build().execute()
        }

        fun play(){
            api.startResumeUsersPlayback().build().execute()
        }

        fun currentlyPlaying(): Track {
            val currentlyPlaying = api.usersCurrentlyPlayingTrack.build().execute()!!
            return currentlyPlaying.item
        }

        fun getTrack(id: String): Track {
            return Spotify.api.getTrack(id).build().execute()!!
        }

        fun analyseTrack(track: Track, importanceAnalyser: ImportanceAnalyser): TrackData {
            val featuresFuture = Spotify.api.getAudioFeaturesForTrack(track.id).build().executeAsync<AudioFeatures>()
            val analysisFuture = Spotify.api.getAudioAnalysisForTrack(track.id).build().executeAsync<com.wrapper.spotify.model_objects.miscellaneous.AudioAnalysis>()

            val features = featuresFuture.get()
            val analysis = analysisFuture.get()
            val moreAnalysis = TrackAnalysis(importanceAnalyser, analysis)

            return TrackData(track, features, moreAnalysis)
        }
    }
}