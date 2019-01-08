import React, { Component } from 'react';
import '../Styles/Nav.css';
import { Link } from "react-router-dom";

class Nav extends Component {

    render() {
        let userIcon;
        if (this.props.isAuthenticated) {
            userIcon =
                <ul className="navbar-nav flex-row ml-md-auto d-none d-md-flex">
                    <li className="nav-item dropdown">
                        <a className="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <i className="fas fa-user"></i>
                        </a>
                        <div className="dropdown-menu dropdown-menu-right text-right" aria-labelledby="navbarDropdown">
                            <span className="user-summary dropdown-item" disabled>
                                {!this.props.currentUser ? "" : this.props.currentUser.nickname}<br />
                                {!this.props.currentUser ? "" : this.props.currentUser.email}
                            </span>
                            <div className="dropdown-divider"></div>
                            <Link className="dropdown-hand dropdown-item" to={`/users/${this.props.currentUser.id}/commentedMovies`}>Profil</Link>
                            <span className="dropdown-hand dropdown-item" onClick={this.props.onLogout}>Wyloguj</span>
                        </div>
                    </li>
                </ul>
        }

        let register;
        let login;
        if (!this.props.isAuthenticated) {
            register =
                <li className="nav-item">
                    <Link className="nav-link" to='/registration'>Rejestracja</Link>
                </li>
            login =
                <li className="nav-item">
                    <Link className="nav-link" to='/login'>Logowanie</Link>
                </li>
        }

        return (
            <nav className="Nav navbar navbar-expand-lg navbar-dark bg-dark">
                <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>

                <div className="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul className="navbar-nav mr-auto">
                        <li className="nav-item">
                            <Link className="nav-link" to='/movies'>Filmy</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to='/stars'>Gwiazdy filmowe</Link>
                        </li>
                        {register}
                        {login}
                    </ul>
                    {userIcon}
                </div>
            </nav>
        );
    }
}

export default Nav;
