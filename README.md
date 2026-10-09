# musicBrainz-kt

Неофициальная Kotlin-библиотека для работы с [API MusicBrainz](https://musicbrainz.org/doc/MusicBrainz_API).
Не связана с MusicBrainz и MetaBrainz Foundation.

Обновляется по мере развития моего приложения Garden: если чего-то здесь нет, оно скоро появится.

## Установка

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    repositories { maven("https://jitpack.io") }
}

// build.gradle.kts
dependencies {
    implementation("com.github.Wirye.musicBrainz-kt:musicBrainz-kt:1.1.4")
}
```

## Быстрый старт

> **Важно:** MusicBrainz требует осмысленный User-Agent: название приложения,
> версию и контакт. Без него запросы могут блокироваться.

```kotlin
val client = MusicBrainzClient(userAgent = "MyApp/1.0.0 ( me@example.com )")

client.search.searchArtists("natori", limit = 5)
    .onSuccess { page -> page.items.forEach { println(it.name) } }
    .onFailure { println("Ошибка: ${it.message}") }
```

Клиент создаётся один раз. В нём:

- `client.search` - поиск (исполнители, релизы, записи и т. д.)
- `client.lookup` - данные по MBID
- `client.browse` - списки связанных сущностей (например, релизы исполнителя)
- `client.art` - обложки (Deezer)

## Ошибки

Все функции возвращают `Result<T>`, а исключения приходят в `Result.failure`

- `NotFoundException` - ничего не найдено
- `ValidationErrorException(message)` - неверный запрос, в сообщении текст от сервера
- `ServerErrorException(code)` - ошибка сервера, код `503` обычно значит превышение лимита запросов

## Лимит запросов

MusicBrainz допускает не больше 1 запроса в секунду с одного IP. В библиотеке есть логика под это ограничение, вам не надо писать его самим, но я советую всё же не вызывать множество функций одновременно: у меня в тесте на 51 одновременный вызов функций api вышло 8 ошибок 503

## Данные и лицензии

Основные данные MusicBrainz распространяются под CC0, теги, рейтинги и аннотации под
CC BY-NC-SA: их можно использовать только некоммерчески, с указанием MusicBrainz
как источника. Для коммерческого использования нужна лицензия MetaBrainz
([подробнее](https://musicbrainz.org/doc/About/Data_License)).
Обложки предоставляет Cover Art Archive, у него свои условия.
