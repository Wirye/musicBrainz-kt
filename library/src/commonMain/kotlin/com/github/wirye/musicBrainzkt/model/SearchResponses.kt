package com.github.wirye.musicBrainzkt.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Обёртки ответов поиска: общие поля одинаковы, а ключ со списком у каждого типа свой
 * (artists, releases, release-groups...). Наружу отдаётся единый [SearchResult].
 */
internal interface SearchResponse<T> {
    val count: Int
    val offset: Int
    val items: List<T>

    fun toSearchResult(): SearchResult<T> = SearchResult(count, offset, items)
}

@Serializable
internal data class AnnotationSearchResponse(
    val created: String? = null,
    override val count: Int,
    override val offset: Int,
    val annotations: List<MbAnnotation> = emptyList(),
) : SearchResponse<MbAnnotation> {
    override val items: List<MbAnnotation> get() = annotations
}

@Serializable
internal data class AreaSearchResponse(
    val created: String? = null,
    override val count: Int,
    override val offset: Int,
    val areas: List<Area> = emptyList(),
) : SearchResponse<Area> {
    override val items: List<Area> get() = areas
}

@Serializable
internal data class ArtistSearchResponse(
    val created: String? = null,
    override val count: Int,
    override val offset: Int,
    val artists: List<Artist> = emptyList(),
) : SearchResponse<Artist> {
    override val items: List<Artist> get() = artists
}

@Serializable
internal data class CdStubSearchResponse(
    val created: String? = null,
    override val count: Int,
    override val offset: Int,
    val cdstubs: List<CdStub> = emptyList(),
) : SearchResponse<CdStub> {
    override val items: List<CdStub> get() = cdstubs
}

@Serializable
internal data class EventSearchResponse(
    val created: String? = null,
    override val count: Int,
    override val offset: Int,
    val events: List<Event> = emptyList(),
) : SearchResponse<Event> {
    override val items: List<Event> get() = events
}

@Serializable
internal data class InstrumentSearchResponse(
    val created: String? = null,
    override val count: Int,
    override val offset: Int,
    val instruments: List<Instrument> = emptyList(),
) : SearchResponse<Instrument> {
    override val items: List<Instrument> get() = instruments
}

@Serializable
internal data class LabelSearchResponse(
    val created: String? = null,
    override val count: Int,
    override val offset: Int,
    val labels: List<Label> = emptyList(),
) : SearchResponse<Label> {
    override val items: List<Label> get() = labels
}

@Serializable
internal data class PlaceSearchResponse(
    val created: String? = null,
    override val count: Int,
    override val offset: Int,
    val places: List<Place> = emptyList(),
) : SearchResponse<Place> {
    override val items: List<Place> get() = places
}

@Serializable
internal data class RecordingsSearchResponse(
    val created: String? = null,
    override val count: Int,
    override val offset: Int,
    val recordings: List<Recording> = emptyList(),
) : SearchResponse<Recording> {
    override val items: List<Recording> get() = recordings
}

@Serializable
internal data class ReleaseGroupSearchResponse(
    val created: String? = null,
    override val count: Int,
    override val offset: Int,
    @SerialName("release-groups") val releaseGroups: List<ReleaseGroup> = emptyList(),
) : SearchResponse<ReleaseGroup> {
    override val items: List<ReleaseGroup> get() = releaseGroups
}

@Serializable
internal data class ReleaseSearchResponse(
    val created: String? = null,
    override val count: Int,
    override val offset: Int,
    val releases: List<Release> = emptyList(),
) : SearchResponse<Release> {
    override val items: List<Release> get() = releases
}

@Serializable
internal data class SeriesSearchResponse(
    val created: String? = null,
    override val count: Int,
    override val offset: Int,
    val series: List<Series> = emptyList(),
) : SearchResponse<Series> {
    override val items: List<Series> get() = series
}

@Serializable
internal data class TagSearchResponse(
    val created: String? = null,
    override val count: Int,
    override val offset: Int,
    val tags: List<TagSearchItem> = emptyList(),
) : SearchResponse<TagSearchItem> {
    override val items: List<TagSearchItem> get() = tags
}

@Serializable
internal data class UrlSearchResponse(
    val created: String? = null,
    override val count: Int,
    override val offset: Int,
    val urls: List<MbUrl> = emptyList(),
) : SearchResponse<MbUrl> {
    override val items: List<MbUrl> get() = urls
}

@Serializable
internal data class WorkSearchResponse(
    val created: String? = null,
    override val count: Int,
    override val offset: Int,
    val works: List<Work> = emptyList(),
) : SearchResponse<Work> {
    override val items: List<Work> get() = works
}
