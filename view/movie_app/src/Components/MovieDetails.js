import React, { Component } from 'react';
import Comments from './Comments';
import Rating from './Rating';
import Description from './Description';
import MovieStarsList from './MovieStarsList';
import MovieShortDetails from './MovieShortDetails';
import Picture from './Picture';
import { API_BASE_URL, ACCESS_TOKEN } from '../constants'
import axios from 'axios';

class MovieDetails extends Component {
    constructor(props) {
        super(props);
        this.state = {
            movie: null
        };

        this.addComment = this.addComment.bind(this);
    }

    componentDidMount() {
        axios.get(`${API_BASE_URL}movies/${this.props.match.params.id}`, { headers: { "Access-Control-Allow-Origin": "*" } })
            .then(res => {
                this.setState({ movie: res.data });
            });
    }

    addComment(data) {
        let movie = this.state.movie;
        if (movie != null) {
            if (movie.movieCommentList) {
                movie.movieCommentList.unshift(data);
            } else {
                movie.movieCommentList = [data];
            }
            this.setState({
                movie: movie
            });
        }
    }

    render() {
        let rating;
        if(this.state.movie){
            rating = <Rating notification={this.props.notification} url={ "movies/" + this.state.movie.id} rate={this.state.movie.rate} isAuthenticated={this.props.isAuthenticated}/>
        }

        return (
            <React.Fragment>
                <div className="row">
                    <Picture file={this.state.movie == null ? null : this.state.movie.image} title={this.state.movie == null ? null : this.state.movie.title} />
                    <div className="col-md-9">
                        <div className="row">
                            <h2 className="col-sm-12" style={{ "textAlign": "left", "paddingLeft": "0px" }}>{this.state.movie == null ? null : this.state.movie.title} </h2>
                        </div>
                        <div className="row" >
                            <div className="col-sm-12" style={{ "paddingLeft": "0px", }}>
                                <div className="jumbotron" style={{ "height": "224px" }}>
                                    <div className="row">
                                        <MovieShortDetails time={this.state.movie == null ? null : this.state.movie.time}
                                            poland_premiere={this.state.movie == null ? null : this.state.movie.polandPremiere}
                                            world_premiere={this.state.movie == null ? null : this.state.movie.worldPremiere}
                                            boxoffice={this.state.movie == null ? null : this.state.movie.boxoffice}
                                            scenarists={this.state.movie == null ? null : this.state.movie.scenaristList}
                                            directors={this.state.movie == null ? null : this.state.movie.directorList}
                                        />
                                        {rating}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <Description content={this.state.movie == null ? null : this.state.movie.description} />
                <MovieStarsList cast={this.state.movie == null ? null : this.state.movie.actorList} />
                <Comments addComment={this.addComment}
                    movieId={this.props.match.params.id}
                    isAuthenticated={this.props.isAuthenticated}
                    commentList={this.state.movie == null ? null : this.state.movie.movieCommentList}
                    url={this.state.movie == null ? null : "movies/" + this.state.movie.id}
                    notification={this.props.notification} />
            </React.Fragment>
        );
    }
}

export default MovieDetails;