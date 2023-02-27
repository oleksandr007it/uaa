# uaa Implementation OWN User Account and Authentication (UAA) Server with Spring Security
Provided OpenId Connect and Oauth 2

[![version](https://img.shields.io/badge/version-0.3.1-blue.svg)](https://semver.org)

### Suitable services HELM chart versions

| UAA                                                                                | Gateway                                                                                | AdmServRequest                                                                       | CryptoService                                                                                | Integration                                                                             | Declarations                                                                            | PermitDoc                                                                            |
| ---------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------ | -------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------ |
| [![helm](https://img.shields.io/badge/version-0.3.1-blue.svg)](https://semver.org) | [![version](https://img.shields.io/badge/version-0.3.2-green.svg)](https://semver.org) | [![version](https://img.shields.io/badge/version-0.2.3-9cf.svg)](https://semver.org) | [![version](https://img.shields.io/badge/version-0.1.0-brightgreen.svg)](https://semver.org) | [![version](https://img.shields.io/badge/version-0.0.1-orange.svg)](https://semver.org) | [![version](https://img.shields.io/badge/version-0.1.3-yellow.svg)](https://semver.org) | [![version](https://img.shields.io/badge/version-0.0.1-red.svg)](https://semver.org) |

## Development

To start your application in the dev profile, run:

    ./gradlew

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

### Doing API-First development using openapi-generator

[OpenAPI-Generator]() is configured for this application. You can generate API code from the `src/main/resources/swagger/api.yml` definition file by running:

```bash
./gradlew openApiGenerate
```

Then implements the generated delegate classes with `@Service` classes.

To edit the `api.yml` definition file, you can use a tool such as [Swagger-Editor](). Start a local instance of the swagger-editor using docker by running: `docker-compose -f src/main/docker/swagger-editor.yml up -d`. The editor will then be reachable at [http://localhost:7742](http://localhost:7742).

Refer to [Doing API-First development][] for more details.

## Building for production

### Packaging as jar

To build the final jar and optimize the uaa application for production, run:

    ./gradlew -Pprod clean bootJar

To ensure everything worked, run:

    java -jar build/libs/*.jar

Refer to [Using JHipster in production][] for more details.

### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

    ./gradlew -Pprod -Pwar clean bootWar

## Testing

To launch your application's tests, run:

    ./gradlew test integrationTest jacocoTestReport

For more information, refer to the [Running tests page][].

### Code quality

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

You can run a Sonar analysis with using the [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) or by using the gradle plugin.

Then, run a Sonar analysis:

```
./gradlew -Pprod clean check jacocoTestReport sonarqube
```

For more information, refer to the [Code quality page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a postgresql database in a docker container, run:

    docker-compose -f src/main/docker/postgresql.yml up -d

To stop it and remove the container, run:

    docker-compose -f src/main/docker/postgresql.yml down

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

    ./gradlew bootJar -Pprod jibDockerBuild

Then run:

    docker-compose -f src/main/docker/app.yml up -d

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.


