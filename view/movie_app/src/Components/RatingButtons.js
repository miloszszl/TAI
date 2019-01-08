import React, { Component } from 'react';
import RatingButton from './RatingButton';
import "../Styles/RatingButtons.css";
import $ from 'jquery';
import { Link } from "react-router-dom";

class RatingButtons extends Component {

    componentDidMount() {
        $(function () {
            $('[data-toggle="tooltip"]').tooltip()
        })
    }

    render() {
        let info;
        let buttons = [];
        if (this.props.isAuthenticated) {
            for (let i = 1; i <= 5; i++) {
                if (i <= this.props.rate)
                    buttons.push(<RatingButton handleClick={this.props.handleClick} type="gold" value={i} key={i} />)
                else
                    buttons.push(<RatingButton handleClick={this.props.handleClick} value={i} key={i} />)
            }
            info=<small className="p-2">Twoja ocena: <strong>{this.props.rate}</strong> / 5</small>
        } else {
            buttons =
                <div className="p-1" style={{ "textAlign": "center" }}>
                    <Link className="btn btn-primary m-1" to='/login'><small>Zaloguj się aby oceniać treści</small></Link>
                </div>
        }

        return (
            <div className="Buttons">
                {buttons}<br/>
                {info}
            </div>
        );
    }
}

export default RatingButtons;