import React, {Component} from 'react';
import './styles.css';
import {getMovies} from "../../store/actions/ytsServer/ytsActions";
import {connect} from "react-redux";
import MovieCard from "../widgets/Card/MovieCard";
import InfiniteScroll from "react-infinite-scroll-component";

class BrowseMovies extends Component {

    componentDidMount() {
        this.props.getMovies(this.state.currentPage);
    }

    state = {
        currentPage: 1
    };

    fetchMovies = () => {
        let newPage = this.state.currentPage + 1;
        this.setState({
            currentPage: newPage
        });
        this.props.getMovies(newPage);
    };

    render() {
        const {movies} = this.props;
        const userList = movies.length ? (
            movies.map(movie => {
                return (
                    <div key={movie.id}>
                        <MovieCard movie={movie}/>
                    </div>
                );
            })
        ) : (
            <div>
                <h2>Loading...</h2>
            </div>
        );
        return (
            <div className={"container"}>
                <h1 className="title">Browse Movies</h1>

                <InfiniteScroll
                    next={this.fetchMovies}
                    loader={<h2>...</h2>}
                    hasMore={true}
                    dataLength={movies.length}>
                    <div className={"wrapper"}>
                        {userList}
                    </div>
                </InfiniteScroll>
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        movies: state.movies.browseMovies
    };
};

function mapDispatchToProps(dispatch) {
    return {
        getMovies: (page) => dispatch(getMovies(page))
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(BrowseMovies);
