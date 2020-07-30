const functions = require('firebase-functions');
const admin = require("firebase-admin");
const express = require('express');
const cors = require('cors');
const app = express();
const helmet = require("helmet");
const postsRoute = require("./routes/posts");

app.use(cors({origin: true}));
app.use(helmet());
app.use(express.json({type: "application/json"}));
app.use('/', postsRoute);
let allowCrossDomain = function (req, res, next) {
    res.header('Access-Control-Allow-Origin', "*");
    res.header('Access-Control-Allow-Headers', "*");
    next();
};
app.use(allowCrossDomain);

exports.app = functions.https.onRequest(app);
exports.pushNotifications = functions
    .firestore
    .document("status/{movieId}")
    .onWrite(event => {
        console.log("================ PUSH NOTIFICATION STARTED ================");

        const beforeDoc = event.before.exists ? event.before.data() : null;
        const afterDoc = event.after.exists ? event.after.data() : null;
        if (parseInt(afterDoc.percentage) === 100) {
            const title = beforeDoc.title;
            const imageUrl = beforeDoc.poster;
            const payload = {
                notification: {
                    title: title,
                    body: `${title} has been downloaded`,
                    sound: 'default',
                    image: imageUrl
                }
            };
            const options = {
                priority: "high",
                timeToLive: 60 * 60 * 24
            };
            return admin.messaging().sendToTopic("pushNotifications", payload, options);
        }
    });
