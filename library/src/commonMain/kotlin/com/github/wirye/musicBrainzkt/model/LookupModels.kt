package com.github.wirye.musicBrainzkt.model

enum class ArtistInclude(val value: String) {
    RECORDINGS("recordings"),
    RELEASES("releases"),
    RELEASE_GROUPS("release-groups"),
    WORKS("works"),
    MEDIA("media"),
    DISC_IDS("discids"),
    ISRCS("isrcs"),
    ARTIST_CREDITS("artist-credits"),
    VARIOUS_ARTISTS("various-artists"),
    ALIASES("aliases"),
    ANNOTATION("annotation"),
    TAGS("tags"),
    RATINGS("ratings"),
    GENRES("genres"),
    URL_RELS("url-rels"),
    ARTIST_RELS("artist-rels"),
}

enum class ReleaseInclude(val value: String) {
    LABELS("labels"),
    RECORDINGS("recordings"),
    RELEASE_GROUPS("release-groups"),
    COLLECTIONS("collections"),
    MEDIA("media"),
    DISC_IDS("discids"),
    ISRCS("isrcs"),
    ARTIST_CREDITS("artist-credits"),
    ALIASES("aliases"),
    ANNOTATION("annotation"),
    TAGS("tags"),
    GENRES("genres"),
    URL_RELS("url-rels"),
    ARTIST_RELS("artist-rels"),
    RECORDING_LEVEL_RELS("recording-level-rels"),
}

enum class RelationInclude(val value: String) {
    AREA_RELS("area-rels"),
    ARTIST_RELS("artist-rels"),
    EVENT_RELS("event-rels"),
    GENRE_RELS("genre-rels"),
    INSTRUMENT_RELS("instrument-rels"),
    LABEL_RELS("label-rels"),
    PLACE_RELS("place-rels"),
    RECORDING_RELS("recording-rels"),
    RELEASE_RELS("release-rels"),
    RELEASE_GROUP_RELS("release-group-rels"),
    SERIES_RELS("series-rels"),
    URL_RELS("url-rels"),
    WORK_RELS("work-rels"),

    /** Связи записей, вложенных в релиз */
    RECORDING_LEVEL_RELS("recording-level-rels"),

    /** Связи release-group, вложенной в релиз (только для релизов) */
    RELEASE_GROUP_LEVEL_RELS("release-group-level-rels"),

    /** Связи произведений, вложенных в запись */
    WORK_LEVEL_RELS("work-level-rels"),
}

enum class ReleaseGroupInclude(val value: String) {
    RELEASES("releases"),

    /** Исполнители для включённых релизов */
    ARTIST_CREDITS("artist-credits"),

    /** Работает только вместе с [RELEASES] */
    MEDIA("media"),

    /** Работает только вместе с [RELEASES] */
    DISC_IDS("discids"),
    ANNOTATION("annotation"),
    TAGS("tags"),
    RATINGS("ratings"),
    GENRES("genres"),
}

enum class RecordingInclude(val value: String) {
    RELEASES("releases"),
    RELEASE_GROUPS("release-groups"),
    ARTIST_CREDITS("artist-credits"),
    ISRCS("isrcs"),

    /** Работает только вместе с [RELEASES] */
    MEDIA("media"),

    /** Работает только вместе с [RELEASES] */
    DISC_IDS("discids"),
    ANNOTATION("annotation"),
    TAGS("tags"),
    RATINGS("ratings"),
    GENRES("genres"),
}

enum class WorkInclude(val value: String) {
    ALIASES("aliases"),
    ANNOTATION("annotation"),
    TAGS("tags"),
    RATINGS("ratings"),
    GENRES("genres"),
}

enum class LabelInclude(val value: String) {
    RELEASES("releases"),

    /** Работает только вместе с [RELEASES] */
    ARTIST_CREDITS("artist-credits"),

    /** Работает только вместе с [RELEASES] */
    MEDIA("media"),

    /** Работает только вместе с [RELEASES] */
    DISC_IDS("discids"),
    ALIASES("aliases"),
    ANNOTATION("annotation"),
    TAGS("tags"),
    RATINGS("ratings"),
    GENRES("genres"),
}

/** У областей нет рейтингов. */
enum class AreaInclude(val value: String) {
    ALIASES("aliases"),
    ANNOTATION("annotation"),
    TAGS("tags"),
    GENRES("genres"),
}

/** У мест нет рейтингов. */
enum class PlaceInclude(val value: String) {
    ALIASES("aliases"),
    ANNOTATION("annotation"),
    TAGS("tags"),
    GENRES("genres"),
}

enum class EventInclude(val value: String) {
    ALIASES("aliases"),
    ANNOTATION("annotation"),
    TAGS("tags"),
    RATINGS("ratings"),
    GENRES("genres"),
}

/** У инструментов нет рейтингов. */
enum class InstrumentInclude(val value: String) {
    ALIASES("aliases"),
    ANNOTATION("annotation"),
    TAGS("tags"),
    GENRES("genres"),
}

/** У серий нет рейтингов. */
enum class SeriesInclude(val value: String) {
    ALIASES("aliases"),
    ANNOTATION("annotation"),
    TAGS("tags"),
    GENRES("genres"),
}
