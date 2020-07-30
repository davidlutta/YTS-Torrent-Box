import {
    GET_MOVIES,
    GET_MOVIE,
    SEARCH_MOVIES, GET_POPULAR_MOVIES
} from "../../actions/types";

const initialState = {
    browseMovies:[],
    movies: [],
    movie: {},
    results: [],
};

const MovieReducer = (state = initialState, action) => {
    switch (action.type) {
        case GET_POPULAR_MOVIES: {
            return {
                ...state,
                movies: action.payload
            };
        }
        case SEARCH_MOVIES: {
            return {
                ...state,
                results: action.payload
            };
        }
        case GET_MOVIES: {
            let {browseMovies} = state;
            browseMovies = [...browseMovies, ...action.payload];
            return {
                ...state,
                browseMovies: browseMovies,
            };
        }
        case GET_MOVIE: {
            return {
                ...state,
                movie: action.payload
            };
        }
        default:
            return state;
    }
};
export default MovieReducer;
