[![Continuous Build](https://github.com/edsoncunha/cinema-springboot-demo/actions/workflows/continuous-build.yml/badge.svg)](https://github.com/edsoncunha/cinema-micronaut-kotlin-spock/actions/workflows/github-actions-ci.yml) [![codecov](https://codecov.io/gh/edsoncunha/cinema-micronaut-kotlin-spock/branch/main/graph/badge.svg)](https://codecov.io/gh/edsoncunha/cinema-springboot-demo)

# Cinema Spring Boot Demo
Sample Cinema Theater application enriched by [OMDB API](https://www.omdbapi.com/)

# How to run
* Create an [OMDB API key](https://www.omdbapi.com/apikey.aspx)
* Put your created key on `OMDB_API_KEY` environment variable and run the project, e.g.:
```
OMDB_API_KEY=12345678 ./gradlew bootRun
```
* OpenAPI documentation will be available at [http://localhost:8080/docs.html](http://localhost:8080/docs.html)