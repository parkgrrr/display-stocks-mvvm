# Parker Stevens
![Screenshot_1633469368](https://user-images.githubusercontent.com/3935227/136105360-f83ea005-60ad-4535-8096-9eaf25f34da6.png)

## Android Tools & Versions Used
Android Studio Artic Fox 2020.3.1, targeting API 30, Kotlin 1.5.31. 

To test the app, you can run it to see a successful response. To change it to and empty or malformed state, you can change api.getPortfolio() to use the other two api methods in MainVM.kt line 24. In a production app I would remove these methods, but wanted to leave them in for review and manual testing.

## Focus Areas
* My biggest focus was having clean, testable architecture and data flow throughout the app
* I focused on technologies and patterns I am comfortable with

## App Architecure
* I built the app using MVVM and a state machine to represent the view in the view model. The state machine LiveData is exposed as immutable to the view, so it is able to consume the state but not write it.
* Android ViewModel Architecure Component with no references to Android framework classes for Unit Testing. This allows view logic to be tested without running a Roboelectric or Espresso test
* LiveData for view states and View -> View Model communication. A sealed class was used to represent different view states, as well as bundle the response data for the recycler view to display
* Retrofit and OKHTTP for networking, with a StockService singleton used to create the network implementation
* Rx for network call threading and error handling
* BaseActivity and BaseVM for code extension and scaling. I am creating the netork service and handle much of the view binding and view model relationship in the BaseVM
* Mockk for testing VM, with a Mock API and validation of view states
* I kept the UI fairly simple as I don't know what to expect for the on site. Otherwise I would have added a refresh button, caching, and buttons to for the reviewer to test the malformed and empty API responses.

## Tradeoffs
* I chose RxKotlin over coroutines since I am a little more comfortable using it for networking
* I considered using an RX PublishSubject instead of LiveData in the view model, but for now the states are simple enough it isn't needed. If the data required more frequent or constant updates, it could be an issue since LiveData will drop and older event if it isn't posted to the main thread before and never state is created. with an RX PublishSubject I could have used a Flowable, which handles back pressure and is better for large streams of data.
* To save a little bit of time I did not use a service locator such as Koin, of dependancy injection with Dagger. In a production app I would have taken this route to better manage the scope and configuration of singletons, such as the network service, loggers, analytics, databases, etc. It would also help scale testing being able to inject mock classes into different test scenarios.
* In my testing, I do not have the code to be very scaleable or reusable, I would improve upon this if I gave myself more time.
* In the view I used a view switcher to swap out the recycler view and error/empty UI states. This may not be very scalable as view switcher allows only two views. I would have added a snack bar for more robust error handling in the future, as well as a more flexible UI experience.
* I did not cache the network response or stock list in the viewmodel, per recomendation by my recruiter. This means upon rotation and other actions that trigger activity lifecycle methods, the network call is made again. In a production app I would add a Repository to manage cache logic, or at least would have had an in memory cache variable in the ViewModel.
* I am not using a diff util in the recyclerview. In a production app and a very large list, it would be more effecient to implement the diff util and only update neccesary view positions for a better UI experience

## 3rd Party Libraries
* RxKotlin, Retrofit, view model / live data KTX, GSON, OKHTTP, MockK, android arch testingfor LiveData and ViewModel testing

## Time Spent and Summary
I spent around 4 hours on this project, the bulk of it spent on the app architecure, and testing. Since I am not using Dagger, I spent a little more time making sure I am properly mocking the api layer for my viewmodel. 

I have also been coding in Flutter the past year, and have been on the Core android team as an IC and manager before that, so I enjoyed writing UI in Android. I also spent time reading through some of the docs for the View Binding lib, vs Kotlin Synthetic view which I am used to.

I feel my strengths are in creating clean architecure, and a good seperation of concerns. I am also passionate about art, design, and enjoy creating animations, but I haven't had as much time as SoFi to do that as I would like. I have enjoyed learning a declaritive UI framework with Flutter, as well as designing our Repos and code structure to be shareble between multiple projects.
