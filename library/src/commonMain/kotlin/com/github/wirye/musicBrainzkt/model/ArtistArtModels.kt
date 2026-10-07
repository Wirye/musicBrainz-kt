package com.github.wirye.musicBrainzkt.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeezerArtist(
    val id: Long,
    val name: String,
    @SerialName("picture_small") val pictureSmall: String? = null,
    @SerialName("picture_medium") val pictureMedium: String? = null,
    @SerialName("picture_big") val pictureBig: String? = null,
    @SerialName("picture_xl") val pictureXl: String? = null
)

@Serializable
data class DeezerArtistSearchResponse(
    val data: List<DeezerArtist>
)
