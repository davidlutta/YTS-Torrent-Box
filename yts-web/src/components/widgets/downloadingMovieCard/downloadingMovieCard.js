import React from 'react';
import "./styles.css";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faTrash} from "@fortawesome/free-solid-svg-icons";
import * as PropTypes from "prop-types";

import {connect} from "react-redux";
import {store} from "react-notifications-component";
import {
    deleteDownloadingMovie,
    deleteDownloadingMovieFromProps
} from "../../../store/actions/expressServer/expressActions";


class DownloadingMovieCard extends React.Component {
    removeMovie = async (movie) => {
        this.props.deleteMovie(movie.hash);
        const message = await deleteDownloadingMovie(movie.hash);
        if (message) {
            store.addNotification({
                title: "Delete",
                message: `${message}`,
                type: "danger",
                insert: "top",
                container: "top-right",
                dismiss: {
                    duration: 2000,
                }
            });
        }
    };

    render() {
        let {movie} = this.props;
        return (
            <div className={"row"}>
                <div className="movieCardPoster">
                    <img src={movie.poster} alt={movie.title}/>
                </div>
                <div className="movieCardDetails">
                    <h1 className={"movieTitle"}>{movie.title}</h1>
                    <p>{movie.percentage}% Downloaded</p>
                    <FontAwesomeIcon className={"fontAwesomeIcon"} onClick={() => this.removeMovie(movie)}
                                     icon={faTrash}
                                     size={"lg"} color={"#F08080"}/>
                </div>
            </div>
        );
    }
}

DownloadingMovieCard.propTypes = {movie: PropTypes.any};

function mapDispatchToProps(dispatch) {
    return {
        deleteMovie: (id) => dispatch(deleteDownloadingMovieFromProps(id))
    }
}

export default connect(null, mapDispatchToProps)(DownloadingMovieCard);
