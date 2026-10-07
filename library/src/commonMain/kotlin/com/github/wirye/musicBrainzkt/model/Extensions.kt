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

val List<ArtistCreditObject>.artistNames: List<ArtistSummary>
    get() = this.mapNotNull {
        if (it.name != null || it.artist.name != null) {
            ArtistSummary(it.artist.id, it.name ?: it.artist.name.orEmpty())
        } else null
    }

fun extractDeezerArtistId(relations: List<MbRelation>?): String? {
    val deezerUrl = relations
        ?.filter { it.targetType == "url" }
        ?.mapNotNull { it.url?.resource }
        ?.firstOrNull { "deezer.com/artist" in it } ?: return null

    return deezerUrl.substringAfterLast("/").substringBefore("?")
}