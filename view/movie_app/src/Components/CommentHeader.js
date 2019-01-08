import React, { Component } from 'react';
import '../Styles/CommentHeader.css';
import { Link } from "react-router-dom";

class CommentHeader extends Component {

    render() {
        return (
            <div className="row">
                <div className="col-sm-12">
                    <Link className="" to={`/users/${this.props.user.id}/commentedMovies`}>
                        <h6 style={{ "display": "inline-block" }}>{this.props.user.nickname}</h6>
                    </Link>
                    <small style={{ "paddingLeft": "12px", "color": "grey" }}>{this.props.date}</small>
                </div>
                {/* <div className="col-sm-6 btns float-right">
                    <span className="float-right">
                        tekst
                        <i class="fas fa-thumbs-up fa-lg" data-toggle="tooltip" data-placement="top" title={this.props.likeMessage}></i> likes
                    </span>
                </div> */}
            </div>
        );
    }
}

export default CommentHeader;