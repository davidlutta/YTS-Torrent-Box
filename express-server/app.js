const express = require("express");
const app = express();

app.use(express.json({type: "application/json"}));
// import routes
const postsRoute = require("./functions/routes/posts");
app.use('/', postsRoute);

const now = (function () {
    const year = new Date(new Date().getFullYear().toString()).getTime();
    return function () {
        return Date.now() - year
    }
})();


