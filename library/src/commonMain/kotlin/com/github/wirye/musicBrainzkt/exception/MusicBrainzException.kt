package com.github.wirye.musicBrainzkt.exception

sealed class MusicBrainzException(message: String) : Exception(message) {
    class InvalidCredentialsException : MusicBrainzException("Неправильный логин или пароль")
    class ValidationErrorException(message: String) : MusicBrainzException("Ошибка валидации (неверный формат данных) $message")
    class HtmlResponseException(code: Int) : MusicBrainzException("Сервер вернул HTML-страницу (Код $code). Возможно, заблокировано антиботом.")
    class ServerErrorException(code: Int) : MusicBrainzException("Ошибка сервера (код $code)")
    class NotFoundException : MusicBrainzException("Ничего не найдено")
}
