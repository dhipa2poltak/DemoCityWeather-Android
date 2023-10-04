# DemoCityWeather-Android

**Screenshots:**
| <img src="docs/screenshots/list_city_weather.jpg"> | <img src="docs/screenshots/add_city.jpg"> | <img src="docs/screenshots/forecast.jpg"> |
| -------------------------------------------------- | ----------------------------------------- | ----------------------------------------- |

**APK file:**
**[Demo City Weather v1.1.apk](https://drive.google.com/file/d/1RupdqjGZJSZilgsYdQ2k4vvfkOA7L5l7/view?usp=sharing)**

**Technology Stack:**
- Kotlin Programming Language
- Clean Architecture
- MVVM Architecture Pattern
- Hilt Dependency Injection
- Navigation Graph
- View Binding
- ViewModel
- LiveData
- ROOM Database
- Retrofit REST + OkHttp
- Coroutine
- RxKotlin
- GSON Serialization
- Lottie
- Gradle build flavors
- Proguard
- Git

**To run the project in DEBUG MODE:**

Get the api key from [openweathermap.org](https://openweathermap.org/).

Create file namely “key.properties” in the root project with following contents:

storePassword=<your_keystore_password> <br />
keyPassword=<your_key_password> <br />
keyAlias=<your_key_alias> <br />
storeFile=<path_to_keystore_file> <br />
apiKey=<your_api_key> <br />

once you have created it, open the project with Android Studio, build the project and run.
