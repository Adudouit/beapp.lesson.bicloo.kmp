# Beapp Lesson: Bicloo app

This project provides a base for the KMP Beapp Lessons in the form of a fill-in-the-gaps exercise.
The formation provides key elements allowing the trainee to complete the application.

## The application purpose

This application allows any user to search for a city, and display all its available shared bikes stations on the map. 

## Road map

### 1. Setup
1. Clone android the project
2. Run the app
3. Download the "Kotlin Multiplatform Mobile" plugin from Android Studio
4. generate a shared kotlin module

### 2. Integrate the shared module
Create the following files :
1. Clone the project the ios project.
2. generate the xcframework & import it into the iOS project
3. Retrieve the example String from the KMP module and display it
5. Do the same for the Android project

### 3. Network
1. Integrate the Ktor library
2. Configure it
3. Retrieve the cities list from the shared module

### 4. Save the data
1. Save the api response to the file system using the Actual/Expect system
2. return the cached datas after it is saved (ie through a CacheManager)
3. Let the application decide if it wants the cached data or 


### 5. To go further 
If you finish too quickly, first : good job !     
Then, here are some features to explore KMP development some more :
- take all 3 APIs endpoint into the KMP module & delete all API related data from the natives project
- export the kdoc for Kotlin/Native
- create some documentation and generate with the help of the Dokka library