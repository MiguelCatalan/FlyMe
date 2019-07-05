# flyme
![](https://github.com/MiguelCatalan/FlyMe/blob/develop/art/flyme_icon.png?raw=true)

## 1- Code challenge
**Intro:** Create an Android app that gets a list of airline schedules and displays their origin and destination airports on a map.

**Task:** Implement an Android app with the following features. 
- Give the option to the user to select the origin and destination airport
- Fetch a list of airline schedules based on the selections above
- Display them on a list
- Show the origin and destination of the flight on a map upon selection of a schedule
- And connect them with a polyline 

## 2- Wild thoughts
+ **Strong architeture**
  - Separated into 3 layers (app, domain, data) in order to not break any dependency rule. But featured oriented in the inner packages.
  - Heavy use of SOLID concepts
  - The domain core logic is delegated to a _Resource_ where all the devil state and core business is. Better to have here and controlled than separated in multiple layer and collaborators. This is why you could appreciate the UseCases light in responsibility.
  - The domain models represent the best approach to handle the problems the app tries to fix, rather than coupling and replicate the API implementation to the app. As you can see in the transformations of the models in the resource.
  - Heavy use of rx (kotlin and android)
+ **From humans to humans**
  - Focus in code readability and maintenance rather that create the most computational optimal solution.
  - _"Write it once, use it how many time you want"_ this is the motto I tried to apply. You can see a generic-reactive implementation of the repository. Making it easier and cheap to use. With 4 lines of code, you can add a cache, a DDBB or whatever you want, only focusing on the API connection data source. If we write less, we have to maintain less and will be fewer bugs.
+ **Lots of first-timers**
  - It is the first time I use an MVVM architecture for the presentation layer. I always have made use of MVP and I wanted to test new things. Please be kind to me :)
  - Likeways with the LiveData approach
  - First time using a DI different from Dagger. I'm used to it, I can handle it and I make use of heavy stuff, but Koin was a tempting and I wanted to give it a try too.
+ **Miscellanea**
  - The data module is Android because I wanted to implement a DB, but for time issues I leave it just with an in-memory approach. Being that, the module could be just kotlin instead.
  - I considered the pagination an API implementation detail. The app does not have any paginated behavior so I decided to not make the domain and the app modules aware of the pagination of the API and encapsulated in the data source.
  - 


## 3- What could be improved?
- **Feature-Focus-Architecture:** The architecture implemented is based on positive discrimination forcing the dev not to be tempted to break CLEAN and SOLID rules. This is great but makes really hard to work in squads working at the same time in the same app. With an architecture based on a module for each vertical containing all the layers (app/domain/data) will result in a better fit for that kind of teams.

- **Remove the responsibility of thread change:** Technically the UseCase is ok to change from the current thread to a worker one, where all the logic should run asynchronously. But does not need to change it back to the UI this is a job form the one who invokes it, the UseCase should be agnostic to this. But because the flyme app only needs to invoke for the UI y let the UseCase own this responsibility in order to be pragmatic.

- **UI tests:** The project lacks presentation test. This is voluntary, IMHO the UI testing in mobile requires a huge amount of time wasted compared to the benefit they return. The UI is one of the most changing points of a view. I find myself consuming more time fixing UI test that they save me. That being said, it is important to have covered the critical paths in order not to break the business income flows. But maybe could be more interesting to have screenshot testing or test the ViewModel alone.

## 4- What is required before going to production?
In the code challenge, the point was to deliver a project ready to go live. That the focus of the solution developed, but if you truly want to upload this to the Play Store some minor task should be done before this and was out of the scope of the challenge.

#### Prepare the build
- Obfuscate and shrink the code using R8 by defining the relative rules
- Implement multiple flavors to compile each version
- Create signing configs

#### Add some data
- Implement any crash and error reporting tool
- _If you can't measure it, it does not exist_. Add a behavior tool such us Amplitude or Segment.
