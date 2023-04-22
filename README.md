# Republic Services Take Home Challenge:

## My Solution

### How to build

1. Clone the repo
1. To build and install to device run `./gradlew installDebug`

### Architecture overview

- Hilt (dependency injection)
- Room (local database)
- OkHttp (network requests)
- Moshi (JSON deserialization)
- ConstraintLayout,RecyclerView,Material (UI)

The app uses MVVM architecture. There is a single activity with multiple fragments. ViewModels access the data layer through the DriverRepo, which manages remote and local data sources.

### Things I would improve

1. Add unit tests for data layer business logic.
1. Handle IO exceptions for network and disk. I've mostly ignored possible exceptions and focussed only on the happy path.
1. Maybe use Jetpack Compose instead of XML layouts.

## Challenge Details

Your task is to build an Android application taking advantage of the clean architecture principles and best practices. You can use any of the Jetpack libraries but use other third-party libraries only if necessary. 

REST API endpoint: https://d49c3a78-a4f2-437d-bf72-569334dea17c.mock.pstmn.io/data

General Requirements:
Call the remote API endpoint mentioned above.
Save driver and routes data in the local database.
Package name should include your last name (ie com.smith)

Main Screen:
Display the list of drivers from the local database. 
Have a button in the top right corner which sorts the drivers based on their last name.
When the user selects one driver, the Route screen is presented, and her/his route/routes is displayed based on the following algorithm (business logic):
If the driver id is the same as the route id then display the route.
If the driver id is divisible by 2 then show the first R type route.
If the driver id is divisible by 5 then display the second C type route.
If the driver id doesn’t meet any of the conditions above, then show the last I type route.

Routes Screen: 
Displays the route information (type and name of route) for the selected driver based on the business logic above.

Send us: 
Full source code (GitHub or similar repository)
Instructions to build the app if necessary.

Evaluation:
SOLID architecture principles
Code structure and readability
Thinking pattern and best approach to solve the problem