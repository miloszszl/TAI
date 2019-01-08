import React, { Component } from 'react';
import MiniThumbnailMovie from './MiniThumbnailMovie';
var uniqid = require('uniqid');

class MovieList extends Component {
    render() {
        let movies;
        if (this.props.movies != null && this.props.movies.length > 0) {
            movies = this.props.movies.map(function (movie) {
                return <MiniThumbnailMovie key={uniqid()} element={movie}/>
            })
        } else {
            movies = <li className="list-group-item">Brak ról</li>
        }
        return (
            <div className="MovieList" style={{ "marginTop": "20px" }}>
                <div className="card" style={{ "textAlign": "left" }}>
                    <div className="card-header" style={{ "borderBottom": "0px" }}>
                        <a className="card-link" data-toggle="collapse" href="#collapseOne">
                            Role
                        </a>
                    </div>
                    <div id="collapseOne" className="collapse">
                        <div className="card-body" style={{ "padding": "0px" }}>
                            <ul className="list-group" >
                                {movies}
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default MovieList;