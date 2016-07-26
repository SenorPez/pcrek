# pcrek [![Build Status](https://travis-ci.org/travis-ci/travis-web.svg?branch=master)](https://travis-ci.org/travis-ci/travis-web)
Kotlin port of [Project CARS Replay Enhancer][1]

## Description

My friend made [Project CARS Replay Enhancer][1] in python.  I'm porting his work because, like the Flanders, I've tried nothing and I'm all out of ideas.

I'm also learning Kotlin with JavaFX because...reasons/fun.

## Build

### Requirements
- Java 8

### Gradle
[Gradle][2] is really awesome, I love using it for lots of reasons; not the least of which is the ease of sharing projects with others with the least amount of setup effort.

Use the Gradle wrapper `./gradlew` on MacOS/Linux, or `gradlew.bat` on Windows.  This will ensure that you are using the correct version of Gradle for this project.

All project dependencies will be downloaded automatically, including the correct version of Gradle.

#### build
`./gradlew clean build`
This will compile the source, run tests/checks, and create distribution artifacts in `build/distributions`

Distribution artifacts contain a `bin` folder that includes both a shell script `./bin/pcrek` and a bat file `bin/pcrek.bat` for executing the application.  The `lib` folder will contain all required runtime jars.


#### run
`./gradlew run`

#### test
`./gradlew test`

## FAQ
*cricket noises*

[1]: https://github.com/SenorPez/project-cars-replay-enhancer
[2]: https://gradle.org
