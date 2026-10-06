package com.github.wirye.musicBrainzkt.api

import com.github.wirye.musicBrainzkt.MusicBrainzRateLimiter
import com.github.wirye.musicBrainzkt.exception.MusicBrainzException
import com.github.wirye.musicBrainzkt.model.Area
import com.github.wirye.musicBrainzkt.model.AreaInclude
import com.github.wirye.musicBrainzkt.model.Artist
import com.github.wirye.musicBrainzkt.model.ArtistInclude
import com.github.wirye.musicBrainzkt.model.Event
import com.github.wirye.musicBrainzkt.model.EventInclude
import com.github.wirye.musicBrainzkt.model.Instrument
import com.github.wirye.musicBrainzkt.model.InstrumentInclude
import com.github.wirye.musicBrainzkt.model.Label
import com.github.wirye.musicBrainzkt.model.LabelInclude
import com.github.wirye.musicBrainzkt.model.MbGenre
import com.github.wirye.musicBrainzkt.model.MbUrl
import com.github.wirye.musicBrainzkt.model.Place
import com.github.wirye.musicBrainzkt.model.PlaceInclude
import com.github.wirye.musicBrainzkt.model.Recording
import com.github.wirye.musicBrainzkt.model.RecordingInclude
import com.github.wirye.musicBrainzkt.model.RelationInclude
import com.github.wirye.musicBrainzkt.model.Release
import com.github.wirye.musicBrainzkt.model.ReleaseGroup
import com.github.wirye.musicBrainzkt.model.ReleaseGroupInclude
import com.github.wirye.musicBrainzkt.model.ReleaseInclude
import com.github.wirye.musicBrainzkt.model.Series
import com.github.wirye.musicBrainzkt.model.SeriesInclude
import com.github.wirye.musicBrainzkt.model.Work
import com.github.wirye.musicBrainzkt.model.WorkInclude
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode

