# web-app-ktor

[English](README.md) | Русский

REST API для университета на Ktor: новости, вопросы и ответы и расписание («daybook»). Пет-проект.

## Стек

- Kotlin 2.4, JVM 21
- Ktor 3 (Netty, Resources, kotlinx.serialization)
- Exposed 1.x на H2
- Koin 4 с Koin Compiler Plugin (граф зависимостей проверяется при компиляции)
- Gradle 9: version catalog, convention-плагины в `build-logic`

## Запуск

Нужен JDK 21.

```bash
./gradlew :app:run                  # http://localhost:8080
./gradlew :app:run -Pdevelopment    # в режиме разработки Ktor
./gradlew build                     # сборка и все тесты
```

`GET /` отдаёт HTML-оглавление всех GET-маршрутов.

## Настройки

Настройки лежат в `app/src/main/resources/application.conf`:

| Ключ                                                | По умолчанию              | Назначение                                                      |
|-----------------------------------------------------|---------------------------|-----------------------------------------------------------------|
| `daybook.timeZone`                                  | `Europe/Moscow`           | Часовой пояс расписания: чётность недели, `/daybook/meta`       |
| `database.news`, `database.qna`, `database.daybook` | `jdbc:h2:file:./build/…`  | JDBC-адреса трёх баз                                            |
| `database.demoData`                                 | `true`                    | Заполнить пустые базы демо-новостями и группами                 |
| `database.importFile`                               | env `DAYBOOK_IMPORT_FILE` | Путь к JSON-выгрузке групп и расписаний, загружается при старте |

Базы — файлы H2, при `:app:run` они в `app/build/`. Миграций нет: после изменения таблиц удалите файлы баз. Формат файла импорта описан в `persistence-exposed/.../daybook/ImportedData.kt`.

## API

Все маршруты под `/api/v1`. Списки постраничные, `?page=` (с 1, по 10 записей); пустой список — `200 []`.

**Новости** — `/news`
- `GET /articles` (`?category=` можно повторять), `GET /articles/{id}`
- `GET /categories` (`?parent=`), `GET /categories/{id}`

**Вопросы и ответы** — `/qna`
- `GET /posts`, `GET /posts/{id}`
- `GET /questions`, `GET /questions/{id}`, `POST /questions`

**Расписание** — `/daybook`
- `GET /meta` — часовой пояс, текущая неделя и все справочники разом; по отдельности: `/meta/week`, `/meta/levels`, `/meta/degrees`, `/meta/forms`, `/meta/table-types`, `/meta/groups`, `/meta/session-types`
- `GET /timetables` (`?group=`, `?type=`, `?format=`), `GET /timetables/{id}`, `POST /timetables`
- `POST /timetables/{id}/sessions` — привязать занятие к расписанию
- `GET /sessions/regular` (`?timetable=`, `?subject=`, `?instructor=`, `?place=`, `?type=`, `?day=`, `?time=`, `?week_option=`), `POST /sessions/regular`
- `GET /sessions/dated` (`?timetable=`, `?subject=`, `?instructor=`, `?place=`, `?type=`, `?dateTime=`), `POST /sessions/dated`

`POST` отвечает `201 Created` с id новой записи (привязка занятия — самой связкой). Ошибки приходят в JSON: `400` — неверные параметры, `404` — нет записи, `409` — дубликат, `415` — тело не в JSON, `422` — ссылка на несуществующую запись.

## Структура

Порты и адаптеры; каждый слой — Gradle-модуль, код внутри разложен по областям (`news`, `qna`, `daybook`).

| Модуль                | Содержимое                                                           |
|-----------------------|----------------------------------------------------------------------|
| `domain`              | Модели, порты репозиториев, сервисы. Без зависимостей от фреймворков |
| `persistence-exposed` | Реализации портов на Exposed, таблицы, начальные данные              |
| `api-ktor`            | Маршруты, DTO, плагины Ktor, обработка ошибок                        |
| `app`                 | Точка входа сервера, настройки, граф Koin                            |
| `build-logic`         | Convention-плагины Gradle                                            |

## Тесты

- `domain` — сервисы на встроенных фейках с фиксированными часами
- `persistence-exposed` — репозитории на H2 в памяти
- `api-ktor` — маршруты через `testApplication` на фейках репозиториев, без базы и DI

```bash
./gradlew test
```
