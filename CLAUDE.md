# personal-website-kotlinjs

Kotlin Multiplatform project powering gchristov.com. KotlinJS/IR transpiles to Node.js (backend services) and browser (frontend); services run as Docker containers on Google Cloud Run.

## Project layout

```
/                        ← root (composite build aggregator)
├── common/              ← shared library modules (composite build)
│   ├── kotlin/          ← DI, logging, serialization, coroutine utilities
│   ├── network/         ← Ktor HTTP client + Express HTTP server wrapper
│   ├── network-testfixtures/ ← FakeHttpResponse for handler tests
│   ├── monitoring/      ← error reporting (posts Warn/Error to Slack webhook)
│   ├── slack/           ← Slack HTTP API client (SlackSender, SlackMessage)
│   └── test/            ← shared test utilities (FakeCoroutineDispatcher, FakeLogger, FakeResponse)
├── contact/             ← Contact form microservice (POST /api/contact → Slack webhook)
│   ├── domain/          ← PostContactUseCase, ContactConfig (reads webhook URL from BuildConfig)
│   ├── adapter/         ← PostContactHttpHandler
│   ├── service/         ← entry point, DI wiring, Dockerfile
│   ├── test-fixtures/   ← FakeContactHttpRequest, FakePostContactUseCase
│   └── infra/           ← Pulumi YAML (Cloud Run)
├── landing-page-web/    ← Browser frontend (Kotlin/JS → HTML/CSS/JS)
├── proxy-web/           ← nginx reverse proxy (routes /api/contact and / )
└── tools/               ← developer tooling
    ├── docker/          ← Docker Compose files per service for local dev
    └── scripts/         ← run_local.sh, secrets.sh, list_services.sh
```

Each backend microservice follows hexagonal architecture:
- `domain/` — use cases, domain models, `BuildConfig` secrets
- `adapter/` — HTTP handlers
- `service/` — entry point, DI wiring, Docker image

## Composite builds

`common/` and `contact/` are standalone Gradle composite builds included from the root via `settings.gradle.kts`. Each composite build has its own `settings.gradle.kts`, `build.gradle.kts`, `gradlew`, and `gradle.properties`.

**Critical distinction:**
- Inside a composite build, modules reference each other via `projects.*` (e.g. `projects.kotlin`)
- Cross-composite references use the version catalog (e.g. `libs.common.slack`, `libs.common.network`)
- New `common` modules must be added to `gradle/libs.versions.toml` under `[libraries]`

## DI framework

Kodein. Every module extends `DiModule()`:

```kotlin
object ContactDomainModule : DiModule() {
    override fun name() = "contact-domain"
    override fun bindDependencies(builder: DI.Builder) {
        builder.apply {
            bindSingleton { ContactConfig.fromBuildConfig() }
            bindProvider { RealPostContactUseCase(slackSender = instance(), config = instance()) }
        }
    }
}
```

Services wire all modules in `*Service.kt` via `DiGraph.registerModules(...)`. Order matters — provide dependencies before consumers.

## Error handling

Arrow `Either<Throwable, T>` throughout. Use `either { }` DSL and `.bind()`.

## Secrets

Each module that needs secrets has a `secrets.properties` file (gitignored). Read via `envSecret()` in `build.gradle.kts` and compiled into `BuildConfig` by the `BuildConfigPlugin`:

```
# contact/domain/secrets.properties
CONTACT_SLACK_WEBHOOK_URL=https://hooks.slack.com/...

# common/monitoring/secrets.properties
MONITORING_SLACK_URL=https://hooks.slack.com/...
```

In CI, `tools/scripts/secrets.sh` writes these files from GitHub secrets before the build runs.

## Common/slack module

`SlackSender` is the single class for Slack HTTP calls:
- `postMessageToUrl(url, message)` — post to a webhook URL (used by contact and monitoring)
- `postMessage(token, message)` — post to a channel by token
- `authUser(clientId, clientSecret, code)` — OAuth token exchange
- `deleteMessage(token, channelId, timestamp)` — delete a message

`SlackMessage` has all fields defaulted — only set what you need.

## Local development

```bash
# Build and run everything via Docker (from repo root):
bash tools/scripts/run_local.sh

# Access via proxy (not directly via service ports):
# http://localhost:8080  → proxy (entry point)
# http://localhost:8081  → landing-page-web (direct, no /api/contact routing)
# http://localhost:8082  → contact service (direct)
```

The contact form at `localhost:8080` POSTs to `/api/contact` which nginx routes to the contact service.

## Build commands

```bash
# From repo root:
./gradlew assemble           # build all composite builds
./gradlew jsTest             # run all tests
./gradlew --max-workers=1 assemble   # sequential build (avoids yarn cache clashes)

# From a composite build directory (e.g. cd contact):
./gradlew assemble
./gradlew jsTest
```

## Tests

Uses `kotlin.test` with mocha as the JS runner. Test files go in `src/commonTest/kotlin/`.

**Known quirk:** mocha prints `"0 passing (0ms)"` — this is a dry-run pass by design (Kotlin/JS IR runs mocha twice; the first pass verifies the file loads, the second uses the TeamCity reporter that Gradle reads for XML). Tests are passing if the XML shows `failures="0" errors="0"`.

Handler tests use `runTest { }` with `FakeCoroutineDispatcher` (= `Dispatchers.Unconfined`) so coroutines run synchronously. Call the public `handleHttpRequest()` (not the protected `handleHttpRequestAsync()`). Use `FakeHttpResponse.assertEquals()` to assert header, data, status, and filePath together.

Mapper tests are plain `kotlin.test` — no coroutines needed.

## Infrastructure

Each service has `infra/Pulumi.yaml` (Pulumi YAML program) and `infra/Pulumi.prod.yaml` (stack config for the `prod` stack). All stacks use:
- `gcp:credentials: credentials-gcp-infra.json`
- `gcp:project: personal-website-416108`
- Region: `europe-west1`
- Artifact Registry repository: `personal-website`

`proxy-web` reads other services' Cloud Run URIs via Pulumi StackReferences and passes them as Docker build args so nginx can proxy to them.

## CI

GitHub Actions workflows:
- `staging-check.yml` — runs on PRs: build, test, `pulumi preview` for changed services
- `deploy-check.yml` — runs on merge to `master`: build, test, `pulumi up` for changed services
- `nightly-check.yml` — runs daily: full build + `pulumi preview` for `common`

Changed services are detected by `detect-changed-services` action (looks for `infra/Pulumi.yaml` at depth 1). Tests run with `--max-workers=1` to avoid concurrent yarn cache clashes.

Required GitHub secrets: `GCP_SA_KEY_INFRA`, `PULUMI_ACCESS_TOKEN`, `MONITORING_SLACK_URL`, `CONTACT_SLACK_WEBHOOK_URL`.
