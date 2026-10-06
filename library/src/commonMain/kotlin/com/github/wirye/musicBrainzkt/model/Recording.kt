package com.github.wirye.musicBrainzkt.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Конкретная аудиозапись (студийная, концертная, ремикс). */
@Serializable
data class Recording(
    val id: String,
    val score: Int? = null,
    val title: String? = null,
    /** Миллисекунды */
    val length: Long? = null,
    val video: Boolean? = null,
    val disambiguation: String? = null,
    @SerialName("artist-credit") val artistCredit: List<ArtistCreditObject>? = null,
    @SerialName("artist-credit-id") val artistCreditId: String? = null,
    @SerialName("first-release-date") val firstReleaseDate: String? = null,
    val releases: List<Release>? = null,
    val isrcs: List<String>? = null,
    val rating: MbRating? = null,
    val genres: List<MbGenre>? = null,
    val tags: List<MbTag>? = null,
    val aliases: List<MbAlias>? = null,
    val relations: List<MbRelation>? = null,
    val annotation: String? = null,
)
