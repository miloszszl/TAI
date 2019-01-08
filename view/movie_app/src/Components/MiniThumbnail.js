import React, { Component } from 'react';
import { Link } from "react-router-dom";

class MiniThumbnail extends Component {
    render() {
        return (
            <li key={this.props.element.id} className="list-group-item">
                <Link className="card-link" to={`/stars/${this.props.element.id}`}>
                    <img className="mr-3" width="64px" height="64px" src={'data:image/jpeg;base64,' + this.props.element.image} alt="Obraz" />
                </Link>
                <Link className="card-link" to={`/stars/${this.props.element.id}`}>
                    {this.props.element.fullName}
                </Link>
                <span> jako {this.props.element.roleName}
                </span>
            </li>
        );
    }
}

export default MiniThumbnail;