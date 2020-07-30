import React from "react";
import "./styles.css";
import {Link} from "react-router-dom";
import notFoundImage from './imgs/not-found.png';
import * as PropTypes from "prop-types";

function truncate(input) {
    if (input.length > 200)
        return input.substring(0, 100) + '...';
    else
        return input;
}

class MovieCard extends React.Component {
    constructor(props) {
        super(props);
        this.state = {loaded: false};
    }

    render() {
        let {movie} = this.props;
        return (
            <div className="card">
                <div className="card_left">
                    <img
                        style={this.state.loaded ? {} : {backgroundImage: `url(${notFoundImage})`}}
                        src={movie.large_cover_image} alt={movie.title}
                        onLoad={() => this.setState({loaded: true})}
                    />
                </div>
                <div className="card_right">
                    <h1>{movie.title ? movie.title : "¯\\_(ツ)_/¯"}</h1>
                    <div className="card_right__details">
                        <ul>
                            <li>{movie.year ? movie.year : "¯\\_(ツ)_/¯"}</li>
                            <li>{movie.runtime ? `${movie.runtime} mins` : "¯\\_(ツ)_/¯"}</li>
                            <li>{movie.genres ? movie.genres[0] : "¯\\_(ツ)_/¯"}</li>
                        </ul>
                        <p className={"rating"}>Rating: {movie.rating}<span>/10</span></p>
                        <div className="card_right__review">
                            <p> {truncate(movie.synopsis)}</p>
                        </div>
                        <div className="card_right__button">
                            <Link to={`/movie/${movie.id}`} className={"myBtn"}>More</Link>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

MovieCard.propTypes = {movie: PropTypes.any}
export default MovieCard;
