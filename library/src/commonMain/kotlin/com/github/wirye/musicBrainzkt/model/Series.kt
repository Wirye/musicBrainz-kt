package com.github.wirye.musicBrainzkt.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Серия релизов, записей или произведений (например, Now That's What I Call Music!). */
@Serializable
data class Series(
    val id: String,
    val score: Int? = null,
    val name: String? = null,
    val type: String? = null,
    @SerialName("type-id") val typeId: String? = null,
    val disambiguation: String? = null,
    val tags: List<MbTag>? = null,
    val aliases: List<MbAlias>? = null,
    val relations: List<MbRelation>? = null,
    val annotation: String? = null,
)
