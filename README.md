# data-beetle
Data Beetle - Project for gathering political information from several sources

[![Build Status](https://travis-ci.org/jresendiz27/data-beetle.svg?branch=master)](https://travis-ci.org/jresendiz27/data-beetle)


# Project requirements:

* JDK 8 or higher.
* Docker 1.8 or higher.
* Docker Compose 1.16 or higher.

# Compile the project

Use the gradle wrapper inside the repository. Consider the next command.

```bash
./gradlew clean
./gradlew compile
./gradlew tasks
```

The `./gradlew tasks` command, shows all the task related with the project.

# Run the project

Step 0: Execute the next commands
```bash
mkdir volumes
```
> Special Step for ElasticSearch: `cd volumes && mkdir elasticsearch && sudo chown 1000:0 elasticsearch`

Run the `docker compose file` inside `src/main/docker` using the next command.

`docker-compose -f src/main/docker/docker-compose.yml`

> ProTip: Create a folder named `volumes`. This will share all the resources
from the docker instances to your local drive, avoiding to reset and load all
the information each time you run the project. The folder will be ignored by git.

