# Yts Android App
This is the Android client which enables the client to download movies and receive push notifications after the movie downloads on your download server. 

## Prerequisites
- Firebase Project with with the `express-sever` running and an android project also set up on firebase.


**Note** Follow steps 1, 2 and 3(part 1 only) to create a new android project in firebase [here](https://firebase.google.com/docs/android/setup)

## Setup
1. Open project in android studio and for it build
2. Go to the `Constants.java` file and add your deployed express api url to the `EXPRESSAPI` field.
3. Add the `google-services.json` file in the src folder of your project to allow your device to receive notifications when a movie finishes downloading.
3. Plug in your android device and run the project.
