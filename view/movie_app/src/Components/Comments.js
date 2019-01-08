import React, { Component } from 'react';
import Comment from './Comment';
import '../Styles/Comments.css';
import CommentInput from './CommentInput';

class Comments extends Component {

    render() {
        let comments;
        let uid = 0;
        if (this.props.commentList != null && this.props.commentList.length > 0) {
            comments = this.props.commentList.map(function (comment) {
                uid += 1;
                return <Comment user={comment.user} key={uid} content={comment.content} nickname={comment.user.nickname} date={comment.date} />
            })
        }

        return (
            <div className="Comments">
                <h6>Komentarze <span className="badge badge-secondary badge-pill">{this.props.commentList == null ? null : this.props.commentList.length}</span></h6>
                <hr />
                <CommentInput addComment={this.props.addComment} movieId={this.props.movieId} notification={this.props.notification} isAuthenticated={this.props.isAuthenticated} url={this.props.url} />
                <ul className="list-unstyled">
                    {comments}
                </ul>
            </div>
        );
    }
}

export default Comments;