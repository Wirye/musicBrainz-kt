package com.github.wirye.musicBrainzkt.api

import com.github.wirye.musicBrainzkt.MusicBrainzRateLimiter
import com.github.wirye.musicBrainzkt.exception.MusicBrainzException
import com.github.wirye.musicBrainzkt.model.Artist
import com.github.wirye.musicBrainzkt.model.ArtistBrowseResponse
import com.github.wirye.musicBrainzkt.model.ArtistInclude
import com.github.wirye.musicBrainzkt.model.Event
import com.github.wirye.musicBrainzkt.model.EventBrowseResponse
import com.github.wirye.musicBrainzkt.model.EventInclude
import com.github.wirye.musicBrainzkt.model.Label
import com.github.wirye.musicBrainzkt.model.LabelBrowseResponse
import com.github.wirye.musicBrainzkt.model.LabelInclude
import com.github.wirye.musicBrainzkt.model.Place
import com.github.wirye.musicBrainzkt.model.PlaceBrowseResponse
import com.github.wirye.musicBrainzkt.model.PlaceInclude
import com.github.wirye.musicBrainzkt.model.Recording
import com.github.wirye.musicBrainzkt.model.RecordingBrowseResponse
import com.github.wirye.musicBrainzkt.model.RecordingInclude
import com.github.wirye.musicBrainzkt.model.Release
import com.github.wirye.musicBrainzkt.model.ReleaseBrowseResponse
import com.github.wirye.musicBrainzkt.model.ReleaseGroup
import com.github.wirye.musicBrainzkt.model.ReleaseGroupBrowseResponse
import com.github.wirye.musicBrainzkt.model.ReleaseGroupInclude
import com.github.wirye.musicBrainzkt.model.ReleaseInclude
import com.github.wirye.musicBrainzkt.model.SearchResult
import com.github.wirye.musicBrainzkt.model.Work
import com.github.wirye.musicBrainzkt.model.WorkBrowseResponse
import com.github.wirye.musicBrainzkt.model.WorkInclude
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode

