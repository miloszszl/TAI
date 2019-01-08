import React, { Component } from 'react';
import { Link } from "react-router-dom";
var uniqid = require('uniqid');

class MovieShortDetails extends Component {
    render() {
        let scenarists;
        if (this.props.scenarists && this.props.scenarists.length > 0) {
            scenarists = this.props.scenarists.map(scenarist => {
                return <Link className="card-link" key={uniqid()} to={`/stars/${scenarist.id}`}>{scenarist.fullName} </Link>
            }).reduce((prev, curr) => [prev, ', ', curr])
        } else {
            scenarists = "brak";
        }

        let directors;
        if (this.props.directors && this.props.directors.length > 0) {
            directors = this.props.directors.map(director => {
                return <Link className="card-link" key={uniqid()} to={`/stars/${director.id}`}>{director.fullName} </Link>
            }).reduce((prev, curr) => [prev, ', ', curr])
        } else {
            directors = "brak";
        }

        return (
            <div className="MovieShortDetails col-md-7" style={{ "textAlign": "left" }}>
                <strong>Czas trwania: </strong> {this.props.time} min<br />
                <strong>Data premiery(Polska): </strong> {this.props.poland_premiere} <br />
                <strong>Data premiery(Świat): </strong> {this.props.world_premiere}<br />
                <strong>Budżet: </strong> {this.props.boxoffice} $<br />
                <strong>Scenażyści: </strong>{scenarists}<br />
                <strong>Reżyserzy: </strong>{directors}
            </div>
        );
    }
}

export default MovieShortDetails;