
# TopUpMamaAssessment- Weather App

Simple Android app purely based on **MVVM** architecture recommended by **Google** for Android applications.
This App uses android architecture components from jetpack to demonstrate MVVM in action.

## Project Screens Preview

![Project Screens](https://www.linkpicture.com/q/Screen-Shot-2022-03-20-at-5.20.31-PM.jpeg)
![Project Screens](https://https://www.linkpicture.com/q/WhatsApp-Image-2022-03-28-at-1.32.26-PM.jpeg)

## Thoughts on architecture

As mentioned above, MVVM is the architecture that suits well for any kind & level of Android applications, there are a lot of support
libraries from jetpack that work well especially in the context of MVVM.

Single Activity and multiple fragments with the use of **Navigation** library makes is super easy to pass data along and do the fragment
transactions. And that is what **Google** promotes to use while developing Android Applications.


## Libraries Used

* [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android to perfrom Network Requests.
* [ViewBinding](https://developer.android.com/topic/libraries/view-binding/) - Easily write code that interacts with views.
* [LifeCycles](https://developer.android.com/topic/libraries/architecture/lifecycle) - Helps in creating a UI that is android components lifecycle aware.
* [Flow](https://developer.android.com/kotlin/flow) - can emit multiple values sequentially.
* [Navigation](https://developer.android.com/guide/navigation) - Easy to use navigation component for in-app navigation logic.
* [Room](https://developer.android.com/topic/libraries/architecture/room) - Wrapper on top of SQLite Database with compile-time checks capabilities.
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Holds asynchronous operations data and UI related data to withstand the configuration changes due to its scope.
* [Kotlin-Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - Helps in writing the code to perform asynchronous operations.
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Newly introduced library on top of Dagger two for [Depencency Inversion](https://developer.android.com/training/dependency-injection)
* [Timber](https://github.com/JakeWharton/timber) - A logger library with a small, extensible API which provides utility on top of Android's normal Log class.

## App Folder Structure

```
app/
|- data/
   |- local
   |- models/
   |   | - local
   |   | - remote
   |- remote
   |- repository
|- di/
|- ui/
|- utils/
-TopUpMama Application class
```

#### - Folder Structure Explained

1. **Data** - This folder will hold all the data related classes in it. This data can either be from a local store or from remote storage.
1. **models** - This folder will have all the Entities(local and remote) of the app as the DB models.
1. **Local** - This folder will have all the related classes for Local DB.
1. **remote** - This folder will have all the related classes for Remote data source and remote service that will fetch the data.
1. **repository** - This folder will have all the repositories of different Screens in a specific app. A class one level below the ViewModels to delegate the data fetching work.
1. **di** - This folder will have Dependency Injection related Classes.
1. **ui** - UI will hold all the UI app components like Activities and Fragments.
1. **utils** - A space for utilities that will be used by all over the application.


## Author
* Opara Kelechi
* Mobile Application Developer
