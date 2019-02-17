# News WatchTower 5

[![CircleCI](https://circleci.com/gh/King-Benx/android-level-up/tree/develop.svg?style=svg)](https://circleci.com/gh/King-Benx/android-level-up/tree/develop)

News WatchTower 5 is an application that brings you the latest news updates from various news sources. It provides you with the ability to view news updates in five locations by default Kampala, Kigali, Nairobi, NewYork and Lagos.
In addition, one can view international headlines, local news, share and save news reports within the application.


## Screenshots
| ![landing view](https://github.com/King-Benx/news-watchtower-5/blob/ch-improve-readme.md-164026904/screenshots/landing.png)| ![detail view](https://github.com/King-Benx/news-watchtower-5/blob/ch-improve-readme.md-164026904/screenshots/detail.png) |


| ![navigation view](https://github.com/King-Benx/news-watchtower-5/blob/ch-improve-readme.md-164026904/screenshots/navigation.png) | ![share view](https://github.com/King-Benx/news-watchtower-5/blob/ch-improve-readme.md-164026904/screenshots/share.png) |

## Getting Started and Installation

1. Clone this repository onto your local machine.
`git clone https://github.com/King-Benx/news-watchtower-5.git`

2. Locate the project on your machine. 

3. In Android Studio, under the file menu select open and select existing project.

4. Build the project.
`./gradlew build`

5. Select an emulator and run the application.

6. In addition, you can run the application using an android device.

### Prerequisites

1. [Set up Android Studio](https://developer.android.com/studio/install) 

2. [Enable Kotlin in Android Studio](https://medium.com/@elye.project/setup-kotlin-for-android-studio-1bffdf1362e8)

3. [Run application on emulator](https://developer.android.com/studio/run/emulator)

4. [Run application on android device](https://developer.android.com/studio/run/device)


## Running the tests

In order to run tests from the terminal, run the following commands
`./gradlew test`

In order to be able to run tests using Android Studio itself, navigate to either androidTest or test folder and select a file then right click the file and select run test

NB: In order to be able to run instrumentation tests, you must have either a device or an emulator running.

### Break down into end to end tests

androidTest folder holds instrumentation tests and these test components/views specific to Android (UI)

test folder holds the unit tests that test the logic and algorithms being used within the application. (Backend)

### Coding Styles/ Conventions
- Google Java Style
- Kotlin Style
- XML Naming Conventions


## Built With/ Powered by

* [Kotlin](https://kotlinlang.org/) - Programming language
* [Android](https://www.android.com/) - Operating System
* [Dagger 2](https://google.github.io/dagger/) - Dependency Injection
* [Retrofit](https://square.github.io/retrofit/) - Networking
* [Picasso](http://square.github.io/picasso/) - Image processing
* [Room](https://developer.android.com/topic/libraries/architecture/room) - Database management
* [Data Binding Library](https://developer.android.com/topic/libraries/data-binding/) - Binding UI components

## Versioning
1.0 

## Authors
[Asiimwe Benard](https://github.com/King-Benx)


## Credits
All this has been possible thanks to the services provided by these guys.
[powered by NewsAPI.org](https://newsapi.org)
