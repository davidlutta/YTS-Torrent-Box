import React, {Component} from 'react';
import './styles.css';
import {Link, withRouter} from "react-router-dom";
import {faArrowAltCircleRight, faDownload, faHome, faSearch} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

class Navbar extends Component {
    render() {
        return (
            <nav className="navbar">
                <ul className="navbar-nav">
                    <Link to={'/'} className={"nav-link nav-item"}>
                        <FontAwesomeIcon className={"svgIcon"} icon={faHome} size={"lg"}  style={{color:'#6649b8'}}/>
                        <p className={"link-text"}>Home</p>
                    </Link>
                    <Link to={'/browse'} className={"nav-link nav-item"}>
                        <FontAwesomeIcon className={"svgIcon"} icon={faArrowAltCircleRight}  size={"lg"} style={{color:'#6649b8'}}/>
                        <p className={"link-text"}>Browse</p>
                    </Link>
                    <Link to={"/search"} className={"nav-link nav-item"}>
                        <FontAwesomeIcon icon={faSearch} className={"svgIcon"} size={"lg"} style={{color: '#6649b8'}}/>
                        <p className={"link-text"}>Search</p>
                    </Link>
                    <Link to={'/downloads'} className={"nav-link nav-item"}>
                        <FontAwesomeIcon className={"svgIcon"} icon={faDownload}  size={"lg"} style={{color:'#6649b8'}}/>
                        <p className={"link-text"}>Downloads</p>
                    </Link>
                </ul>
            </nav>
        );
    }
}

export default withRouter(Navbar);
