import React from 'react';

import YouTube from "react-youtube";

const onReady = (event) => {
    event.target.pauseVideo();
};

function TrailerCard({videoId}) {
    const opts = {
        height: '300',
        width: 'auto',
        playerVars: {
            autoplay: 0,
        },
    };

    return (
        <YouTube videoId={videoId} opts={opts} onReady={onReady}/>
    );
}

export default TrailerCard;

