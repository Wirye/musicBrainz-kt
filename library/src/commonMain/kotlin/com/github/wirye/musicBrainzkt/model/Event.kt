package com.github.wirye.musicBrainzkt.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Событие: концерт, фестиваль. */
@Serializable
data class Event(
    val id: String,
    val score: Int? = null,
    val name: String? = null,
    val type: String? = null,
    @SerialName("type-id") val typeId: String? = null,
    val time: String? = null,
    val cancelled: Boolean? = null,
    val setlist: String? = null,
    @SerialName("life-span") val lifeSpan: MbLifeSpan? = null,
    val disambiguation: String? = null,
    val rating: MbRating? = null,
    val tags: List<MbTag>? = null,
    val aliases: List<MbAlias>? = null,
    val relations: List<MbRelation>? = null,
    val annotation: String? = null,
)
