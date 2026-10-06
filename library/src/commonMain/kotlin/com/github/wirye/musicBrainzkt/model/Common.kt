package com.github.wirye.musicBrainzkt.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Результат поиска, который получает пользователь библиотеки. */
data class SearchResult<T>(
    /** Общее число найденных совпадений (не длина [items]) */
    val total: Int,
    val offset: Int,
    val items: List<T>,
)

@Serializable
data class MbAlias(
    val name: String? = null,
    @SerialName("sort-name") val sortName: String? = null,
    val locale: String? = null,
    val type: String? = null,
    @SerialName("type-id") val typeId: String? = null,
    val primary: Boolean? = null,
    // lookup отдаёт begin/end/ended, search отдаёт begin-date/end-date
    val begin: String? = null,
    val end: String? = null,
    val ended: Boolean? = null,
    @SerialName("begin-date") val beginDate: String? = null,
    @SerialName("end-date") val endDate: String? = null,
)

@Serializable
data class MbTag(
    val name: String,
    val count: Int? = null,
)

@Serializable
data class MbGenre(
    val id: String? = null,
    val name: String,
    val count: Int? = null,
    val disambiguation: String? = null,
)

@Serializable
data class MbRating(
    @SerialName("votes-count") val votesCount: Int? = null,
    val value: Double? = null,
)

@Serializable
data class MbLifeSpan(
    /** "1969" или "1980-01-22": даты бывают неполными, поэтому String */
    val begin: String? = null,
    val end: String? = null,
    val ended: Boolean? = null,
)

@Serializable
data class MbCoordinates(
    // в поиске координаты приходят строками ("51.9414"), в lookup, скорее всего, числами
    @Serializable(with = FlexibleDoubleSerializer::class) val latitude: Double? = null,
    @Serializable(with = FlexibleDoubleSerializer::class) val longitude: Double? = null,
)

/** Ссылка на внешний ресурс (Spotify, Wikipedia, Bandcamp...). Приходит через inc=url-rels. */
@Serializable
data class MbUrl(
    val id: String? = null,
    val score: Int? = null,
    val resource: String? = null,
    val relations: List<MbRelation>? = null,
)

/**
 * Связь между сущностями (inc=*-rels). Заполнено ровно одно из полей-сущностей,
 * ключ совпадает с [targetType]: artist, url, recording, release и т.д.
 */
@Serializable
data class MbRelation(
    val type: String? = null,
    @SerialName("type-id") val typeId: String? = null,
    @SerialName("target-type") val targetType: String? = null,
    val target: String? = null,
    @SerialName("target-credit") val targetCredit: String? = null,
    @SerialName("source-credit") val sourceCredit: String? = null,
    val direction: String? = null,
    val begin: String? = null,
    val end: String? = null,
    val ended: Boolean? = null,
    val attributes: List<String>? = null,
    @SerialName("attribute-ids") val attributeIds: Map<String, String>? = null,
    @SerialName("attribute-values") val attributeValues: Map<String, String>? = null,
    @SerialName("attribute-credits") val attributeCredits: Map<String, String>? = null,
    val url: MbUrl? = null,
    val artist: Artist? = null,
    val area: Area? = null,
    val event: Event? = null,
    val instrument: Instrument? = null,
    val label: Label? = null,
    val place: Place? = null,
    val recording: Recording? = null,
    val release: Release? = null,
    @SerialName("release-group") val releaseGroup: ReleaseGroup? = null,
    val series: Series? = null,
    val work: Work? = null,
)
