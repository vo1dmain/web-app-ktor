# web-app-ktor

English | [Русский](README.ru.md)

A REST API for a university built with Ktor: news, questions & answers and the timetable ("daybook"). A pet project.

## Stack

- Kotlin 2.4, JVM 21
- Ktor 3 (Netty, Resources, kotlinx.serialization)
- Exposed 1.x on H2
- Koin 4 with the Koin Compiler Plugin (the dependency graph is checked at compile time)
- Gradle 9: version catalog, convention plugins in `build-logic`

## Running

JDK 21 is required.

```bash
./gradlew :app:run                  # http://localhost:8080
./gradlew :app:run -Pdevelopment    # with Ktor development mode
./gradlew build                     # build and run all tests
```

`GET /` returns an HTML index of all GET routes.

## Configuration

Settings live in `app/src/main/resources/application.conf`:

| Key                                                 | Default                   | Purpose                                                         |
|-----------------------------------------------------|---------------------------|-----------------------------------------------------------------|
| `daybook.timeZone`                                  | `Europe/Moscow`           | Time zone of the timetable: week parity, `/daybook/meta`        |
| `database.news`, `database.qna`, `database.daybook` | `jdbc:h2:file:./build/…`  | JDBC URLs of the three databases                                |
| `database.demoData`                                 | `true`                    | Fill empty databases with demo news and groups                  |
| `database.importFile`                               | env `DAYBOOK_IMPORT_FILE` | Path to a JSON export of groups and timetables to load at start |

The databases are H2 files, `app/build/` under `:app:run`. There are no migrations: after changing tables, delete the database files. The format of the import file is described in `persistence-exposed/.../daybook/ImportedData.kt`.

## API

All routes are under `/api/v1`. Lists are paginated with `?page=` (1-based, 10 items per page) and return `200 []` when empty.

**News** — `/news`
- `GET /articles` (`?category=` can be repeated), `GET /articles/{id}`
- `GET /categories` (`?parent=`), `GET /categories/{id}`

**Questions & answers** — `/qna`
- `GET /posts`, `GET /posts/{id}`
- `GET /questions`, `GET /questions/{id}`, `POST /questions`

**Timetable** — `/daybook`
- `GET /meta` — time zone, current week and all reference data at once; separately: `/meta/week`, `/meta/levels`, `/meta/degrees`, `/meta/forms`, `/meta/table-types`, `/meta/groups`, `/meta/session-types`
- `GET /timetables` (`?group=`, `?type=`, `?format=`), `GET /timetables/{id}`, `POST /timetables`
- `POST /timetables/{id}/sessions` — attach a session to a timetable
- `GET /sessions/regular` (`?timetable=`, `?subject=`, `?instructor=`, `?place=`, `?type=`, `?day=`, `?time=`, `?week_option=`), `POST /sessions/regular`
- `GET /sessions/dated` (`?timetable=`, `?subject=`, `?instructor=`, `?place=`, `?type=`, `?dateTime=`), `POST /sessions/dated`

`POST` returns `201 Created` with the new id (attaching a session returns the link itself). Errors come as JSON: `400` for invalid parameters, `404` for a missing entity, `409` for a duplicate, `415` for a non-JSON body, `422` for a reference to a missing entity.

## Structure

Ports & adapters; each layer is a Gradle module, code inside is grouped by area (`news`, `qna`, `daybook`).

| Module                | Contents                                                      |
|-----------------------|---------------------------------------------------------------|
| `domain`              | Models, repository ports, services. No framework dependencies |
| `persistence-exposed` | Port implementations on Exposed, tables, seed data            |
| `api-ktor`            | Routes, DTOs, Ktor plugins, error handling                    |
| `app`                 | Server entry point, configuration, Koin graph                 |
| `build-logic`         | Gradle convention plugins                                     |

## Tests

- `domain` — services with in-line fakes and a fixed clock
- `persistence-exposed` — repositories against in-memory H2
- `api-ktor` — routes via `testApplication` over fake repositories, no database or DI

```bash
./gradlew test
```
