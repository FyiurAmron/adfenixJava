Setup & common tasks
--------------------

Project requires Gradle >=7.4 & JDK/JRE >= 17, and provides Gradle wrapper.

Running locally:

    ./gradlew bootRun

Building the artifact JAR:

    ./gradle bootJar

Running tests:

    ./gradlew test

Auto-dockerizing:

    ./gradlew bootBuildImage

... and then running from image via e.g. (creates and runs a throw-away container)

    docker run --tty --rm -p 8080:8080 vaxquis/adfenixJavaTask:0.1.0-SNAPSHOT

Common URLs
-----------

* healthcheck/heartbeat @ http://localhost:8080/actuator/health
* Swagger API docs @ http://localhost:8080/swagger-ui.html
* OpenAPI v3 @ http://localhost:8080/openapi-docs
