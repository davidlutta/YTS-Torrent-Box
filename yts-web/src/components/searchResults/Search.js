import React, {Component} from 'react';
import {searchMovie} from "../../store/actions/ytsServer/ytsActions";
import {connect} from "react-redux";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faSearch} from "@fortawesome/free-solid-svg-icons";
import MovieCard from "../widgets/Card/MovieCard";

class Search extends Component {
    state = {
        query: ''
    };
    handleChange = (e) => {
        this.setState({
            [e.target.id]: e.target.value
        });
    };
    handleSubmit = (e) => {
        e.preventDefault();
        this.props.getSearchResults(this.state.query);
    };
    renderSearchResults = () => {
        const {results} = this.props;
        if (results){
        const movieList = results.length ? (
            results.map(movie => {
                return (
                    <div key={movie.id}>
                        <MovieCard movie={movie}/>
                    </div>
                );
            })
        ) : (
            <div>
            </div>
        );
        return (
            <div className={"wrapper container"}>
                {movieList}
            </div>
        );
        }else {
            return (
                <div className={"container"}>Movie not Found</div>
            );
        }
    };

    render() {
        return (
            <div>
                <div className="wrap">
                    <form onSubmit={this.handleSubmit} className={"search"}>
                        <input type="text" className="searchTerm" placeholder="Search Movie" id={"query"}
                               onChange={this.handleChange}/>
                        <button type="submit" className="searchButton">
                            <FontAwesomeIcon icon={faSearch} size={"sm"} style={{color: '#ffffff'}}/>
                        </button>
                    </form>
                </div>
                {this.renderSearchResults()}
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        results: state.movies.results,
    };
};

function mapDispatchToProps(dispatch) {
    return {
        getSearchResults: (query) => dispatch(searchMovie(query))
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Search);
