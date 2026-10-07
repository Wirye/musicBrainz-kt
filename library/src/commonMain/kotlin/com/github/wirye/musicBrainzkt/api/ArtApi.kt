package com.github.wirye.musicBrainzkt.api

import com.github.wirye.musicBrainzkt.exception.MusicBrainzException
import com.github.wirye.musicBrainzkt.model.DeezerArtist
import com.github.wirye.musicBrainzkt.model.DeezerArtistSearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode

class ArtApi(
    private val httpClient: HttpClient
) {
    suspend fun getArtistAvatar(
        artistName: String,
        deezerId: String? = null
    ): Result<DeezerArtist?> = runCatching {
        if (!deezerId.isNullOrBlank()) {
            val result = httpClient.get("https://api.deezer.com/artist/$deezerId")

            when (result.status) {
                HttpStatusCode.OK -> {
                    result.body()
                }

                HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
                HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
                else -> throw MusicBrainzException.ServerErrorException(result.status.value)
            }
        } else {
            val response = httpClient.get("https://api.deezer.com/search/artist") {
                parameter("q", artistName)
            }

            when (response.status) {
                HttpStatusCode.OK -> {
                    response.body<DeezerArtistSearchResponse>().data.firstOrNull() ?: throw MusicBrainzException.NotFoundException()
                }

                HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
                HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(response.bodyAsText())
                else -> throw MusicBrainzException.ServerErrorException(response.status.value)
            }
        }
    }

    private fun String?.isNullOrBlank(): Boolean = this == null || this.isBlank()
}