import React, { Component } from 'react';
import '../Styles/Rating.css';
import RatingButtons from './RatingButtons';
import axios from 'axios';
import { API_BASE_URL, ACCESS_TOKEN } from '../constants'
import { simpleHandleInputChange } from '../Utils'

class Rating extends Component {
    constructor(props) {
        super(props);
        this.state = {
            userRate: null,
            globalRate: null
        }

        this.loadUserRate = this.loadUserRate.bind(this);
        this.handleClick = this.handleClick.bind(this);
        this.sendRate = this.sendRate.bind(this);
    }

    loadUserRate() {
        if (this.props.url && this.props.isAuthenticated)
            axios.get(`${API_BASE_URL}users/me/${this.props.url}/rate`,
                {
                    headers: {
                        'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
                    }
                }
            ).then(res => {
                this.setState({ userRate: res.data });
            }).catch(e => {
            });
    }

    handleClick(rate) {
        this.setState({ userRate: rate }, this.sendRate);
        this.props.notification("Ocena", "Pomyślnie oceniono treści", "success")
    }

    sendRate() {
        axios.put(`${API_BASE_URL}users/me/${this.props.url}/rate`, { rate: this.state.userRate },
            {
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
                }
            }
        ).then(res => {
            console.log(res.data);
            this.setState({ globalRate: res.data });
        });
    }

    componentDidMount() {
        this.loadUserRate();
        this.setState({
            globalRate: this.props.rate
        });
    }

    render() {
        return (
            <div className="Rating col-md-5">
                <h5>Średnia ocena użytkowników</h5>
                <h2 className="bold padding-bottom-7">{this.state.globalRate} <small>/ 5</small></h2>
                <RatingButtons handleClick={this.handleClick} rate={this.state.userRate} isAuthenticated={this.props.isAuthenticated}/>
            </div>
        );
    }
}

export default Rating;