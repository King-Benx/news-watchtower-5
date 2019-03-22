# News WatchTower 5

[![CircleCI](https://circleci.com/gh/King-Benx/news-watchtower-5.svg?style=svg)](https://circleci.com/gh/King-Benx/news-watchtower-5) [![Maintainability](https://api.codeclimate.com/v1/badges/e87306cb313d69fde181/maintainability)](https://codeclimate.com/github/King-Benx/news-watchtower-5/maintainability) [![Test Coverage](https://api.codeclimate.com/v1/badges/e87306cb313d69fde181/test_coverage)](https://codeclimate.com/github/King-Benx/news-watchtower-5/test_coverage)

News WatchTower 5 is an application that brings you the latest news updates from various news sources. It provides you with the ability to view news updates in five locations by default Kampala, Kigali, Nairobi, NewYork, and Lagos.
In addition, one can view international headlines, news based on other locals, share and save news reports within the application and ability to operate while offline.

### [Get the app on Amazon](https://www.amazon.com/Asiimwe-Benard-News-WatchTower-5/dp/B07PWG48NX/ref=sr_1_fkmrnull_2?keywords=news+watchtower+5&qid=1553242492&s=gateway&sr=8-2-fkmrnull)
  

## Screenshots
| ![home view](https://github.com/King-Benx/news-watchtower-5/blob/ft-material-design-cleanup-164682501/screenshots/home.png) | ![dashboard view](https://github.com/King-Benx/news-watchtower-5/blob/ft-material-design-cleanup-164682501/screenshots/dashboard.png) |


| ![international view](https://github.com/King-Benx/news-watchtower-5/blob/ft-material-design-cleanup-164682501/screenshots/international.png) | ![locale view](https://github.com/King-Benx/news-watchtower-5/blob/ft-material-design-cleanup-164682501/screenshots/world.png) |


| ![detail view](https://github.com/King-Benx/news-watchtower-5/blob/ft-material-design-cleanup-164682501/screenshots/details.png) | ![share view](https://github.com/King-Benx/news-watchtower-5/blob/ft-material-design-cleanup-164682501/screenshots/share.png) |


| ![stored view](https://github.com/King-Benx/news-watchtower-5/blob/ft-material-design-cleanup-164682501/screenshots/stored.png) | ![network view](https://github.com/King-Benx/news-watchtower-5/blob/ft-material-design-cleanup-164682501/screenshots/network.png) |

## Getting Started and Installation

1. Clone this repository onto your local machine.
`git clone https://github.com/King-Benx/news-watchtower-5.git`

2. Locate the project on your machine. 

3. In Android Studio, under the file menu select open, then select an existing project.

4. Build the project.
`./gradlew build`

5. Select an emulator and run the application.

6. In addition, you can run the application using an Android device.

### Prerequisites

1. [Set up Android Studio](https://developer.android.com/studio/install) 

2. [Enable Kotlin in Android Studio](https://medium.com/@elye.project/setup-kotlin-for-android-studio-1bffdf1362e8)

3. [Run application on emulator](https://developer.android.com/studio/run/emulator)

4. [Run application on android device](https://developer.android.com/studio/run/device)


## Running the tests

1. In order to run tests from the terminal, run the following commands
`./gradlew test`

2. In order to be able to run tests using Android Studio itself, navigate to either androidTest or test folder and select a file then right click the file and select run test

NB: In order to be able to run instrumentation tests, you must have either a device or an emulator running.

### Break down into the end to end tests

androidTest folder holds instrumentation tests and these test components/views specific to Android (UI)

test folder holds the unit tests that test the logic and algorithms being used within the application. (Backend)

### Coding Styles/ Conventions
- Google Java Style
- Kotlin Style
- XML Naming Conventions

## Architecture
* MVVM

## Consumed API Endpoints

```
    https://newsapi.org/v2/everything?q={query}&apiKey={key}
```

```
    https://newsapi.org/v2/top-headlines?sources={source}&apiKey={key}
```

## Built With/ Powered by

* [RxAndroid](https://github.com/ReactiveX/RxAndroid) - Asynchronous and event-based functionality
* [RxJava](https://github.com/ReactiveX/RxJava) - Asynchronous and event-based functionality
* [Kotlin](https://kotlinlang.org/) - Programming language
* [Android](https://www.android.com/) - Operating System
* [Retrofit](https://square.github.io/retrofit/) - Networking
* [Picasso](http://square.github.io/picasso/) - Image processing
* [Room](https://developer.android.com/topic/libraries/architecture/room) - Database management

## Versioning
1.0 

## Authors
[Asiimwe Benard](https://github.com/King-Benx)


## Credits
All this has been possible thanks to the services provided by these guys.
[powered by NewsAPI.org](https://newsapi.org)
