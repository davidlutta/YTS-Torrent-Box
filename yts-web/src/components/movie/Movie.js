import React, {Component} from 'react';
import "./styles.css";
import {getMovie} from "../../store/actions/ytsServer/ytsActions";
import {connect} from "react-redux";
import TrailerCard from "../widgets/trailers/TrailerCard";
import DownloadButton from "../widgets/downloadButton/DownloadButton";
import notFoundImage from "../widgets/Card/imgs/not-found.png";


class Movie extends Component {
    constructor(props) {
        super(props);
        this.state = {loaded: false};
    }

    componentDidMount() {
        let id = this.props.match.params.id;
        this.props.getMovie(id);
    }

    render() {
        const {movie} = this.props;
        try {
            const downloadButton = movie.torrents.map(torrent => {
                return (
                    <div key={torrent.hash}>
                        <DownloadButton torrent={torrent} movie={movie}/>
                    </div>
                );
            });
            const genres = movie.genres.join(",");

            return (
                <div className="container">
                    <div className="flexRow">
                        <div className="poster">
                            <img
                                style={this.state.loaded ? {} : {backgroundImage: `url(${notFoundImage})`}}
                                src={movie.large_cover_image} alt={movie.title}
                                onLoad={() => this.setState({loaded: true})}
                            />
                        </div>
                        <div className="details">
                            <h1>{movie.title ? movie.title : "¯\\_(ツ)_/¯"}</h1>
                            <p>Year: <span className={"year"}>{movie.year ? movie.year : "¯\\_(ツ)_/¯"}</span></p>
                            <p>Rating: <span
                                className={"rating"}>{movie.rating ? movie.rating : "¯\\_(ツ)_/¯"}</span><span
                                className={"purpleBold"}>/10</span></p>
                            <p>Genres: {genres ? genres : "¯\\_(ツ)_/¯"}</p>
                            <p>Runtime: <span
                                className={"purpleBold"}>{movie.runtime ? `${movie.runtime} mins` : "¯\\_(ツ)_/¯"}</span>
                            </p>
                            <p>Language: {movie.language ? movie.language : "¯\\_(ツ)_/¯"}</p>
                            <h4>Description:</h4>
                            <hr/>
                            <p>{movie.description_full ? movie.description_full : "¯\\_(ツ)_/¯"}</p>
                            <h4>Download:</h4>
                            <div className="flexRow">
                                {downloadButton}
                            </div>
                        </div>
                    </div>
                    <div className="blockRow">
                        <h2>Trailer:</h2>
                        <TrailerCard videoId={movie.yt_trailer_code}/>
                    </div>
                </div>
            );
        } catch (e) {
            console.error(e.message);
            return (
                <div>
                    Loading...
                </div>
            );
        }
    }
}

const mapStateToProps = (state) => {
    return {
        movie: state.movies.movie
    };
};

function mapDispatchToProps(dispatch) {
    return {
        getMovie: (id) => dispatch(getMovie(id))
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Movie);
