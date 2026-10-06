package com.github.wirye.musicBrainzkt.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Конкретное издание альбома: CD 1991 года в Японии, винил, цифровая версия. */
@Serializable
data class Release(
    val id: String,
    val score: Int? = null,
    val count: Int? = null,
    val title: String? = null,
    val disambiguation: String? = null,
    /** "Official", "Promotion", "Bootleg"... */
    val status: String? = null,
    @SerialName("status-id") val statusId: String? = null,
    val quality: String? = null,
    val packaging: String? = null,
    @SerialName("packaging-id") val packagingId: String? = null,
    @SerialName("text-representation") val textRepresentation: TextRepresentation? = null,
    /** "2014-09-01", "2014-09" или "2014" */
    val date: String? = null,
    val country: String? = null,
    val barcode: String? = null,
    val asin: String? = null,
    @SerialName("release-events") val releaseEvents: List<ReleaseEvent>? = null,
    @SerialName("label-info") val labelInfo: List<LabelInfo>? = null,
    @SerialName("track-count") val trackCount: Int? = null,
    val media: List<MediaObject>? = null,
    @SerialName("artist-credit") val artistCredit: List<ArtistCreditObject>? = null,
    @SerialName("artist-credit-id") val artistCreditId: String? = null,
    @SerialName("release-group") val releaseGroup: ReleaseGroup? = null,
    @SerialName("cover-art-archive") val coverArtArchive: CoverArtArchiveInfo? = null,
    val genres: List<MbGenre>? = null,
    val tags: List<MbTag>? = null,
    val aliases: List<MbAlias>? = null,
    val relations: List<MbRelation>? = null,
    val annotation: String? = null,
)

@Serializable
data class TextRepresentation(
    /** ISO 639-3, например "eng" */
    val language: String? = null,
    /** ISO 15924, например "Latn" */
    val script: String? = null,
)

@Serializable
data class ReleaseEvent(
    val date: String? = null,
    val area: Area? = null,
)

@Serializable
data class LabelInfo(
    @SerialName("catalog-number") val catalogNumber: String? = null,
    val label: Label? = null,
)

/** Диск (медиум) внутри релиза. */
@Serializable
data class MediaObject(
    val position: Int? = null,
    val title: String? = null,
    val format: String? = null,
    @SerialName("format-id") val formatId: String? = null,
    @SerialName("track-count") val trackCount: Int? = null,
    @SerialName("track-offset") val trackOffset: Int? = null,
    /** Ключ "track": так приходит в ответах поиска (обычно один найденный трек) */
    val track: List<MediaTrack>? = null,
    /** Ключ "tracks": так приходит в lookup релиза (полный список) */
    val tracks: List<MediaTrack>? = null,
    val discs: List<MbDisc>? = null,
) {
    /** Треки независимо от того, пришли они из поиска или из lookup. */
    val allTracks: List<MediaTrack>? get() = tracks ?: track
}

@Serializable
data class MediaTrack(
    val id: String? = null,
    val position: Int? = null,
    /** Строка: бывает "A4" или "12" */
    val number: String? = null,
    val title: String? = null,
    /** Миллисекунды */
    val length: Long? = null,
    @SerialName("artist-credit") val artistCredit: List<ArtistCreditObject>? = null,
    /** Только в lookup с inc=recordings */
    val recording: Recording? = null,
)

@Serializable
data class MbDisc(
    val id: String? = null,
    val sectors: Int? = null,
    @SerialName("offset-count") val offsetCount: Int? = null,
    val offsets: List<Int>? = null,
)

/** Сводка по обложкам релиза (поле cover-art-archive в lookup релиза). */
@Serializable
data class CoverArtArchiveInfo(
    val artwork: Boolean? = null,
    val count: Int? = null,
    val front: Boolean? = null,
    val back: Boolean? = null,
    val darkened: Boolean? = null,
)
