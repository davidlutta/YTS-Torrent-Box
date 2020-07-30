import React from 'react';
import "./styles.css";
import { store } from 'react-notifications-component';
import * as PropTypes from "prop-types";
import {postMovie} from "../../../store/actions/expressServer/expressActions";
import {connect} from "react-redux";



class DownloadButton extends React.Component {

    sendToServer = async (torrent, movie) => {
        const message = await postMovie(torrent, movie);
        if (message) {
            store.addNotification({
                title: "Success",
                message: "Success Uploading Movie",
                type: "success",
                insert: "top",
                container: "top-right",
                dismiss: {
                    duration: 2000,
                }
            });
        } else {
            store.addNotification({
                title: "Error",
                message: "Failed to Upload Movie",
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
        let {torrent, movie} = this.props;
        return (
            <div className={"button button-glow button-rounded"} onClick={() => this.sendToServer(torrent, movie)}>
                {torrent.type}
                <p>{torrent.quality}</p>
            </div>
        );
    }
}

DownloadButton.propTypes = {
    torrent: PropTypes.any,
    movie: PropTypes.any
};
const mapStateToProps = (state) => {
    return {
        message: state.downloadMovie.message
    };
};
function mapDispatchToProps(dispatch) {
    return {
        postMovie: (torrent, movie) => dispatch(postMovie(torrent, movie))
    };
}
export default connect(mapStateToProps, mapDispatchToProps)(DownloadButton);
