import React, { Component } from 'react';
import "../Styles/RatingButton.css"

class RatingButton extends Component {

    render() {
        const style = this.props.type === "gold" ? "btn-warning btn-gold" : "btn-light";
        return (
            <span className="pr-1">
                <button type="button" className={"btn " + style + " btn-sm"} aria-label="Left Align"
                    onClick={() => { this.props.handleClick(this.props.value) }} data-toggle="tooltip" data-placement="bottom" title={this.props.value}>
                    <i className="fas fa-star"></i>
                </button>
            </span>
        );
    }
}

export default RatingButton;