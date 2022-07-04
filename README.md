# SpaceX Fan Application

# Project Requirements

Create an android application that;

1. Has three tabs
    - Listing all SpaceX rockets
    - Listing favorite rockets
    - Listing upcoming launches
2. First tab: SpaceX Rockets
    - List all spaceX rockets
    - Each rocket card in the list must have favorite button
    - Previous favorite state should be stored (user selected the rocket as favorite or not)
    - Each item must have a thumbnail of the rocket (if rocket has images)
    - Clicking an item will launch the rocket detail page
3. Rocket detail page: Detail of Selected Rocket
    - Show information of the rocket
    - Show rocket images in a slidable gallery
    - Show the clickable favorite button with its previous state (user selected the rocket as
    favorite or not)
4. Second tab: Favorite Rockets
    - List favorite rockets of the user
    - Each item must have unfavorite button
    - Clicking an item will launch the rocket detail page
    - This tab should be entered via Fingerprint or Firebase Email/Password Authentication
5. Third tab: Upcoming Launches
    - List upcoming launches
    - Clicking an item will open detail page of the upcoming launch
6. The application should know its state when the user pushes it at the background.
For example,
    - I opened a rocket details in the "SpaceX Fan" application
    - I received a Whatsapp message and opened Whatsapp message
    - I then returned to "SpaceX Fan", the state should be the same when I left "SpaceX Fan".
7. (BONUS) Create a cloud service on any cloud service provider (Amazon, DigitalOcean,
Google, Firebase etc.) free services provided are enough to handle. Create a primitive user login
and dummy user database on cloud. Keep favorite rockets of dummy users on the cloud, and
make appropriate changes on mobile application.

# Approach

1. This project aims to create an application that provides information about SpaceX rockets and launches using the SpaceX API and allows users to select and save favorite rockets using an account.
2. The construction of this project is basically divided into four parts. These parts are:
    - Creating the user interface,
    - Providing data from remote services,
    - Authentication using Firebase,
    - Applying a proper design pattern to be able to transfer data.
3. The user interface part consists of creating the compounds and background, aligning, spacing and coloring. The interface should provide list view and detail view of rockets and launches.
4. The rocket and launch information should be fetched from remote services by using the API provided by SpaceX. A service should be prepared to handle the communication with API using HTTP protocol.
5. Users should be able to select their favourite rockets and see them under the relative section. This requires holding the user based information on a remote service. A Firebase authentication and storage service should be implemented. When the favourites tab is selected, user should log in to see the favourite rockets.
6. The selected design pattern should satisfy the needs of the project. This part starts with creating a module that implements the collection of the functions to be used in the whole project, and continues with the addition of the necessary fragments and activities. MVVM design pattern is one of the choices
7. Tests will be made after every iteration. After the main tasks are completed, general tests will be made for a certain period of time and in case of errors, the errors will be resolved and application will be ready to release.
8. All process will be documented.
9. Projected completion time is 8 days.

# Tasks and Roadmap

Roadmap and tasks can be seen below.

## Roadmap

| Task ID | Day 1 | Day 2 | Day 3 | Day 4 | Day 5 | Day 6 | Day 7 | Day 8 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| UI_0 | X | X | X | X |  |  |  |  |
| UI_1 |  |  |  | X | X | X |  |  |
| DATA_0 |  | X | X | X |  |  |  |  |
| DATA_1 |  |  | X | X | X |  |  |  |
| AUTH_0 |  |  |  | X | X |  |  |  |
| AUTH_1 |  |  |  |  | X | X |  |  |
| PAT_0 | X | X | X | X |  |  |  |  |
| PAT_1 |  | X | X | X | X | X |  |  |
| TEST |  |  |  |  |  | X | X | X |
| DOC | X |  |  |  |  | X | X | X |

## Tasks

### UI_0

General research about what to use when building application. Necessary UI components and features will be decided and basic implementations will be done.

### UI_1

Pages, fragments, components and aligning-spacing will be completed.

### DATA_0

General research about how to get data from services. The API that is going to be used requires an HTTP service. The HTTP packages and samples will be researched and implemented in a basic state.

### DATA_1

API connections will be completed and service will be included in design pattern.

### AUTH_0

General research about Firebase Authentication. The authentication service will be completed and tested with dummy account.

### AUTH_1

Auth service will be implemented in sign-in page and the two will become a part of design pattern.

### PAT_0

General information about MVVM desing pattern will be collected. Examples and best-uses will be observed. The approach on how to connect UI with the back-end will be decided in this task. The modules and fragments will be implemented using these decisions.

### PAT_1

MVVM design pattern implementation. As the project matures, the components will be a part of this design pattern. Incoming data will be stored and provided to relative UI components.

### TEST

System tests will be completed. Bugs will be fixed and optimizations will be done in necessary areas.

### DOC

Progresses and details about project will be documented.

# Project Overview

An Android application that provides information about SpaceX rockets and launches using the SpaceX API and allows users to select and save favorite rockets using an account is developed using Kotlin.

## Flow

The Rockets fragment is loaded when the application is started. On this page, there are cards with brief information about all rockets, including the status of like. If there is a user already logged in, likes can be added or removed, otherwise, user is not are allowed to use the like feature of the app. When these cards are clicked, the detail page of that card opens. On the detail page, related information is shown, including the status of like if the user is logged-in.
When the second tab, the favorites, is clicked, if the user is authenticated, the favorite rockets of that user are listed. User can remove the likes and enter the details of these rockets or log out. If there is no authecticated user, the login page opens. In this view user can log-in if there is a account created before and can become a member by using sing-up feature if there is not.
The third tab, Upcoming Launches, lists all future launches. When the explore button on the cards is clicked, the launch detail page opens.

## Remote Resource Service Implementation

SpaceX API and Firebase is used to reach the remote resources.

### SpaceX API

Retrofit library is used to make HTTP request to the API. 

- `/rockets` endpoint is used to get the information of rockets.
- `/launches/upcoming` endpoint is used to get the information of upcoming launches.

### Firebase Authentication and Firestore

Firebase library is used to make requests to Firebase. 

- Firebase authentication is implemented
- Firestore storage is implemented.
- When a user signs in, Favorites tab and like buttons of the rocket cards will be available to user.

## MVVM Implementation

Necessary views constructed for rockets, favorites and launches.

The Rocket view model is called when the Rockets fragment is created. This view allows us to retrieve the data of all rockets via SpaceX API using the Retrofit library from the rockets repository.

Since Rockets and Favorites both use the rocket information, the Rocket View Model is used for both of those fragments. This fragments are also responsible for checking if user is available or not.

Upcoming launches fragment has its own view and view model.

## Libraries

1. hilt: Dependincy Injection
2. circleimageview: For rounded images
3. picasso: For loading images from api to imageview
4. crashlytics: For detecting crashes on app
5. retrofit: For Http requests
6. firestore: For authentication and storing data of users

# Summary

Project is built using Kotlin on Anroid Studio IDE.
