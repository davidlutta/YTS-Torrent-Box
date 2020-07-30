import React, {Component} from 'react';
import "./styles.css";
import {getDownloadingMovies, getDownloadQueueMovies} from "../../store/actions/expressServer/expressActions";
import {connect} from "react-redux";
import DownloadingQueueMovieCard from "../widgets/downloadingMovieCard/downloadingQueueMovieCard";
import DownloadingMovieCard from "../widgets/downloadingMovieCard/downloadingMovieCard";


class Download extends Component {
    componentDidMount() {
        this.props.getDownloadingMovies();
        this.props.getDownloadQueueMovies();
    }

    renderDownloadingMovies = () => {
        const {downloadingMovies} = this.props;
        if (downloadingMovies) {
            const downloadList = downloadingMovies.length ? (
                downloadingMovies.map(movie => {
                    return (
                        <div key={movie.hash}>
                            <DownloadingMovieCard movie={movie}/>
                        </div>
                    );
                })
            ) : (
                <div></div>
            );
            return (
                <div>
                    {downloadList}
                </div>
            );
        } else {
            return (
                <div>
                    No Movies downloading
                </div>
            );
        }
    };


    renderDownloadingQueueMovies = () => {
        const {downloadQueue} = this.props;
        if (downloadQueue) {
            const downloadList = downloadQueue.length ? (
                downloadQueue.map(movie => {
                    return (
                        <div key={movie.hash}>
                            <DownloadingQueueMovieCard movie={movie}/>
                        </div>
                    );
                })
            ) : (
                <div>No Movies downloading in Downloading Queue</div>
            );
            return (
                <div>
                    {downloadList}
                </div>
            );
        } else {
            return (
                <div>
                    No Movies downloading in Downloading Queue
                </div>
            );
        }
    };

    render() {
        return (
            <div className={"container"}>
                <div className={"flexRow"}>
                    <div className="downloading">
                        <h1 className="sub-title">Downloading</h1>
                        {this.renderDownloadingMovies()}
                    </div>
                    <div className="requests">
                        <h1 className="sub-title">Queue</h1>
                        {this.renderDownloadingQueueMovies()}
                    </div>
                </div>
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return ({
        downloadingMovies: state.downloadMovie.downloading,
        downloadQueue: state.downloadMovie.queue
    });
};

function mapDispatchToProps(dispatch) {
    return {
        getDownloadingMovies: () => dispatch(getDownloadingMovies()),
        getDownloadQueueMovies: () => dispatch(getDownloadQueueMovies())
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(Download);
