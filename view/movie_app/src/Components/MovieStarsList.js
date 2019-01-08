import React, { Component } from 'react';
import MiniThumbnail from './MiniThumbnail';
var uniqid = require('uniqid');

class MovieStarsList extends Component {
    render() {
        let actors;
        if (this.props.cast != null && this.props.cast.length > 0) {

            actors = this.props.cast.map(function (actor) {
                return <MiniThumbnail key={uniqid()} element={actor} />
            })
        } else {
            actors = <li className="list-group-item">Brak obsady</li>
        }

        return (
            <div className="MovieStarsList" style={{ "marginTop": "20px" }}>
                <div className="card" style={{ "textAlign": "left" }}>
                    <div className="card-header" style={{ "borderBottom": "0px" }}>
                        <a className="card-link" data-toggle="collapse" href="#collapseOne">
                            Obsada
                        </a>
                    </div>
                    <div id="collapseOne" className="collapse">
                        <div className="card-body" style={{ "padding": "0px" }}>
                            <ul className="list-group" >
                                {actors}
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default MovieStarsList;