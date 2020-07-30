import {combineReducers} from "redux";
import MovieReducer from "./ytsServer/movieReducer";
import downloadMovieReducer from "./expressServer/movieReducer";

const rootReducer = combineReducers({
    movies: MovieReducer, // YTS Server
    downloadMovie: downloadMovieReducer // Express Server
});
export default rootReducer;
