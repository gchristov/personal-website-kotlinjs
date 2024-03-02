# About

As the name suggests, `personal-website-kotlinjs` is a cutting-edge Kotlin multiplatform project, powering my [personal website](https://gchristov.com). Built with [KotlinJS](https://kotlinlang.org/docs/js-overview.html), it seamlessly bridges Kotlin and Javascript to bring a fully serverless platform, currently deployed as microservice [Docker](https://www.docker.com/) containers on [Google Cloud](https://cloud.google.com/run) using [Pulumi](https://www.pulumi.com/) infrastructure as code.

<details>
  <summary>🛠 Tech stack</summary>

- [Hexagon microservice architecture](https://en.m.wikipedia.org/wiki/Hexagonal_architecture_(software)) - implemented as `domain`, `adapter` and `service` sub-projects for each microservice
- [KotlinJS](https://kotlinlang.org/docs/js-overview.html) - NodeJS transpiling
- [Docker](https://www.docker.com/) - containerised deployment
- [Cloud Run](https://cloud.google.com/run) - serverless deployment of microservices
- [GitHub Actions](https://github.com/features/actions) - CI automation
- [Pulumi](https://www.pulumi.com/) - infrastructure as code, using [micro-stacks](https://www.pulumi.com/docs/using-pulumi/organizing-projects-stacks/#micro-stacks)
- [nginx](https://nginx.org/) - web reverse proxy
</details>

🌍 [Live demo](https://gchristov.com)