class LookupApi(
    private val httpClient: HttpClient
) {
    suspend fun getArtist(
        mbid: String,
        include: Set<ArtistInclude> = emptySet(),
        type: String? = null,
        status: String? = null,
    ): Result<Artist> = runCatching {
        validateArtistInclude(include)
        MusicBrainzRateLimiter.acquire()

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/artist/$mbid") {
                parameter("fmt", "json")
                if (include.isNotEmpty()) {
                    parameter("inc", include.joinToString(" ") { it.value })
                }
                type?.let { parameter("type", it) }
                status?.let { parameter("status", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                result.body()
            }

            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    private fun validateArtistInclude(include: Set<ArtistInclude>) {
        val hasReleases = ArtistInclude.RELEASES in include
        val hasRecordings = ArtistInclude.RECORDINGS in include

        require(ArtistInclude.ARTIST_CREDITS !in include || hasReleases || hasRecordings) {
            "ARTIST_CREDITS работает только вместе с RELEASES или RECORDINGS"
        }
        require(
            (ArtistInclude.MEDIA !in include && ArtistInclude.DISC_IDS !in include &&
                    ArtistInclude.VARIOUS_ARTISTS !in include) || hasReleases
        ) {
            "MEDIA, DISC_IDS и VARIOUS_ARTISTS работают только вместе с RELEASES"
        }
        require(ArtistInclude.ISRCS !in include || hasRecordings) {
            "ISRCS работает только вместе с RECORDINGS"
        }
    }

    suspend fun getRelease(
        mbid: String,
        include: Set<ReleaseInclude> = emptySet(),
    ): Result<Release> = runCatching {
        MusicBrainzRateLimiter.acquire()

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/release/$mbid") {
                parameter("fmt", "json")
                if (include.isNotEmpty()) {
                    parameter("inc", include.joinToString(" ") { it.value })
                }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                result.body()
            }

            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getReleaseGroup(
        mbid: String,
        include: Set<ReleaseGroupInclude> = emptySet(),
        relations: Set<RelationInclude> = emptySet(),
        /** Фильтр включённых релизов по типу группы: "album", "ep", "album|ep" */
        type: String? = null,
        /** Фильтр включённых релизов по статусу: "official", "bootleg"... */
        status: String? = null,
    ): Result<ReleaseGroup> = runCatching {
        require(
            (ReleaseGroupInclude.MEDIA !in include && ReleaseGroupInclude.DISC_IDS !in include) ||
                    ReleaseGroupInclude.RELEASES in include
        ) { "MEDIA и DISC_IDS работают только вместе с RELEASES" }

        MusicBrainzRateLimiter.acquire()

        val inc = (include.map { it.value } + relations.map { it.value }).joinToString(" ")

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/release-group/$mbid") {
                parameter("fmt", "json")
                if (inc.isNotEmpty()) parameter("inc", inc)
                type?.let { parameter("type", it) }
                status?.let { parameter("status", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                result.body()
            }

            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getRecording(
        mbid: String,
        include: Set<RecordingInclude> = emptySet(),
        relations: Set<RelationInclude> = emptySet(),
        type: String? = null,
        status: String? = null,
    ): Result<Recording> = runCatching {
        require(
            (RecordingInclude.MEDIA !in include && RecordingInclude.DISC_IDS !in include) ||
                    RecordingInclude.RELEASES in include
        ) { "MEDIA и DISC_IDS работают только вместе с RELEASES" }

        MusicBrainzRateLimiter.acquire()

        val inc = (include.map { it.value } + relations.map { it.value }).joinToString(" ")

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/recording/$mbid") {
                parameter("fmt", "json")
                if (inc.isNotEmpty()) parameter("inc", inc)
                type?.let { parameter("type", it) }
                status?.let { parameter("status", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                result.body()
            }

            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getWork(
        mbid: String,
        include: Set<WorkInclude> = emptySet(),
        relations: Set<RelationInclude> = emptySet(),
    ): Result<Work> = runCatching {
        MusicBrainzRateLimiter.acquire()

        val inc = (include.map { it.value } + relations.map { it.value }).joinToString(" ")

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/work/$mbid") {
                parameter("fmt", "json")
                if (inc.isNotEmpty()) parameter("inc", inc)
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                result.body()
            }

            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getLabel(
        mbid: String,
        include: Set<LabelInclude> = emptySet(),
        relations: Set<RelationInclude> = emptySet(),
        type: String? = null,
        status: String? = null,
    ): Result<Label> = runCatching {
        require(
            (LabelInclude.ARTIST_CREDITS !in include && LabelInclude.MEDIA !in include &&
                    LabelInclude.DISC_IDS !in include) || LabelInclude.RELEASES in include
        ) { "ARTIST_CREDITS, MEDIA и DISC_IDS работают только вместе с RELEASES" }

        MusicBrainzRateLimiter.acquire()

        val inc = (include.map { it.value } + relations.map { it.value }).joinToString(" ")

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/label/$mbid") {
                parameter("fmt", "json")
                if (inc.isNotEmpty()) parameter("inc", inc)
                type?.let { parameter("type", it) }
                status?.let { parameter("status", it) }
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                result.body()
            }

            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getArea(
        mbid: String,
        include: Set<AreaInclude> = emptySet(),
        relations: Set<RelationInclude> = emptySet(),
    ): Result<Area> = runCatching {
        MusicBrainzRateLimiter.acquire()

        val inc = (include.map { it.value } + relations.map { it.value }).joinToString(" ")

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/area/$mbid") {
                parameter("fmt", "json")
                if (inc.isNotEmpty()) parameter("inc", inc)
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                result.body()
            }

            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getPlace(
        mbid: String,
        include: Set<PlaceInclude> = emptySet(),
        relations: Set<RelationInclude> = emptySet(),
    ): Result<Place> = runCatching {
        MusicBrainzRateLimiter.acquire()

        val inc = (include.map { it.value } + relations.map { it.value }).joinToString(" ")

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/place/$mbid") {
                parameter("fmt", "json")
                if (inc.isNotEmpty()) parameter("inc", inc)
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                result.body()
            }

            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getEvent(
        mbid: String,
        include: Set<EventInclude> = emptySet(),
        relations: Set<RelationInclude> = emptySet(),
    ): Result<Event> = runCatching {
        MusicBrainzRateLimiter.acquire()

        val inc = (include.map { it.value } + relations.map { it.value }).joinToString(" ")

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/event/$mbid") {
                parameter("fmt", "json")
                if (inc.isNotEmpty()) parameter("inc", inc)
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                result.body()
            }

            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getInstrument(
        mbid: String,
        include: Set<InstrumentInclude> = emptySet(),
        relations: Set<RelationInclude> = emptySet(),
    ): Result<Instrument> = runCatching {
        MusicBrainzRateLimiter.acquire()

        val inc = (include.map { it.value } + relations.map { it.value }).joinToString(" ")

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/instrument/$mbid") {
                parameter("fmt", "json")
                if (inc.isNotEmpty()) parameter("inc", inc)
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                result.body()
            }

            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    suspend fun getSeries(
        mbid: String,
        include: Set<SeriesInclude> = emptySet(),
        relations: Set<RelationInclude> = emptySet(),
    ): Result<Series> = runCatching {
        MusicBrainzRateLimiter.acquire()

        val inc = (include.map { it.value } + relations.map { it.value }).joinToString(" ")

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/series/$mbid") {
                parameter("fmt", "json")
                if (inc.isNotEmpty()) parameter("inc", inc)
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                result.body()
            }

            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    /** У жанров нет ни inc, ни связей: только базовые поля. */
    suspend fun getGenre(mbid: String): Result<MbGenre> = runCatching {
        MusicBrainzRateLimiter.acquire()

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/genre/$mbid") {
                parameter("fmt", "json")
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                result.body()
            }

            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    /** Для url поддерживаются только связи (relations), других inc нет. */
    suspend fun getUrl(
        mbid: String,
        relations: Set<RelationInclude> = emptySet(),
    ): Result<MbUrl> = runCatching {
        val relations =
            relations.filter {
                it != RelationInclude.RELEASE_GROUP_LEVEL_RELS &&
                        it != RelationInclude.RECORDING_LEVEL_RELS &&
                        it != RelationInclude.WORK_LEVEL_RELS
            }
        MusicBrainzRateLimiter.acquire()

        val inc = relations.joinToString(" ") { it.value }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/url/$mbid") {
                parameter("fmt", "json")
                if (inc.isNotEmpty()) parameter("inc", inc)
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                result.body()
            }

            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }

    /**
     * Поиск сущности ссылки по самому адресу из другого сервиса, например "https://open.spotify.com/artist/...".
     * Чтобы узнать, к какому артисту или релизу привязана ссылка, добавьте нужные *_RELS.
     * Если такой ссылки нет в базе, вернётся NotFoundException.
     */
    suspend fun getResourceByUrl(
        url: String,
        relations: Set<RelationInclude> = emptySet(),
    ): Result<MbUrl> = runCatching {
        val relations =
            relations.filter {
                it != RelationInclude.RELEASE_GROUP_LEVEL_RELS &&
                        it != RelationInclude.RECORDING_LEVEL_RELS &&
                        it != RelationInclude.WORK_LEVEL_RELS
            }
        MusicBrainzRateLimiter.acquire()

        val inc = relations.joinToString(" ") { it.value }

        val result =
            httpClient.get("https://musicbrainz.org/ws/2/url") {
                parameter("fmt", "json")
                // Ktor сам экранирует адрес целиком, в том числе ? и & внутри него
                parameter("resource", url)
                if (inc.isNotEmpty()) parameter("inc", inc)
            }

        when (result.status) {
            HttpStatusCode.OK -> {
                result.body()
            }

            HttpStatusCode.NotFound -> throw MusicBrainzException.NotFoundException()
            HttpStatusCode.BadRequest -> throw MusicBrainzException.ValidationErrorException(result.bodyAsText())
            else -> throw MusicBrainzException.ServerErrorException(result.status.value)
        }
    }
}
