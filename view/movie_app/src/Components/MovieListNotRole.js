import React, { Component } from 'react';
import MiniThumbnailMovieNotRole from './MiniThumbnailMovieNotRole';
var uniqid = require('uniqid');

class MovieListNotRole extends Component {
    render() {
        let movies;
        if (this.props.movies != null && this.props.movies.length > 0) {
            movies = this.props.movies.map(function (element) {
                return <MiniThumbnailMovieNotRole key={uniqid()} element={element} />
            })
        } else {
            movies = <li className="list-group-item">Brak</li>
        }
        return (
            <div className="MovieListNotRole" style={{ "marginTop": "20px" }}>
                <div className="card" style={{ "textAlign": "left" }}>
                    <div className="card-header" style={{ "borderBottom": "0px" }}>
                        <a className="card-link" data-toggle="collapse" href={"#" + "collapseOne" + this.props.num}>
                            {this.props.asWho}
                        </a>
                    </div>
                    <div id={"collapseOne" + this.props.num} className="collapse">
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

export default MovieListNotRole;