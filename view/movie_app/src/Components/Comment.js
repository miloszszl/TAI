import React, { Component } from 'react';
import '../Styles/Comment.css';
import CommentHeader from './CommentHeader';
import { Link } from "react-router-dom";

class Comment extends Component {
    render() {
        return (
            <li className="p-1">
                <div className="media border p-3">
                    <Link className="" to={`/users/${this.props.user.id}`}>
                        <img className="mr-3" width="64px" height="64px" src={'data:image/jpeg;base64,' + this.props.user.image} alt="Obraz" />
                    </Link>
                    <div className="media-body" >
                        <CommentHeader date={this.props.date} user={this.props.user} />
                        <span className="commentContent">
                            {this.props.content}
                        </span>
                    </div>
                </div>
            </li>
        );
    }
}

export default Comment;