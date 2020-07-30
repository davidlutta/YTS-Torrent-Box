import {
    GET_DOWNLOADING_MOVIES,
    POST_MOVIE,
    DELETE_MOVIE, GET_DOWNLOAD_QUEUE_MOVIES, DELETE_DOWNLOADING_MOVIE
} from "../../actions/types";

const initialState = {
    downloading: [],
    queue: [],
    message: '',
    deleteMessage: ''
};

const downloadMovieReducer = (state = initialState, action) => {
    switch (action.type) {
        case GET_DOWNLOADING_MOVIES: {
            return {
                ...state,
                downloading: action.payload
            };
        }
        case GET_DOWNLOAD_QUEUE_MOVIES: {
            return {
                ...state,
                queue: action.payload
            };
        }
        case POST_MOVIE: {
            return {
                ...state,
                message: action.message,
            };
        }
        case DELETE_MOVIE: {
            let newPosts = state.queue.filter(movie => {
                return action.id !== movie.hash
            });
            return {
                ...state,
                queue: newPosts
            };
        }

        case DELETE_DOWNLOADING_MOVIE: {
            let newPosts = state.downloading.filter(movie => {
                return action.id !== movie.hash
            });
            return {
                ...state,
                downloading: newPosts
            };
        }
        default:
            return state;
    }
};
export default downloadMovieReducer;
