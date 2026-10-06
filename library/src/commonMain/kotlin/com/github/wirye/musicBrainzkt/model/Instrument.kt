package com.github.wirye.musicBrainzkt.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Музыкальный инструмент. */
@Serializable
data class Instrument(
    val id: String,
    val score: Int? = null,
    val name: String? = null,
    val type: String? = null,
    @SerialName("type-id") val typeId: String? = null,
    val description: String? = null,
    val disambiguation: String? = null,
    val tags: List<MbTag>? = null,
    val aliases: List<MbAlias>? = null,
    val relations: List<MbRelation>? = null,
    val annotation: String? = null,
)
