# Express Server
This is the heart of the project. It is the component that is an intermediary between the database and the web and mobile client.

# Prerequisites 
- A Firebase Project with _firestore db_ and _cloud functions_ enabled you can set up one [here](https://firebase.google.com/).

# Setup
1. Navigate to the `functions` folder.
2. Run `npm install` to install the required packages.
3. Insert your `serviceAccount.json` from firebase inside the **functions** folder.
4. Add a `.env` file then add `DB_URL` followed with your firestore url.
5. Run `npm run deploy` in the terminal to deploy your project to firebase. If you are not sure on the steps use this [guide](https://medium.com/@sandun.isuru/how-to-deploy-nodejs-express-app-to-firebase-as-function-31515c304e70)

**NOTE**: After deploying you will get a url which looks something like `https://FIREBASE_APP_NAME.cloudfunctions.net/app/` this is going to be the primary api url for the web and mobile client

# Api Endpoints
- `/movies` which deals with the movies in the download queue it has the following methods: GET, POST & DELETE.
- `/requests` which deals with movies that are in the queue but have not yet been sent to the download server it has the following methods: GET, POST & DELETE.
