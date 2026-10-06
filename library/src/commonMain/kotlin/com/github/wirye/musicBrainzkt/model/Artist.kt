package com.github.wirye.musicBrainzkt.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Исполнитель: человек, группа, оркестр, персонаж. */
@Serializable
data class Artist(
    val id: String,
    val score: Int? = null,
    val name: String? = null,
    @SerialName("sort-name") val sortName: String? = null,
    val type: String? = null,
    @SerialName("type-id") val typeId: String? = null,
    val gender: String? = null,
    @SerialName("gender-id") val genderId: String? = null,
    val country: String? = null,
    val area: Area? = null,
    @SerialName("begin-area") val beginArea: Area? = null,
    @SerialName("end-area") val endArea: Area? = null,
    @SerialName("life-span") val lifeSpan: MbLifeSpan? = null,
    val disambiguation: String? = null,
    val isnis: List<String>? = null,
    val ipis: List<String>? = null,
    val rating: MbRating? = null,
    val tags: List<MbTag>? = null,
    val aliases: List<MbAlias>? = null,
    val relations: List<MbRelation>? = null,
    val annotation: String? = null,
    @SerialName("release-groups") val releaseGroups: List<ReleaseGroup>? = null,
    val releases: List<Release>? = null,
    val recordings: List<Recording>? = null,
    val works: List<Work>? = null,
)

/** Один элемент "artist credit": как исполнитель указан на записи/релизе. */
@Serializable
data class ArtistCreditObject(
    /** Имя в том виде, как оно указано на этой записи (может отличаться от artist.name) */
    val name: String? = null,
    /** Связка с следующим исполнителем: " feat. ", " & " */
    val joinphrase: String? = null,
    val artist: Artist,
)
