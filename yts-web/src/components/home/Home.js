import React, {Component} from 'react';
import {getPopularMovies} from "../../store/actions/ytsServer/ytsActions";
import {connect} from "react-redux";
import "./styles.css"
import MovieCard from "../widgets/Card/MovieCard";

class Home extends Component {
    componentDidMount() {
        this.props.getPopularMovies();
    }

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
            <div className={"custom-container"}>
                <h1 className="title">Popular Movies</h1>
                <div className="wrapper">
                    {userList}
                </div>
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        movies: state.movies.movies
    };
};

function mapDispatchToProps(dispatch) {
    return {
        getPopularMovies: () => dispatch(getPopularMovies())
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Home);
