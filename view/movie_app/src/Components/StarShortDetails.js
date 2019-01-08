import React, { Component } from 'react';

class StarShortDetails extends Component {
    render() {
        return (
            <div className="StarShortDetails col-md-7" style={{ "textAlign": "left" }}>
                <strong>Imię: </strong> {this.props.name}<br />
                <strong>Nazwisko: </strong> {this.props.surname} <br />
                <strong>Data urodzenia: </strong> {this.props.birthdate} <br />
                <strong>Miejsce urodzenia: </strong> {this.props.birthPlace} <br />
                <strong>Wzrost: </strong> {this.props.height}cm<br />
            </div>
        );
    }
}

export default StarShortDetails;