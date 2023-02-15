Applikasjonen bygger på følgende rammeverk:

* http://cxf.apache.org/docs/jax-rs.html

## Bygg & kjør

Applikasjonen er bygget, kompilert og testet med:

* AdoptOpenJDK 11.0.8 (build 11.0.8+10)
* Apache Maven 3.8.2

```bash
$ mvn clean package
$ java -jar target/myApp-*.jar
```

Kan også kjøres i docker container

```bash
$ mvn clean package
$ docker build --tag my-app .
$ docker run -p 8080:8080 my-app
```

## Dokumentasjon

Når applikasjonen er oppe og kjører er swagger dokumentasjon tilgjengelig på 

* https://(servername):8080/openapi.json (OpenAPI)
* https://(servername):8080/api-docs?url=/openapi.json (Swagger)
