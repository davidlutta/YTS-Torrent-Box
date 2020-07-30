import React from 'react';
import "./styles.css";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faTrash} from "@fortawesome/free-solid-svg-icons";

import {connect} from "react-redux";
import {store} from "react-notifications-component";
import {
    deleteDownloadQueueMovie,
    deleteDownloadQueueMovieFromProps
} from "../../../store/actions/expressServer/expressActions";


class DownloadingQueueMovieCard extends React.Component {
    removeMovie = async (movie) => {
        this.props.deleteMovie(movie.hash);
        const res = await deleteDownloadQueueMovie(movie.hash);
        if (res) {
            store.addNotification({
                title: "Delete",
                message: `${res}`,
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
                    <FontAwesomeIcon className={"fontAwesomeIcon"} onClick={() => this.removeMovie(movie)}
                                     icon={faTrash}
                                     size={"lg"}
                                     color={"#F08080"}/>
                </div>
            </div>
        );
    }
}


function mapDispatchToProps(dispatch) {
    return {
        deleteMovie: (id) => dispatch(deleteDownloadQueueMovieFromProps(id))
    }
}

export default connect(null, mapDispatchToProps)(DownloadingQueueMovieCard);
