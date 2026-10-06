package com.github.wirye.musicBrainzkt.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Лейбл звукозаписи. */
@Serializable
data class Label(
    val id: String,
    val score: Int? = null,
    val name: String? = null,
    @SerialName("sort-name") val sortName: String? = null,
    val type: String? = null,
    @SerialName("type-id") val typeId: String? = null,
    @SerialName("label-code") val labelCode: Int? = null,
    val country: String? = null,
    val area: Area? = null,
    @SerialName("life-span") val lifeSpan: MbLifeSpan? = null,
    val disambiguation: String? = null,
    val ipis: List<String>? = null,
    val isnis: List<String>? = null,
    val rating: MbRating? = null,
    val tags: List<MbTag>? = null,
    val aliases: List<MbAlias>? = null,
    val relations: List<MbRelation>? = null,
    val annotation: String? = null,
    val releases: List<Release>? = null,
)
