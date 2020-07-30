import React from 'react';
import './App.css';
import {BrowserRouter, Route, Switch} from "react-router-dom";
import Navbar from "./components/navbar/Navbar";
import Home from "./components/home/Home";
import BrowseMovies from "./components/browseMovies/BrowseMovies";
import Download from "./components/download/Download";
import Movie from "./components/movie/Movie";
import Search from "./components/searchResults/Search";
import ReactNotification from 'react-notifications-component'
import 'react-notifications-component/dist/theme.css'

function App() {
    return (
        <BrowserRouter>
            <Navbar/>
            <main>
                <div className={"container"}>
                    <ReactNotification/>
                    <Switch>
                        <Route exact path={'/'} component={Home}/>
                        <Route exact path={'/movie/:id'} component={Movie}/>
                        <Route exact path={'/search'} component={Search}/>
                        <Route exact path={'/browse'} component={BrowseMovies}/>
                        <Route exact path={'/downloads'} component={Download}/>
                    </Switch>
                </div>
            </main>
        </BrowserRouter>
    );
}
export default App;
