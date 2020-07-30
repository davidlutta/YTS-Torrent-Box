const firebase = require("firebase-admin");
const firebaseConfig = require("./serviceAccount");
const Transmission = require('transmission');
const schedule = require('node-schedule');
require('dotenv').config();

const transmission = new Transmission({
    username: process.env.TUSERNAME,
    password: process.env.TPASSWORD
});
firebase.initializeApp({
    credential: firebase.credential.cert(firebaseConfig),
    databaseURL: process.env.DB_URL
});

const db = firebase.firestore();
const statusRef = db.collection("status");
const requestsRef = db.collection("requests");

const addTorrent = (hash, movieTitle, downloadUrl, poster) => {
    transmission.add(downloadUrl, {}, (err, res) => {
        if (err) {
            return console.log(err);
        }
        let id = res.id;
        console.log("Added torrent with ID: " + id);
        const post = {
            "hash":hash,
            "id": id,
            "title": movieTitle,
            "poster": poster,
            "progress": "Downloading",
            "percentage": "0.0",
            "timestamp": Date.now()
        };
        const now = (function () {
            const year = new Date(new Date().getFullYear().toString()).getTime();
            return function () {
                return Date.now() - year
            }
        })();
        console.log(`${new Date(now())} Added ${movieTitle} to Download Queue`);
        statusRef.doc(hash)
            .set(post)
            .then((res) => {
                requestsRef.doc(hash)
                    .delete()
                    .catch(err => console.log(err.message));
        }).catch(err => console.log(err.message));
    });
};

function deleteTorrent(id){
    transmission.remove(id, false, function(err, result){
        if (err){
            console.log(err);
        } else {
            console.log("Deleted Torrent");
            transmission.get(id, function(err, result) {
                console.log("No Torrent Found");
            });
        }
    });
}

function getTorrentDetails(id, hash) {
    transmission.get(id, function(err, result) {
        if (err) {
            throw err;
        }
        if(result.torrents.length > 0){
            const percentage = result.torrents[0].percentDone * 100;
            const name = result.torrents[0].name;
            if (percentage===100){
                console.log(`${name} Completed`);
                deleteTorrent(id);
                statusRef.doc(hash).update({
                    percentage: '100',
                    progress: 'Downloaded'
                }).then(res => console.log(`Updated: ${hash}`)).catch(err => console.error(err));
            }else {
		console.log(`Updating ${hash}`);
                statusRef.doc(hash)
                    .update({percentage: percentage.toFixed(2)})
		    .then(res => console.log(`Updated: ${hash}`))
                    .catch(err => console.error(err));
            }
        }
    });
}

    requestsRef.onSnapshot((docs) => {
        docs.docs.forEach(doc => {
            if (doc.exists) {
                let hash = doc.data().hash;
                let title = doc.data().title;
                let poster = doc.data().poster;
                let downloadUrl = doc.data().downloadUrl;
                addTorrent(hash, title, downloadUrl, poster);
            } else {
                console.log("There are no movies being requested to be downloaded");
            }
        });
    }, error => {
        console.error(error);
    });

const updateDb = () => {
    statusRef
        .where("progress", "==", "Downloading")
        .get()
        .then(snapshot => {
            snapshot.forEach(doc => {
                if (doc.exists) {
                    let id = doc.data().id;
                    let hash = doc.id;
                    getTorrentDetails(id, hash);
                }else {
                    console.error("Document Doesn't Exist");
                }
            });
        });
};

let updateScheduler = schedule.scheduleJob('*/5 * * * *', function(){
    updateDb()
    }); // Updates DB every 5 minutes
