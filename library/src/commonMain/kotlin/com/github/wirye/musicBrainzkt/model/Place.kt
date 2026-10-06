package com.github.wirye.musicBrainzkt.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Место: студия, концертный зал, стадион. */
@Serializable
data class Place(
    val id: String,
    val score: Int? = null,
    val name: String? = null,
    val type: String? = null,
    @SerialName("type-id") val typeId: String? = null,
    val address: String? = null,
    val coordinates: MbCoordinates? = null,
    val area: Area? = null,
    @SerialName("life-span") val lifeSpan: MbLifeSpan? = null,
    val disambiguation: String? = null,
    val rating: MbRating? = null,
    val tags: List<MbTag>? = null,
    val aliases: List<MbAlias>? = null,
    val relations: List<MbRelation>? = null,
    val annotation: String? = null,
)
