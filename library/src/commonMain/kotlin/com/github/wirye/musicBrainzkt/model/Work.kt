package com.github.wirye.musicBrainzkt.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Музыкальное произведение как сочинение, независимо от исполнения (у одного work много recordings). */
@Serializable
data class Work(
    val id: String,
    val score: Int? = null,
    val title: String? = null,
    val type: String? = null,
    @SerialName("type-id") val typeId: String? = null,
    val language: String? = null,
    val languages: List<String>? = null,
    val iswcs: List<String>? = null,
    val disambiguation: String? = null,
    val attributes: List<WorkAttribute>? = null,
    val rating: MbRating? = null,
    val genres: List<MbGenre>? = null,
    val tags: List<MbTag>? = null,
    val aliases: List<MbAlias>? = null,
    val relations: List<MbRelation>? = null,
    val annotation: String? = null,
)

@Serializable
data class WorkAttribute(
    val type: String? = null,
    @SerialName("type-id") val typeId: String? = null,
    val value: String? = null,
    @SerialName("value-id") val valueId: String? = null,
)
