# RevolutExercise
Currency converter 


Introduction
------------

* Application to display the current currency rates for different countries.
* List all currencies you get from the endpoint (one per row). Each row has an input where
you can enter any amount of money. 
* When you tap on a currency row it should slide to
the top and it's input becomes the first responder.
* When you’re changing the amount
the app must simultaneously update the corresponding value for other currencies.


Getting Started
---------------

This project uses the Gradle build system. To build this project, use the
`gradlew build` command or use "Import Project" in Android Studio.

Screenshots
-----------
![CurrencyConverter](https://github.com/aniketmane/RevolutExercise/blob/master/RevolutExercise/screenshots/device-2020-06-17-211340.png?raw=true "Currency Converter")


Libraries Used
--------------

* [Architecture][0] - A collection of libraries that help you design robust, testable, and
  maintainable apps.
  * [LiveData][1] - Build data objects that notify views when the underlying database changes.
  * [ViewModel][2] - Store UI-related data that isn't destroyed on app rotations. Easily schedule
     asynchronous tasks for optimal execution.
  * [Navigation][3] - Android Navigation for fragment/screen navigation.android navigation 
  * [Lifecycle][4] - Android life cycle components used for Live Data, View Model.
  * [UI][5] - Details on why and how to use UI Components in your apps - together or separate
* Third party
  * [Retrofit][6] for network operation
  * [RxKotlin][7] for managing background threads with simplified code and reducing needs for callbacks
  * [Glide][8] - Glide is a fast and efficient open source media management and image loading framework for Android that wraps media decoding, memory and disk caching, and resource pooling into a simple and easy to use interface.
  
  
[0]: https://developer.android.com/jetpack/arch/
[1]: https://developer.android.com/topic/libraries/architecture/livedata
[2]: https://developer.android.com/topic/libraries/architecture/viewmodel
[3]: https://developer.android.com/guide/topics/ui
[4]: https://github.com/square/retrofit
[5]: https://github.com/ReactiveX/RxKotlin
[6]: https://developer.android.com/guide/topics/ui
[7]: https://developer.android.com/guide/topics/ui
[8]: https://github.com/bumptech/glide

Android Studio IDE setup
------------------------
For development, the latest version of Android Studio is required. The latest version can be
downloaded from [here](https://developer.android.com/studio/).

Improvements 
-----------------
* Add Test Cases
* Add Dependency Injection through Dagger or Koin or latest Hilt.

Author
------------------------
* **Aniket Mane**
  
