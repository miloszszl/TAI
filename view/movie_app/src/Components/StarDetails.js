import React, { Component } from 'react';
import Comments from './Comments';
import Rating from './Rating';
import StarShortDetails from './StarShortDetails';
import Picture from './Picture';
import { API_BASE_URL, ACCESS_TOKEN } from '../constants'
import axios from 'axios';
import MovieList from './MovieList';
import MovieListNotRole from './MovieListNotRole';

class StarDetails extends Component {
    constructor(props) {
        super(props);
        this.state = {
            star: null
        };

        this.addComment = this.addComment.bind(this);
    }

    componentDidMount() {
        axios.get(`${API_BASE_URL}stars/${this.props.match.params.id}`, { headers: { "Access-Control-Allow-Origin": "*" } })
            .then(res => {
                console.log(res.data)
                this.setState({ star: res.data });
            });
    }

    addComment(data) {
        let star = this.state.star;
        if (star != null) {
            if (star.moviePersonCommentList) {
                star.moviePersonCommentList.unshift(data);
            } else {
                star.moviePersonCommentList = [data];
            }
            this.setState({
                star: star
            });
        }
    }

    render() {
        let rating;
        if(this.state.star){
            rating = <Rating notification={this.props.notification} url={ "stars/" + this.state.star.id} rate={this.state.star.rate} isAuthenticated={this.props.isAuthenticated}/>
        }

        return (
            <React.Fragment>
                <div className="row">
                    <Picture file={this.state.star == null ? null : this.state.star.image} title={this.state.star == null ? null : this.state.star.title} />
                    <div className="col-md-9">
                        <div className="row">
                            <h2 className="col-sm-12" style={{ "textAlign": "left", "paddingLeft": "0px" }}>{this.state.star == null ? null : this.state.star.name+" "+this.state.star.surname} </h2>
                        </div>
                        <div className="row" >
                            <div className="col-sm-12" style={{ "paddingLeft": "0px", }}>
                                <div className="jumbotron" style={{ "height": "224px" }}>
                                    <div className="row">
                                        <StarShortDetails
                                            birthPlace={this.state.star == null ? null : this.state.star.birthPlace}
                                            birthdate={this.state.star == null ? null : this.state.star.birthdate}
                                            height={this.state.star == null ? null : this.state.star.height}
                                            name={this.state.star == null ? null : this.state.star.name}
                                            surname={this.state.star == null ? null : this.state.star.surname}
                                        />
                                        {rating}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <MovieList movies={this.state.star == null ? null : this.state.star.actorMovieList} />
                <MovieListNotRole movies={this.state.star == null ? null : this.state.star.directorMovieList} asWho="Jako reżyser" num="1"/>
                <MovieListNotRole movies={this.state.star == null ? null : this.state.star.scenaristMovieList} asWho="Jako scenarzysta" num="2"/>
                <Comments addComment={this.addComment}
                    starId={this.props.match.params.id}
                    isAuthenticated={this.props.isAuthenticated}
                    commentList={this.state.star == null ? null : this.state.star.moviePersonCommentList}
                    url={this.state.star == null ? null : "stars/" + this.state.star.id}
                    notification={this.props.notification} />
            </React.Fragment>
        );
    }
}

export default StarDetails;