package com.github.wirye.musicBrainzkt.model

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/** "Artist A feat. Artist B" */
val Recording.artistDisplayName: String?
    get() = artistCredit?.joinToString("") {
        (it.name ?: it.artist.name.orEmpty()) + it.joinphrase.orEmpty()
    }

val Recording.duration: Duration?
    get() = length?.milliseconds

val MediaTrack.duration: Duration?
    get() = length?.milliseconds

/** Адрес лицевой обложки; запросов не требует, но у части релизов обложки нет (404). */
val ReleaseGroup.coverArtUrl: String
    get() = "https://coverartarchive.org/release-group/$id/front"

val Release.coverArtUrl: String
    get() = "https://coverartarchive.org/release/$id/front"