class BrowseApi(
    private val httpClient: HttpClient
) {
    suspend fun getReleasesByReleaseGroup(
        releaseGroupMbid: String,
        include: Set<ReleaseInclude> = emptySet(),
        /** Фильтр по типу группы: "album", "ep", "album|ep" */
        type: String? = null,
        /** Фильтр по статусу: "official", "promotion", "bootleg" */
        status: String? = null,
        /** 1..100, по умолчанию на сервере 25 */
        limit: Int? = null,
        offset: Int? = null,
    ): Result<SearchResult<Release>> = runCatching {
        require(limit == null || limit in 1..100) { "limit должен быть от 1 до 100" }
        require(offset == null || offset >= 0) { "offset не может быть отрицательным" }
        require(ReleaseInclude.ISRCS !in include || ReleaseInclude.RECORDINGS in include) {
            "ISRCS работает только вместе с RECORDINGS"
        }

        val include = include.filter {
            it != ReleaseInclude.COLLECTIONS
        }

        MusicBrainzRateLimiter.acquire()

        val inc = include.joinToString(" ") { it.value }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/release") {
                parameter("fmt", "json")
                parameter("release-group", releaseGroupMbid)
                if (inc.isNotEmpty()) parameter("inc", inc)
                type?.let { parameter("type", it) }
                status?.let { parameter("status", it) }
                limit?.let { parameter("limit", it) }
                offset?.let { parameter("offset", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                result.body<ReleaseBrowseResponse>().toSearchResult()
            }

            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getReleasesByArtist(
        artistMbid: String,
        include: Set<ReleaseInclude> = emptySet(),
        type: String? = null,
        status: String? = null,
        limit: Int? = null,
        offset: Int? = null,
    ): Result<SearchResult<Release>> = runCatching {
        require(limit == null || limit in 1..100) { "limit должен быть от 1 до 100" }
        require(offset == null || offset >= 0) { "offset не может быть отрицательным" }
        require(ReleaseInclude.ISRCS !in include || ReleaseInclude.RECORDINGS in include) {
            "ISRCS работает только вместе с RECORDINGS"
        }

        val include = include.filter { it != ReleaseInclude.COLLECTIONS }
        MusicBrainzRateLimiter.acquire()
        val inc = include.joinToString(" ") { it.value }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/release") {
                parameter("fmt", "json")
                parameter("artist", artistMbid)
                if (inc.isNotEmpty()) parameter("inc", inc)
                type?.let { parameter("type", it) }
                status?.let { parameter("status", it) }
                limit?.let { parameter("limit", it) }
                offset?.let { parameter("offset", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> result.body<ReleaseBrowseResponse>().toSearchResult()
            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getReleaseGroupsByArtist(
        artistMbid: String,
        include: Set<ReleaseGroupInclude> = emptySet(),
        type: String? = null,
        limit: Int? = null,
        offset: Int? = null,
    ): Result<SearchResult<ReleaseGroup>> = runCatching {
        require(limit == null || limit in 1..100) { "limit должен быть от 1 до 100" }
        require(offset == null || offset >= 0) { "offset не может быть отрицательным" }

        val include = include.filter { it != ReleaseGroupInclude.RELEASES && it != ReleaseGroupInclude.MEDIA && it != ReleaseGroupInclude.DISC_IDS }

        MusicBrainzRateLimiter.acquire()
        val inc = include.joinToString(" ") { it.value }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/release-group") {
                parameter("fmt", "json")
                parameter("artist", artistMbid)
                if (inc.isNotEmpty()) parameter("inc", inc)
                type?.let { parameter("type", it) }
                limit?.let { parameter("limit", it) }
                offset?.let { parameter("offset", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> result.body<ReleaseGroupBrowseResponse>().toSearchResult()
            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getRecordingsByArtist(
        artistMbid: String,
        include: Set<RecordingInclude> = emptySet(),
        limit: Int? = null,
        offset: Int? = null,
    ): Result<SearchResult<Recording>> = runCatching {
        require(limit == null || limit in 1..100) { "limit должен быть от 1 до 100" }
        require(offset == null || offset >= 0) { "offset не может быть отрицательным" }

        val include =
            include.filter { it != RecordingInclude.RELEASES && it != RecordingInclude.RELEASE_GROUPS && it != RecordingInclude.MEDIA && it != RecordingInclude.DISC_IDS && it != RecordingInclude.ISRCS }

        MusicBrainzRateLimiter.acquire()
        val inc = include.joinToString(" ") { it.value }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/recording") {
                parameter("fmt", "json")
                parameter("artist", artistMbid)
                if (inc.isNotEmpty()) parameter("inc", inc)
                limit?.let { parameter("limit", it) }
                offset?.let { parameter("offset", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> result.body<RecordingBrowseResponse>().toSearchResult()
            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getWorksByArtist(
        artistMbid: String,
        include: Set<WorkInclude> = emptySet(),
        limit: Int? = null,
        offset: Int? = null,
    ): Result<SearchResult<Work>> = runCatching {
        require(limit == null || limit in 1..100) { "limit должен быть от 1 до 100" }
        require(offset == null || offset >= 0) { "offset не может быть отрицательным" }

        MusicBrainzRateLimiter.acquire()
        val inc = include.joinToString(" ") { it.value }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/work") {
                parameter("fmt", "json")
                parameter("artist", artistMbid)
                if (inc.isNotEmpty()) parameter("inc", inc)
                limit?.let { parameter("limit", it) }
                offset?.let { parameter("offset", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> result.body<WorkBrowseResponse>().toSearchResult()
            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getReleasesByLabel(
        labelMbid: String,
        include: Set<ReleaseInclude> = emptySet(),
        type: String? = null,
        status: String? = null,
        limit: Int? = null,
        offset: Int? = null,
    ): Result<SearchResult<Release>> = runCatching {
        require(limit == null || limit in 1..100) { "limit должен быть от 1 до 100" }
        require(offset == null || offset >= 0) { "offset не может быть отрицательным" }

        val include = include.filter { it != ReleaseInclude.COLLECTIONS }
        MusicBrainzRateLimiter.acquire()
        val inc = include.joinToString(" ") { it.value }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/release") {
                parameter("fmt", "json")
                parameter("label", labelMbid)
                if (inc.isNotEmpty()) parameter("inc", inc)
                type?.let { parameter("type", it) }
                status?.let { parameter("status", it) }
                limit?.let { parameter("limit", it) }
                offset?.let { parameter("offset", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> result.body<ReleaseBrowseResponse>().toSearchResult()
            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getReleasesByArea(
        areaMbid: String,
        include: Set<ReleaseInclude> = emptySet(),
        type: String? = null,
        status: String? = null,
        limit: Int? = null,
        offset: Int? = null,
    ): Result<SearchResult<Release>> = runCatching {
        require(limit == null || limit in 1..100) { "limit должен быть от 1 до 100" }
        require(offset == null || offset >= 0) { "offset не может быть отрицательным" }

        val include = include.filter { it != ReleaseInclude.COLLECTIONS }
        MusicBrainzRateLimiter.acquire()
        val inc = include.joinToString(" ") { it.value }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/release") {
                parameter("fmt", "json")
                parameter("area", areaMbid)
                if (inc.isNotEmpty()) parameter("inc", inc)
                type?.let { parameter("type", it) }
                status?.let { parameter("status", it) }
                limit?.let { parameter("limit", it) }
                offset?.let { parameter("offset", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> result.body<ReleaseBrowseResponse>().toSearchResult()
            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getArtistsByArea(
        areaMbid: String,
        include: Set<ArtistInclude> = emptySet(),
        limit: Int? = null,
        offset: Int? = null,
    ): Result<SearchResult<Artist>> = runCatching {
        require(limit == null || limit in 1..100) { "limit должен быть от 1 до 100" }
        require(offset == null || offset >= 0) { "offset не может быть отрицательным" }

        val include =
            include.filter { it != ArtistInclude.RECORDINGS && it != ArtistInclude.RELEASES && it != ArtistInclude.RELEASE_GROUPS && it != ArtistInclude.WORKS && it != ArtistInclude.MEDIA && it != ArtistInclude.DISC_IDS && it != ArtistInclude.ISRCS && it != ArtistInclude.ARTIST_CREDITS && it != ArtistInclude.VARIOUS_ARTISTS }

        MusicBrainzRateLimiter.acquire()
        val inc = include.joinToString(" ") { it.value }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/artist") {
                parameter("fmt", "json")
                parameter("area", areaMbid)
                if (inc.isNotEmpty()) parameter("inc", inc)
                limit?.let { parameter("limit", it) }
                offset?.let { parameter("offset", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> result.body<ArtistBrowseResponse>().toSearchResult()
            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getLabelsByArea(
        areaMbid: String,
        include: Set<LabelInclude> = emptySet(),
        limit: Int? = null,
        offset: Int? = null,
    ): Result<SearchResult<Label>> = runCatching {
        require(limit == null || limit in 1..100) { "limit должен быть от 1 до 100" }
        require(offset == null || offset >= 0) { "offset не может быть отрицательным" }

        val include =
            include.filter { it != LabelInclude.RELEASES && it != LabelInclude.ARTIST_CREDITS && it != LabelInclude.MEDIA && it != LabelInclude.DISC_IDS }

        MusicBrainzRateLimiter.acquire()
        val inc = include.joinToString(" ") { it.value }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/label") {
                parameter("fmt", "json")
                parameter("area", areaMbid)
                if (inc.isNotEmpty()) parameter("inc", inc)
                limit?.let { parameter("limit", it) }
                offset?.let { parameter("offset", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> result.body<LabelBrowseResponse>().toSearchResult()
            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getPlacesByArea(
        areaMbid: String,
        include: Set<PlaceInclude> = emptySet(),
        limit: Int? = null,
        offset: Int? = null,
    ): Result<SearchResult<Place>> = runCatching {
        require(limit == null || limit in 1..100) { "limit должен быть от 1 до 100" }
        require(offset == null || offset >= 0) { "offset не может быть отрицательным" }

        MusicBrainzRateLimiter.acquire()
        val inc = include.joinToString(" ") { it.value }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/place") {
                parameter("fmt", "json")
                parameter("area", areaMbid)
                if (inc.isNotEmpty()) parameter("inc", inc)
                limit?.let { parameter("limit", it) }
                offset?.let { parameter("offset", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> result.body<PlaceBrowseResponse>().toSearchResult()
            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getEventsByPlace(
        placeMbid: String,
        include: Set<EventInclude> = emptySet(),
        limit: Int? = null,
        offset: Int? = null,
    ): Result<SearchResult<Event>> = runCatching {
        require(limit == null || limit in 1..100) { "limit должен быть от 1 до 100" }
        require(offset == null || offset >= 0) { "offset не может быть отрицательным" }

        MusicBrainzRateLimiter.acquire()
        val inc = include.joinToString(" ") { it.value }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/event") {
                parameter("fmt", "json")
                parameter("place", placeMbid)
                if (inc.isNotEmpty()) parameter("inc", inc)
                limit?.let { parameter("limit", it) }
                offset?.let { parameter("offset", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> result.body<EventBrowseResponse>().toSearchResult()
            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getEventsByArtist(
        artistMbid: String,
        include: Set<EventInclude> = emptySet(),
        limit: Int? = null,
        offset: Int? = null,
    ): Result<SearchResult<Event>> = runCatching {
        require(limit == null || limit in 1..100) { "limit должен быть от 1 до 100" }
        require(offset == null || offset >= 0) { "offset не может быть отрицательным" }

        MusicBrainzRateLimiter.acquire()
        val inc = include.joinToString(" ") { it.value }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/event") {
                parameter("fmt", "json")
                parameter("artist", artistMbid)
                if (inc.isNotEmpty()) parameter("inc", inc)
                limit?.let { parameter("limit", it) }
                offset?.let { parameter("offset", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> result.body<EventBrowseResponse>().toSearchResult()
            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getReleasesByRecording(
        recordingMbid: String,
        include: Set<ReleaseInclude> = emptySet(),
        /** Фильтр по типу группы: "album", "ep", "album|ep" */
        type: String? = null,
        /** Фильтр по статусу: "official", "promotion", "bootleg" */
        status: String? = null,
        /** 1..100, по умолчанию на сервере 25 */
        limit: Int? = null,
        offset: Int? = null,
    ): Result<SearchResult<Release>> = runCatching {
        require(limit == null || limit in 1..100) { "limit должен быть от 1 до 100" }
        require(offset == null || offset >= 0) { "offset не может быть отрицательным" }
        require(ReleaseInclude.ISRCS !in include || ReleaseInclude.RECORDINGS in include) {
            "ISRCS работает только вместе с RECORDINGS"
        }

        val include = include.filter { it != ReleaseInclude.COLLECTIONS }

        MusicBrainzRateLimiter.acquire()

        val inc = include.joinToString(" ") { it.value }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/release") {
                parameter("fmt", "json")
                parameter("recording", recordingMbid)
                if (inc.isNotEmpty()) parameter("inc", inc)
                type?.let { parameter("type", it) }
                status?.let { parameter("status", it) }
                limit?.let { parameter("limit", it) }
                offset?.let { parameter("offset", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                result.body<ReleaseBrowseResponse>().toSearchResult()
            }

            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getReleasesByTrack(
        trackMbid: String,
        include: Set<ReleaseInclude> = emptySet(),
        /** Фильтр по типу группы: "album", "ep", "album|ep" */
        type: String? = null,
        /** Фильтр по статусу: "official", "promotion", "bootleg" */
        status: String? = null,
        /** 1..100, по умолчанию на сервере 25 */
        limit: Int? = null,
        offset: Int? = null,
    ): Result<SearchResult<Release>> = runCatching {
        require(limit == null || limit in 1..100) { "limit должен быть от 1 до 100" }
        require(offset == null || offset >= 0) { "offset не может быть отрицательным" }
        require(ReleaseInclude.ISRCS !in include || ReleaseInclude.RECORDINGS in include) {
            "ISRCS работает только вместе с RECORDINGS"
        }

        val include = include.filter { it != ReleaseInclude.COLLECTIONS }

        MusicBrainzRateLimiter.acquire()

        val inc = include.joinToString(" ") { it.value }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/release") {
                parameter("fmt", "json")
                parameter("track", trackMbid)
                if (inc.isNotEmpty()) parameter("inc", inc)
                type?.let { parameter("type", it) }
                status?.let { parameter("status", it) }
                limit?.let { parameter("limit", it) }
                offset?.let { parameter("offset", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                result.body<ReleaseBrowseResponse>().toSearchResult()
            }

            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getReleasesByTrackArtist(
        artistMbid: String,
        include: Set<ReleaseInclude> = emptySet(),
        /** Фильтр по типу группы: "album", "ep", "album|ep" */
        type: String? = null,
        /** Фильтр по статусу: "official", "promotion", "bootleg" */
        status: String? = null,
        /** 1..100, по умолчанию на сервере 25 */
        limit: Int? = null,
        offset: Int? = null,
    ): Result<SearchResult<Release>> = runCatching {
        require(limit == null || limit in 1..100) { "limit должен быть от 1 до 100" }
        require(offset == null || offset >= 0) { "offset не может быть отрицательным" }
        require(ReleaseInclude.ISRCS !in include || ReleaseInclude.RECORDINGS in include) {
            "ISRCS работает только вместе с RECORDINGS"
        }

        val include = include.filter { it != ReleaseInclude.COLLECTIONS }

        MusicBrainzRateLimiter.acquire()

        val inc = include.joinToString(" ") { it.value }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/release") {
                parameter("fmt", "json")
                parameter("track_artist", artistMbid)
                if (inc.isNotEmpty()) parameter("inc", inc)
                type?.let { parameter("type", it) }
                status?.let { parameter("status", it) }
                limit?.let { parameter("limit", it) }
                offset?.let { parameter("offset", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                result.body<ReleaseBrowseResponse>().toSearchResult()
            }

            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getReleaseGroupsByRelease(
        releaseMbid: String,
        include: Set<ReleaseGroupInclude> = emptySet(),
        /** Фильтр по типу группы: "album", "ep", "album|ep" */
        type: String? = null,
        /** 1..100, по умолчанию на сервере 25 */
        limit: Int? = null,
        offset: Int? = null,
    ): Result<SearchResult<ReleaseGroup>> = runCatching {
        require(limit == null || limit in 1..100) { "limit должен быть от 1 до 100" }
        require(offset == null || offset >= 0) { "offset не может быть отрицательным" }

        val include = include.filter { it != ReleaseGroupInclude.RELEASES && it != ReleaseGroupInclude.MEDIA && it != ReleaseGroupInclude.DISC_IDS }

        MusicBrainzRateLimiter.acquire()

        val inc = include.joinToString(" ") { it.value }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/release-group") {
                parameter("fmt", "json")
                parameter("release", releaseMbid)
                if (inc.isNotEmpty()) parameter("inc", inc)
                type?.let { parameter("type", it) }
                limit?.let { parameter("limit", it) }
                offset?.let { parameter("offset", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                result.body<ReleaseGroupBrowseResponse>().toSearchResult()
            }

            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getRecordingsByRelease(
        releaseMbid: String,
        include: Set<RecordingInclude> = emptySet(),
        /** 1..100, по умолчанию на сервере 25 */
        limit: Int? = null,
        offset: Int? = null,
    ): Result<SearchResult<Recording>> = runCatching {
        require(limit == null || limit in 1..100) { "limit должен быть от 1 до 100" }
        require(offset == null || offset >= 0) { "offset не может быть отрицательным" }

        val include = include.filter { it != RecordingInclude.RELEASES && it != RecordingInclude.RELEASE_GROUPS && it != RecordingInclude.MEDIA && it != RecordingInclude.DISC_IDS }

        MusicBrainzRateLimiter.acquire()

        val inc = include.joinToString(" ") { it.value }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/recording") {
                parameter("fmt", "json")
                parameter("release", releaseMbid)
                if (inc.isNotEmpty()) parameter("inc", inc)
                limit?.let { parameter("limit", it) }
                offset?.let { parameter("offset", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                result.body<RecordingBrowseResponse>().toSearchResult()
            }

            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getRecordingsByWork(
        workMbid: String,
        include: Set<RecordingInclude> = emptySet(),
        /** 1..100, по умолчанию на сервере 25 */
        limit: Int? = null,
        offset: Int? = null,
    ): Result<SearchResult<Recording>> = runCatching {
        require(limit == null || limit in 1..100) { "limit должен быть от 1 до 100" }
        require(offset == null || offset >= 0) { "offset не может быть отрицательным" }

        val include = include.filter { it != RecordingInclude.RELEASES && it != RecordingInclude.RELEASE_GROUPS && it != RecordingInclude.MEDIA && it != RecordingInclude.DISC_IDS }

        MusicBrainzRateLimiter.acquire()

        val inc = include.joinToString(" ") { it.value }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/recording") {
                parameter("fmt", "json")
                parameter("work", workMbid)
                if (inc.isNotEmpty()) parameter("inc", inc)
                limit?.let { parameter("limit", it) }
                offset?.let { parameter("offset", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                result.body<RecordingBrowseResponse>().toSearchResult()
            }

            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getArtistsByRecording(
        recordingMbid: String,
        include: Set<ArtistInclude> = emptySet(),
        /** 1..100, по умолчанию на сервере 25 */
        limit: Int? = null,
        offset: Int? = null,
    ): Result<SearchResult<Artist>> = runCatching {
        require(limit == null || limit in 1..100) { "limit должен быть от 1 до 100" }
        require(offset == null || offset >= 0) { "offset не может быть отрицательным" }

        val include = include.filter { it != ArtistInclude.RECORDINGS && it != ArtistInclude.RELEASES && it != ArtistInclude.RELEASE_GROUPS && it != ArtistInclude.WORKS && it != ArtistInclude.MEDIA && it != ArtistInclude.DISC_IDS && it != ArtistInclude.ISRCS && it != ArtistInclude.ARTIST_CREDITS && it != ArtistInclude.VARIOUS_ARTISTS }

        MusicBrainzRateLimiter.acquire()

        val inc = include.joinToString(" ") { it.value }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/artist") {
                parameter("fmt", "json")
                parameter("recording", recordingMbid)
                if (inc.isNotEmpty()) parameter("inc", inc)
                limit?.let { parameter("limit", it) }
                offset?.let { parameter("offset", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                result.body<ArtistBrowseResponse>().toSearchResult()
            }

            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getArtistsByRelease(
        releaseMbid: String,
        include: Set<ArtistInclude> = emptySet(),
        /** 1..100, по умолчанию на сервере 25 */
        limit: Int? = null,
        offset: Int? = null,
    ): Result<SearchResult<Artist>> = runCatching {
        require(limit == null || limit in 1..100) { "limit должен быть от 1 до 100" }
        require(offset == null || offset >= 0) { "offset не может быть отрицательным" }

        val include = include.filter { it != ArtistInclude.RECORDINGS && it != ArtistInclude.RELEASES && it != ArtistInclude.RELEASE_GROUPS && it != ArtistInclude.WORKS && it != ArtistInclude.MEDIA && it != ArtistInclude.DISC_IDS && it != ArtistInclude.ISRCS && it != ArtistInclude.ARTIST_CREDITS && it != ArtistInclude.VARIOUS_ARTISTS }

        MusicBrainzRateLimiter.acquire()

        val inc = include.joinToString(" ") { it.value }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/artist") {
                parameter("fmt", "json")
                parameter("release", releaseMbid)
                if (inc.isNotEmpty()) parameter("inc", inc)
                limit?.let { parameter("limit", it) }
                offset?.let { parameter("offset", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                result.body<ArtistBrowseResponse>().toSearchResult()
            }

            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getArtistsByReleaseGroup(
        releaseGroupMbid: String,
        include: Set<ArtistInclude> = emptySet(),
        /** 1..100, по умолчанию на сервере 25 */
        limit: Int? = null,
        offset: Int? = null,
    ): Result<SearchResult<Artist>> = runCatching {
        require(limit == null || limit in 1..100) { "limit должен быть от 1 до 100" }
        require(offset == null || offset >= 0) { "offset не может быть отрицательным" }

        val include = include.filter { it != ArtistInclude.RECORDINGS && it != ArtistInclude.RELEASES && it != ArtistInclude.RELEASE_GROUPS && it != ArtistInclude.WORKS && it != ArtistInclude.MEDIA && it != ArtistInclude.DISC_IDS && it != ArtistInclude.ISRCS && it != ArtistInclude.ARTIST_CREDITS && it != ArtistInclude.VARIOUS_ARTISTS }

        MusicBrainzRateLimiter.acquire()

        val inc = include.joinToString(" ") { it.value }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/artist") {
                parameter("fmt", "json")
                parameter("release-group", releaseGroupMbid)
                if (inc.isNotEmpty()) parameter("inc", inc)
                limit?.let { parameter("limit", it) }
                offset?.let { parameter("offset", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                result.body<ArtistBrowseResponse>().toSearchResult()
            }

            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getArtistsByWork(
        workMbid: String,
        include: Set<ArtistInclude> = emptySet(),
        /** 1..100, по умолчанию на сервере 25 */
        limit: Int? = null,
        offset: Int? = null,
    ): Result<SearchResult<Artist>> = runCatching {
        require(limit == null || limit in 1..100) { "limit должен быть от 1 до 100" }
        require(offset == null || offset >= 0) { "offset не может быть отрицательным" }

        val include = include.filter { it != ArtistInclude.RECORDINGS && it != ArtistInclude.RELEASES && it != ArtistInclude.RELEASE_GROUPS && it != ArtistInclude.WORKS && it != ArtistInclude.MEDIA && it != ArtistInclude.DISC_IDS && it != ArtistInclude.ISRCS && it != ArtistInclude.ARTIST_CREDITS && it != ArtistInclude.VARIOUS_ARTISTS }

        MusicBrainzRateLimiter.acquire()

        val inc = include.joinToString(" ") { it.value }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/artist") {
                parameter("fmt", "json")
                parameter("work", workMbid)
                if (inc.isNotEmpty()) parameter("inc", inc)
                limit?.let { parameter("limit", it) }
                offset?.let { parameter("offset", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                result.body<ArtistBrowseResponse>().toSearchResult()
            }

            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getLabelsByRelease(
        releaseMbid: String,
        include: Set<LabelInclude> = emptySet(),
        /** 1..100, по умолчанию на сервере 25 */
        limit: Int? = null,
        offset: Int? = null,
    ): Result<SearchResult<Label>> = runCatching {
        require(limit == null || limit in 1..100) { "limit должен быть от 1 до 100" }
        require(offset == null || offset >= 0) { "offset не может быть отрицательным" }

        val include =
            include.filter { it != LabelInclude.RELEASES && it != LabelInclude.ARTIST_CREDITS && it != LabelInclude.MEDIA && it != LabelInclude.DISC_IDS }

        MusicBrainzRateLimiter.acquire()

        val inc = include.joinToString(" ") { it.value }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/label") {
                parameter("fmt", "json")
                parameter("release", releaseMbid)
                if (inc.isNotEmpty()) parameter("inc", inc)
                limit?.let { parameter("limit", it) }
                offset?.let { parameter("offset", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                result.body<LabelBrowseResponse>().toSearchResult()
            }

            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getEventsByArea(
        areaMbid: String,
        include: Set<EventInclude> = emptySet(),
        /** 1..100, по умолчанию на сервере 25 */
        limit: Int? = null,
        offset: Int? = null,
    ): Result<SearchResult<Event>> = runCatching {
        require(limit == null || limit in 1..100) { "limit должен быть от 1 до 100" }
        require(offset == null || offset >= 0) { "offset не может быть отрицательным" }

        MusicBrainzRateLimiter.acquire()

        val inc = include.joinToString(" ") { it.value }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/event") {
                parameter("fmt", "json")
                parameter("area", areaMbid)
                if (inc.isNotEmpty()) parameter("inc", inc)
                limit?.let { parameter("limit", it) }
                offset?.let { parameter("offset", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                result.body<EventBrowseResponse>().toSearchResult()
            }

            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getEventsByEvent(
        eventMbid: String,
        include: Set<EventInclude> = emptySet(),
        /** 1..100, по умолчанию на сервере 25 */
        limit: Int? = null,
        offset: Int? = null,
    ): Result<SearchResult<Event>> = runCatching {
        require(limit == null || limit in 1..100) { "limit должен быть от 1 до 100" }
        require(offset == null || offset >= 0) { "offset не может быть отрицательным" }

        MusicBrainzRateLimiter.acquire()

        val inc = include.joinToString(" ") { it.value }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/event") {
                parameter("fmt", "json")
                parameter("event", eventMbid)
                if (inc.isNotEmpty()) parameter("inc", inc)
                limit?.let { parameter("limit", it) }
                offset?.let { parameter("offset", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                result.body<EventBrowseResponse>().toSearchResult()
            }

            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }
}