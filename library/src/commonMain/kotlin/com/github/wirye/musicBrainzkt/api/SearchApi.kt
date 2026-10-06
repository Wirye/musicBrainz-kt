package com.github.wirye.musicBrainzkt.api

import com.github.wirye.musicBrainzkt.MusicBrainzRateLimiter
import com.github.wirye.musicBrainzkt.model.Artist
import com.github.wirye.musicBrainzkt.model.Recording
import com.github.wirye.musicBrainzkt.exception.MusicBrainzException
import com.github.wirye.musicBrainzkt.model.Area
import com.github.wirye.musicBrainzkt.model.AreaSearchResponse
import com.github.wirye.musicBrainzkt.model.ArtistSearchResponse
import com.github.wirye.musicBrainzkt.model.Event
import com.github.wirye.musicBrainzkt.model.EventSearchResponse
import com.github.wirye.musicBrainzkt.model.Instrument
import com.github.wirye.musicBrainzkt.model.InstrumentSearchResponse
import com.github.wirye.musicBrainzkt.model.Label
import com.github.wirye.musicBrainzkt.model.LabelSearchResponse
import com.github.wirye.musicBrainzkt.model.Place
import com.github.wirye.musicBrainzkt.model.PlaceSearchResponse
import com.github.wirye.musicBrainzkt.model.RecordingsSearchResponse
import com.github.wirye.musicBrainzkt.model.Release
import com.github.wirye.musicBrainzkt.model.ReleaseGroup
import com.github.wirye.musicBrainzkt.model.ReleaseGroupSearchResponse
import com.github.wirye.musicBrainzkt.model.ReleaseSearchResponse
import com.github.wirye.musicBrainzkt.model.SearchResult
import com.github.wirye.musicBrainzkt.model.Series
import com.github.wirye.musicBrainzkt.model.SeriesSearchResponse
import com.github.wirye.musicBrainzkt.model.Work
import com.github.wirye.musicBrainzkt.model.WorkSearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode

