import {GET_POPULAR_MOVIES,GET_MOVIES, GET_MOVIE, SEARCH_MOVIES} from "../types";

const MOVIES_BASE_URL = 'https://yts.mx/api/v2/list_movies.json';
const MOVIE_BASE_URL = 'https://yts.mx/api/v2/movie_details.json';

export const getPopularMovies = () => async dispatch => {
    const res = await fetch(`${MOVIES_BASE_URL}?minimum_rating=8&limit=10&sort_by=download count`);
    const json = await res.json();
    dispatch({
        type: GET_POPULAR_MOVIES,
        payload: json.data.movies
    });
};

export const searchMovie = (query) => async dispatch => {
    const res = await fetch(`${MOVIES_BASE_URL}?query_term=${query}`);
    const json = await res.json();
    dispatch({
        type: SEARCH_MOVIES,
        payload: json.data.movies,
    });
};

export const getMovies = (page) => async dispatch => {
    let request = `https://yts.mx/api/v2/list_movies.json?&page=${page}`;
    const res = await fetch(request);
    const json = await res.json();
    dispatch({
        type: GET_MOVIES,
        payload: json.data.movies
    });
};

export const getMovie = (id) => async dispatch => {
    const res = await fetch(`${MOVIE_BASE_URL}?movie_id=${id}`);
    const json = await res.json();
    dispatch({
        type: GET_MOVIE,
        payload: json.data.movie
    });
};
