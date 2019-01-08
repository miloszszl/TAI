import React, { Component } from 'react';
import { API_BASE_URL, ACCESS_TOKEN } from '../constants';
import { Route, Switch, withRouter, Redirect, Link } from "react-router-dom";
import axios from 'axios';
import UserAvatar from './UserAvatar';
import '../Styles/UserProfile.css';
import Loader from './Loader';
import Thumbnail from './Thumbnail';

class UserProfile extends Component {

    constructor(props) {
        super(props);
        this.state = {
            thumbnails: [],
            user: null,
            isLoading: false
        }

        this.loadData = this.loadData.bind(this);
        this.loadUserProfile = this.loadUserProfile.bind(this);
        this.handleClick = this.handleClick.bind(this);
    }

    loadData(url) {
        if (localStorage.getItem(ACCESS_TOKEN)) {
            this.setState({
                isLoading: true
            });
            axios.get(`${API_BASE_URL}${url}`,
                { headers: { 'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN) } })
                .then(res => {
                    this.setState({
                        thumbnails: res.data,
                        isLoading: false
                    });
                }).catch(error => {
                    this.setState({
                        isLoading: false
                    });
                });
        }
    }

    loadUserProfile() {
        if (localStorage.getItem(ACCESS_TOKEN)) {
            axios.get(`${API_BASE_URL}users/${this.props.match.params.id}`, { headers: { 'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN) } })
                .then(res => {
                    this.setState({
                        user: {
                            nickname: res.data.nickname,
                            email: res.data.email,
                            image: res.data.image,
                            moviesComments: res.data.movieCommentList,
                            starsComments: res.data.moviePersonCommentList,
                            roles: res.data.roles,
                            joined: this.formatDate(res.data.createdAt)
                        }
                    });
                }).catch(error => {
                });
        }
    }

    getThumbnails() {
        let thumbnails;
        let urlBeg= this.getUrlBeg();
        if (this.state.isLoading) {
            thumbnails = <Loader />
        } else {
            thumbnails = this.state.thumbnails.map(function (thumbnail) {
                return <Thumbnail key={thumbnail.id} url={`/${urlBeg}/${thumbnail.id}`} file={thumbnail.image}
                    title={thumbnail.title == null ? thumbnail.fullName : thumbnail.title} rate={thumbnail.rate} />;
            }, this)
            thumbnails = <div className="row">{thumbnails}</div>
        }
        return thumbnails;
    }

    componentDidMount() {
        this.loadUserProfile();
        this.loadData(`users/${this.props.match.params.id}/${this.props.match.params.section}`);
    }

    formatDate(dateString) {
        const date = new Date(dateString);
        const monthNames = [
            "Styczeń", "Luty", "Marzec",
            "Kwiecień", "Maj", "Czerwiec", "Lipiec",
            "Sierpień", "Wrzesień", "Październik",
            "Listopad", "Grudzień"
        ];

        const monthIndex = date.getMonth();
        const year = date.getFullYear();

        return monthNames[monthIndex] + ' ' + year;
    }

    handleClick(url) {
        this.props.history.push(url);
        this.loadData(url.substr(1));
    }

    getUrlBeg() {
        if (this.props.match.params.section && this.props.match.params.section.indexOf("Movies") > -1)
            return "movies";
        else
            return "stars"
    }

    render() {
        if (!this.props.isAuthenticated) {
            return <Redirect to='/login' />
        }

        if (!this.state.user)
            return (<span ></span>);

        let thumbnails = this.getThumbnails();

        return (
            <div className="UserProfile">
                <div className="d-flex justify-content-center" style={{ 'paddingBottom': "16px" }}>
                    <UserAvatar file={this.state.user.image} title={this.state.user.nickname} />
                    <div>
                        <div className="p-2" style={{ "fontSize": "1.5em" }}>{this.state.user.nickname}</div>
                        <div className="p-3" style={{ 'color': 'gray' }}>
                            {this.state.user.email}<br />
                            Dołączył {this.state.user.joined}
                        </div>
                    </div>
                </div>
                <ul className="nav nav-tabs justify-content-center user-profile-menu" style={{ "marginBottom": "5px" }}>
                    <li className="nav-item">
                        <a className={`nav-link ${this.props.match.params.section == "commentedMovies" ? "active" : ""}`}
                            onClick={() => { this.handleClick(`/users/${this.props.match.params.id}/commentedMovies`) }}>Komentowane filmy</a>
                    </li>
                    <li className="nav-item">
                        <a className={`nav-link ${this.props.match.params.section == "commentedMoviePersons" ? "active" : ""}`}
                            onClick={() => { this.handleClick(`/users/${this.props.match.params.id}/commentedMoviePersons`) }}>Komentowane gwiazdy</a>
                    </li>
                    <li className="nav-item">
                        <a className={`nav-link ${this.props.match.params.section == "ratedMovies" ? "active" : ""}`}
                            onClick={() => { this.handleClick(`/users/${this.props.match.params.id}/ratedMovies`) }}>Ocenione filmy</a>
                    </li>
                    <li className="nav-item">
                        <a className={`nav-link ${this.props.match.params.section == "ratedMoviePersons" ? "active" : ""}`}
                            onClick={() => { this.handleClick(`/users/${this.props.match.params.id}/ratedMoviePersons`) }}>Ocenione gwiazdy</a>
                    </li>
                </ul>
                {thumbnails}
            </div>
        );
    }
}

export default UserProfile;