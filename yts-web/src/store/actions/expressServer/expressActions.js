import {DELETE_DOWNLOADING_MOVIE, DELETE_MOVIE, GET_DOWNLOAD_QUEUE_MOVIES, GET_DOWNLOADING_MOVIES} from "../types";
import axios from 'axios';
require('dotenv').config();
const BASE_URL = process.env.BASE_URL; // for example: https://MYSERVER.cloudfunctions.net/app/movies I can't name variables 😢
const REQUEST_BASE_URL =  process.env.REQUEST_BASE_URL; //'https://MYSERVER.cloudfunctions.net/app/requests';
export const getDownloadingMovies = () => async dispatch => {
    const res = await fetch(BASE_URL);
    const json = await res.json();
    dispatch({
        type: GET_DOWNLOADING_MOVIES,
        payload: json
    })
};

export const getDownloadQueueMovies = () => async dispatch => {
    const res = await fetch(REQUEST_BASE_URL);
    const json = await res.json();
    dispatch({
        type: GET_DOWNLOAD_QUEUE_MOVIES,
        payload: json
    });
};
export const postMovie = async (torrent, movie) => {

    const res = await axios.post(BASE_URL, {
        id: torrent.hash,
        downloadUrl: torrent.url,
        hash: torrent.hash,
        poster: movie.medium_cover_image,
        title: `${movie.title} [${torrent.type}, ${torrent.quality}]`
    });
    return res.data;
};

export const deleteDownloadingMovie = async (id) => {
    const res = await axios.delete(`${BASE_URL}/${id}`);
    return res.data.message;
};

export const deleteDownloadingMovieFromProps = id => {
    return {
        type: DELETE_DOWNLOADING_MOVIE,
        id: id
    };
};

export const deleteDownloadQueueMovie = async (id) => {
    const res = await axios.delete(`${REQUEST_BASE_URL}/${id}`);
    return res.data.message;
};

export const deleteDownloadQueueMovieFromProps = (id) => {
    return {
        type: DELETE_MOVIE,
        id: id
    };
};