class SearchApi(
    private val httpClient: HttpClient
) {
    suspend fun searchAreas(
        query: String,
        limit: Int? = null,
    ): Result<SearchResult<Area>> = runCatching {
        MusicBrainzRateLimiter.acquire()

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/area") {
                parameter("query", query)
                parameter("fmt", "json")
                limit?.let { parameter("limit", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                val response = result.body<AreaSearchResponse>()
                SearchResult(response.count, response.offset, response.areas)
            }

            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            HttpStatusCode.ServiceUnavailable -> throw MusicBrainzException.ServerErrorException(result.status.value)
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun searchArtists(
        query: String,
        limit: Int? = null,
    ): Result<SearchResult<Artist>> = runCatching {
        MusicBrainzRateLimiter.acquire()

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/artist") {
                parameter("query", query)
                parameter("fmt", "json")
                limit?.let { parameter("limit", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                val response = result.body<ArtistSearchResponse>()
                SearchResult(response.count, response.offset, response.artists)
            }

            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            HttpStatusCode.ServiceUnavailable -> throw MusicBrainzException.ServerErrorException(result.status.value)
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun searchReleaseGroups(
        query: String,
        limit: Int? = null,
    ): Result<SearchResult<ReleaseGroup>> = runCatching {
        MusicBrainzRateLimiter.acquire()

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/release-group") {
                parameter("query", query)
                parameter("fmt", "json")
                limit?.let { parameter("limit", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                val response = result.body<ReleaseGroupSearchResponse>()
                SearchResult(response.count, response.offset, response.releaseGroups)
            }

            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            HttpStatusCode.ServiceUnavailable -> throw MusicBrainzException.ServerErrorException(result.status.value)
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun searchWorks(
        query: String,
        limit: Int? = null,
    ): Result<SearchResult<Work>> = runCatching {
        MusicBrainzRateLimiter.acquire()

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/work") {
                parameter("query", query)
                parameter("fmt", "json")
                limit?.let { parameter("limit", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                val response = result.body<WorkSearchResponse>()
                SearchResult(response.count, response.offset, response.works)
            }

            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            HttpStatusCode.ServiceUnavailable -> throw MusicBrainzException.ServerErrorException(result.status.value)
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun searchLabels(
        query: String,
        limit: Int? = null,
    ): Result<SearchResult<Label>> = runCatching {
        MusicBrainzRateLimiter.acquire()

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/label") {
                parameter("query", query)
                parameter("fmt", "json")
                limit?.let { parameter("limit", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                val response = result.body<LabelSearchResponse>()
                SearchResult(response.count, response.offset, response.labels)
            }

            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            HttpStatusCode.ServiceUnavailable -> throw MusicBrainzException.ServerErrorException(result.status.value)
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun searchPlaces(
        query: String,
        limit: Int? = null,
    ): Result<SearchResult<Place>> = runCatching {
        MusicBrainzRateLimiter.acquire()

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/place") {
                parameter("query", query)
                parameter("fmt", "json")
                limit?.let { parameter("limit", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                val response = result.body<PlaceSearchResponse>()
                SearchResult(response.count, response.offset, response.places)
            }

            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            HttpStatusCode.ServiceUnavailable -> throw MusicBrainzException.ServerErrorException(result.status.value)
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun searchEvents(
        query: String,
        limit: Int? = null,
    ): Result<SearchResult<Event>> = runCatching {
        MusicBrainzRateLimiter.acquire()

        val query = buildString {
            append("event:\"${query.escapeLucene()}\"")
        }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/event") {
                parameter("query", query)
                parameter("fmt", "json")
                limit?.let { parameter("limit", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                val response = result.body<EventSearchResponse>()
                SearchResult(response.count, response.offset, response.events)
            }

            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            HttpStatusCode.ServiceUnavailable -> throw MusicBrainzException.ServerErrorException(result.status.value)
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun searchSeries(
        query: String,
        limit: Int? = null,
    ): Result<SearchResult<Series>> = runCatching {
        MusicBrainzRateLimiter.acquire()

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/series") {
                parameter("query", query)
                parameter("fmt", "json")
                limit?.let { parameter("limit", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                val response = result.body<SeriesSearchResponse>()
                SearchResult(response.count, response.offset, response.series)
            }

            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            HttpStatusCode.ServiceUnavailable -> throw MusicBrainzException.ServerErrorException(result.status.value)
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun searchInstruments(
        query: String,
        limit: Int? = null,
    ): Result<SearchResult<Instrument>> = runCatching {
        MusicBrainzRateLimiter.acquire()

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/instrument") {
                parameter("query", query)
                parameter("fmt", "json")
                limit?.let { parameter("limit", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                val response = result.body<InstrumentSearchResponse>()
                SearchResult(response.count, response.offset, response.instruments)
            }

            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            HttpStatusCode.ServiceUnavailable -> throw MusicBrainzException.ServerErrorException(result.status.value)
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun searchRecordings(
        title: String,
        artist: String? = null,
        limit: Int? = null,
    ): Result<SearchResult<Recording>> = runCatching {
        MusicBrainzRateLimiter.acquire()

        val query = buildString {
            append("recording:\"${title.escapeLucene()}\"")
            if (!artist.isNullOrBlank()) {
                append(" AND artist:\"${artist.escapeLucene()}\"")
            }
        }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/recording") {
                parameter("query", query)
                parameter("fmt", "json")
                limit?.let { parameter("limit", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                val response = result.body<RecordingsSearchResponse>()
                SearchResult(response.count, response.offset, response.recordings)
            }

            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            HttpStatusCode.ServiceUnavailable -> throw MusicBrainzException.ServerErrorException(result.status.value)
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun searchReleases(
        title: String,
        artist: String? = null,
        limit: Int? = null,
    ): Result<SearchResult<Release>> = runCatching {
        val query = buildString {
            append("release:\"${title.escapeLucene()}\"")
            if (!artist.isNullOrBlank()) {
                append(" AND artist:\"${artist.escapeLucene()}\"")
            }
        }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/release") {
                parameter("query", query)
                parameter("fmt", "json")
                limit?.let { parameter("limit", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                val response = result.body<ReleaseSearchResponse>()
                SearchResult(response.count, response.offset, response.releases)
            }

            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            HttpStatusCode.ServiceUnavailable -> throw MusicBrainzException.ServerErrorException(result.status.value)
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    private fun String.escapeLucene(): String =
        replace("\\", "\\\\").replace("\"", "\\\"")
}
