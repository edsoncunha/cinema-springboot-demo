[![Continuous Build](https://github.com/edsoncunha/cinema-springboot-demo/actions/workflows/continuous-build.yml/badge.svg?branch=main)](https://github.com/edsoncunha/cinema-springboot-demo/actions/workflows/continuous-build.yml) [![codecov](https://codecov.io/gh/edsoncunha/cinema-springboot-demo/branch/main/graph/badge.svg)](https://codecov.io/gh/edsoncunha/cinema-springboot-demo)

# Cinema Spring Boot Demo
Sample Cinema Theater application enriched by [OMDB API](https://www.omdbapi.com/)


# How to run

## Requirements
* Docker compose
* An [OMDB API key](https://www.omdbapi.com/apikey.aspx)

## Steps
* Start related containers with `docker-compose up -d`
* Put your OMDB key on `OMDB_API_KEY` environment variable and run the project, e.g.:
```
OMDB_API_KEY=12345678 ./gradlew bootRun
```
* OpenAPI documentation will be available at [http://localhost:8080/docs.html](http://localhost:8080/docs.html)