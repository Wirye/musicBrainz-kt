package com.github.wirye.musicBrainzkt.model

import kotlinx.serialization.Serializable

/** Заметка редактора к любой сущности (только в поиске по type=annotation). */
@Serializable
data class MbAnnotation(
    /** Тип аннотированной сущности: "release", "artist"... */
    val type: String? = null,
    val score: Int? = null,
    /** MBID аннотированной сущности */
    val entity: String? = null,
    val name: String? = null,
    /** Текст в вики-разметке */
    val text: String? = null,
)

/** Временная запись о CD, которой ещё нет в базе (только в поиске по type=cdstub). */
@Serializable
data class CdStub(
    /** Не UUID, а Disc ID */
    val id: String,
    val score: Int? = null,
    val count: Int? = null,
    val title: String? = null,
    val artist: String? = null,
    val barcode: String? = null,
    val comment: String? = null,
)

/**
 * Результат поиска по type=tag. Структура этого ответа в документации не проверялась
 * (страница Search оборвалась до этого раздела), сверьте с реальным JSON.
 */
@Serializable
data class TagSearchItem(
    val name: String,
    val score: Int? = null,
    val count: Int? = null,
)
