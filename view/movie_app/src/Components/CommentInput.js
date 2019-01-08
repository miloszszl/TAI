import React, { Component } from 'react';
import axios from 'axios';
import qs from 'qs';
import { Link } from "react-router-dom";
import { API_BASE_URL, ACCESS_TOKEN } from '../constants'
import { simpleHandleInputChange } from '../Utils'

class CommentInput extends Component {

    constructor(props) {
        super(props);
        this.state = {
            commentContent: '',
            isLoadingComments: false,
            isLoadingCast: false
        }

        this.handleInputChange = simpleHandleInputChange.bind(this);
        this.publishComment = this.publishComment.bind(this);
    }

    publishComment(e) {
        e.preventDefault();
        if (localStorage.getItem(ACCESS_TOKEN)) {

            axios.post(`${API_BASE_URL}${this.props.url}/comments`, {
                content: this.state.commentContent
            },
                { headers: { 'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN) } })
                .then(response => {
                    this.setState({
                        commentContent: ''
                    }, () => {
                        this.props.addComment(response.data);
                        this.props.notification("Komentarz", "Pomyślnie opublikowano komentarz", "success")
                    });
                }).catch(error => {
                });
        }
    }

    render() {

        if (!this.props.isAuthenticated) {
            return (
                <div className="p-2" style={{ "textAlign": "center" }}>
                    <Link className="btn btn-primary m-1" to='/login'><small>Zaloguj się aby dodawać komentarze</small></Link>
                </div>
            )
        }

        return (
            <div className="p-1 form-group">
                <label htmlFor="comment">Napisz komentarz:</label>

                <textarea className="form-control" name="commentContent" rows="5" key="comment" value={this.state.commentContent}
                    style={{ "minHeight": "100px" }} maxLength="500" onChange={this.handleInputChange}></textarea>

                <button type="button" className="btn btn-primary mt-2" onClick={this.publishComment}
                    style={{ "float": "right" }}>Publikuj</button>

                <div style={{ "clear": "both" }}></div>
            </div>
        );
    }
}

export default CommentInput;