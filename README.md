# musicBrainz-kt

Kotlin-библиотека для работы с API MusicBrainz.
(Обновляется по мере развития моего приложения Garden, если тут чего-то нету, то оно скоро будет)

Чтобы начать работу создайте MusicBrainzClient(), в нём находятся все функции, например для входа в аккаунт - MusicBrainzClient().search.auth.searchArtists(query, limit) и так далее

ВАЖНО: При создании клиента библиотеки, обязательно указывать осмысленный User-Agent, это требование самого api MusicBrainz!!!

ОЧЕНЬ ВАЖНО: В httpClient указывайте json → ignoreUnknownKeys = true
