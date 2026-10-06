package com.github.wirye.musicBrainzkt.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*
 * Cover Art Archive: отдельный сервис (coverartarchive.org), не MusicBrainz API.
 * GET https://coverartarchive.org/release/{mbid}  → список всех картинок релиза
 * GET https://coverartarchive.org/release-group/{mbid}  → то же для release-group
 */

@Serializable
data class CoverArtListing(
    val images: List<CoverArtImage> = emptyList(),
    /** Ссылка на релиз в MusicBrainz */
    val release: String? = null,
)

@Serializable
data class CoverArtImage(
    val approved: Boolean? = null,
    val back: Boolean? = null,
    val front: Boolean? = null,
    val comment: String? = null,
    val edit: Long? = null,
    @Serializable(with = FlexibleStringSerializer::class) val id: String? = null,
    /** Оригинальный файл */
    val image: String? = null,
    val thumbnails: CoverArtThumbnails? = null,
    /** "Front", "Back", "Booklet"... */
    val types: List<String>? = null,
)

@Serializable
data class CoverArtThumbnails(
    @SerialName("250") val size250: String? = null,
    @SerialName("500") val size500: String? = null,
    @SerialName("1200") val size1200: String? = null,
    val small: String? = null,
    val large: String? = null,
)
