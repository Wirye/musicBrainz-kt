package com.github.wirye.musicBrainzkt.exception

sealed class MusicBrainzException(message: String) : Exception(message) {
    class ValidationErrorException(message: String) : MusicBrainzException("Ошибка валидации (неверный формат данных) $message")
    class ServerErrorException(code: Int) : MusicBrainzException("Ошибка сервера (код $code)")
    class NotFoundException : MusicBrainzException("Ничего не найдено")
}
