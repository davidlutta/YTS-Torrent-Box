const express = require('express');
const router = express.Router();
const firebase = require('firebase-admin');
const firebaseConfig = require('../serviceAccount');
require('dotenv').config();
firebase.initializeApp({
    credential: firebase.credential.cert(firebaseConfig),
    databaseURL: process.env.DB_URL
});

const db = firebase.firestore();

const REQUEST_COLLECTION = "requests";
const STATUS_COLLECTION = "status";

// Posts movies to requests collection
router.post("/movies", (req, res) => {
    const hash = req.body.id.toString();
    const downloadUrl = req.body.downloadUrl.toString();
    const title = req.body.title.toString();
    const poster = req.body.poster.toString();
    const post = {
        "hash": hash,
        "title":title,
        "poster":poster,
        "downloadUrl": downloadUrl,
        "timestamp": Date.now()
    };
    db.collection(REQUEST_COLLECTION).doc(hash).set(post).then((result) => {
        res.status(201);
        res.json({"message":"Saved Movie"});
    }).catch(err => {
        res.status(500);
        res.json({"message":err.message});
    });
});

// Returns Movies in Download Queue
router.get("/movies",async (req, res) => {
    try {
        const movies = [];
        await db.collection(STATUS_COLLECTION).orderBy("timestamp").get().then((docs) => {
            docs.docs.forEach(doc => {
                if (doc.exists) {
                    movies.push(doc.data());
                }
            });
                if (movies.length>0) {
                    res.status(200);
                    res.json(movies);
                } else {
                    return res.status(204).json({"message": "No Movies Were Found"});
                }

        }).catch(err => {
            return res.status(400).json({"message": err.message});
        });
    }catch (e) {
        res.json({message: e.message});
    }
});

router.delete("/movies/:uid", (req, res) => {
    const id = req.params.uid;
    db.collection(STATUS_COLLECTION).doc(id).get().then(doc => {
        if (doc.exists) {
            const progress = doc.data().progress;
            if (progress === "Downloaded") {
                db.collection(STATUS_COLLECTION).doc(id).delete().then(result => {
                    res.status(200);
                    res.json({"message": "Deleted Movie"});
                }).catch(err => {
                    res.status(400);
                    res.json({"message": err.message});
                });
            } else {
                res.status(200).json({"message": "You can't delete a movie that is downloading"});
            }
        } else {
            res.status(404).json({"message": "Movie Doesn't Exist"});
        }
    });
});

// Returns movies in the download Queue
router.get("/requests", async (req, res)=>{
    try {
        const movies = [];
        await db.collection(REQUEST_COLLECTION).orderBy("timestamp").get().then((docs=>{
            docs.docs.forEach(doc =>{
                if(doc.exists){
                    movies.push(doc.data());
                }});
                if(movies.length>0){
                    res.status(200);
                    res.json(movies);
                }else{
                    return res.status(204).json({"message":"No Movies in Download Queue"});
                }
        })).catch(err=>{
            return res.status(400).json({"message":err.message});
        });
    } catch (e) {
        res.json({message: e.message});
    }
});

router.delete("/requests/:uid", (req, res) => {
    const id = req.params.uid;
    db.collection(REQUEST_COLLECTION).doc(id).get().then(doc => {
        if (doc.exists) {
                db.collection(REQUEST_COLLECTION).doc(id).delete().then(result => {
                    res.status(200);
                    res.json({"message": "Deleted Movie"});
                }).catch(err => {
                    res.status(400);
                    res.json({"message": err.message});
                });
            
        } else {
            res.status(400).json({"message": "Movie Doesn't Exist"}); 
        }
    });
});


module.exports = router;
