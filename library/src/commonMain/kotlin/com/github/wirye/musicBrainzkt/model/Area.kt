package com.github.wirye.musicBrainzkt.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Географическая область: страна, регион, город. */
@Serializable
data class Area(
    val id: String,
    val score: Int? = null,
    val name: String? = null,
    @SerialName("sort-name") val sortName: String? = null,
    val type: String? = null,
    @SerialName("type-id") val typeId: String? = null,
    @SerialName("iso-3166-1-codes") val iso31661Codes: List<String>? = null,
    @SerialName("iso-3166-2-codes") val iso31662Codes: List<String>? = null,
    @SerialName("iso-3166-3-codes") val iso31663Codes: List<String>? = null,
    @SerialName("life-span") val lifeSpan: MbLifeSpan? = null,
    val tags: List<MbTag>? = null,
    val aliases: List<MbAlias>? = null,
    val relations: List<MbRelation>? = null,
    val annotation: String? = null,
)

