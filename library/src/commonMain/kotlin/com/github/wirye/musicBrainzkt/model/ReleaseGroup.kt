package com.github.wirye.musicBrainzkt.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** «Альбом вообще»: объединяет все издания (releases) одного альбома/сингла/EP. */
@Serializable
data class ReleaseGroup(
    val id: String,
    val score: Int? = null,
    /** В поиске: число релизов в группе */
    val count: Int? = null,
    val title: String? = null,
    val disambiguation: String? = null,
    /** Устаревшее поле поиска, заменено primary/secondary-type */
    val type: String? = null,
    @SerialName("primary-type") val primaryType: String? = null,
    @SerialName("primary-type-id") val primaryTypeId: String? = null,
    /** "Compilation", "Live", "Soundtrack"... */
    @SerialName("secondary-types") val secondaryTypes: List<String>? = null,
    @SerialName("secondary-type-ids") val secondaryTypeIds: List<String>? = null,
    @SerialName("first-release-date") val firstReleaseDate: String? = null,
    @SerialName("artist-credit") val artistCredit: List<ArtistCreditObject>? = null,
    @SerialName("artist-credit-id") val artistCreditId: String? = null,
    val releases: List<Release>? = null,
    val rating: MbRating? = null,
    val tags: List<MbTag>? = null,
    val aliases: List<MbAlias>? = null,
    val relations: List<MbRelation>? = null,
    val annotation: String? = null,
)
