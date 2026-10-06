package com.github.wirye.musicBrainzkt.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ReleaseBrowseResponse(
    @SerialName("release-count") override val count: Int,
    @SerialName("release-offset") override val offset: Int,
    val releases: List<Release> = emptyList(),
) : SearchResponse<Release> {
    override val items: List<Release> get() = releases
}

@Serializable
internal data class ReleaseGroupBrowseResponse(
    @SerialName("release-group-count") override val count: Int,
    @SerialName("release-group-offset") override val offset: Int,
    @SerialName("release-groups") val releaseGroups: List<ReleaseGroup> = emptyList(),
) : SearchResponse<ReleaseGroup> {
    override val items: List<ReleaseGroup> get() = releaseGroups
}

@Serializable
internal data class ArtistBrowseResponse(
    @SerialName("artist-count") override val count: Int,
    @SerialName("artist-offset") override val offset: Int,
    val artists: List<Artist> = emptyList(),
) : SearchResponse<Artist> {
    override val items: List<Artist> get() = artists
}

@Serializable
internal data class RecordingBrowseResponse(
    @SerialName("recording-count") override val count: Int,
    @SerialName("recording-offset") override val offset: Int,
    val recordings: List<Recording> = emptyList(),
) : SearchResponse<Recording> {
    override val items: List<Recording> get() = recordings
}

@Serializable
internal data class WorkBrowseResponse(
    @SerialName("work-count") override val count: Int,
    @SerialName("work-offset") override val offset: Int,
    val works: List<Work> = emptyList(),
) : SearchResponse<Work> {
    override val items: List<Work> get() = works
}

@Serializable
internal data class LabelBrowseResponse(
    @SerialName("label-count") override val count: Int,
    @SerialName("label-offset") override val offset: Int,
    val labels: List<Label> = emptyList(),
) : SearchResponse<Label> {
    override val items: List<Label> get() = labels
}

@Serializable
internal data class PlaceBrowseResponse(
    @SerialName("place-count") override val count: Int,
    @SerialName("place-offset") override val offset: Int,
    val places: List<Place> = emptyList(),
) : SearchResponse<Place> {
    override val items: List<Place> get() = places
}

@Serializable
internal data class EventBrowseResponse(
    @SerialName("event-count") override val count: Int,
    @SerialName("event-offset") override val offset: Int,
    val events: List<Event> = emptyList(),
) : SearchResponse<Event> {
    override val items: List<Event> get() = events
}