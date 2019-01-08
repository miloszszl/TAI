import React, { Component } from 'react';
import Thumbnail from './Thumbnail';
import Select from 'react-select';
import makeAnimated from 'react-select/lib/animated';
import axios from 'axios';
import { API_BASE_URL, ACCESS_TOKEN } from '../constants'
import Loader from './Loader';
import { Link } from "react-router-dom";

const sortOptions = [
    { value: '1', label: 'a-z' },
    { value: '2', label: 'z-a' },
    { value: '3', label: 'Ocena rosnąco' },
    { value: '4', label: 'Ocena malejąco' },
    { value: '5', label: 'Premiera rosnąco' },
    { value: '6', label: 'Premiera malejąco' }
];

class Movies extends Component {
    constructor(props) {
        super(props);
        this.state = {
            title: '',
            selectedMovieTypes: [],
            selectedPremiereYears: [],
            selectedSortOption: 0,
            movies: [],
            movieTypes: [],
            years: [],
            isLoading: false
        }

        this.handleTitleChange = this.handleTitleChange.bind(this);
        this.handleChangePremiereYear = this.handleChangePremiereYear.bind(this);
        this.handleChangeMovieType = this.handleChangeMovieType.bind(this);
        this.handleChangeSortOption = this.handleChangeSortOption.bind(this);
    }

    handleTitleChange(e) {
        this.setState({
            [e.target.name]: e.target.value,
        }, this.loadMoviesWithCriteria);
    }

    loadMoviesWithCriteria() {
        this.setState({
            isLoading: true
        });
        axios.get(`${API_BASE_URL}movies/`, {
            params:
            {
                types: this.state.selectedMovieTypes,
                years: this.state.selectedPremiereYears,
                sortOpt: this.state.selectedSortOption,
                title: this.state.title
            },
            paramsSerializer: function (params) {
                let result = '';
                for (let property in params) {
                    if (params.hasOwnProperty(property)) {
                        if (Array.isArray(property)) {
                            if (params[property].length > 0) {
                                result += property + '=';
                                result += params[property].join(',');
                                result += "&";
                            }
                        } else {
                            if (params[property] != null && ((params[property] + "").trim() != "")) {
                                result += property + '=';
                                result += params[property];
                                result += "&";
                            }
                        }
                    }
                }
                result = result.substr(0, result.length - 1);
                return result;
                // return qs.stringify(params, { arrayFormat: 'repeat' })
            },
            headers: { "Access-Control-Allow-Origin": "*" }
        }).then(res => {
            this.setState({
                isLoading: false
            });
            this.setState({ movies: res.data });
        }).catch(error => {
            this.setState({
                isLoading: false
            });
        });
    }



    handleChangeMovieType = (selectedOptions) => {
        const types = selectedOptions.map(function (element) {
            return element.id;
        });
        this.setState({ selectedMovieTypes: types }, this.loadMoviesWithCriteria);
    }

    handleChangePremiereYear = (selectedOptions) => {
        const years = selectedOptions.map(function (element) {
            return element.value;
        });
        this.setState({ selectedPremiereYears: years }, this.loadMoviesWithCriteria);
    }

    handleChangeSortOption = (selectedOption) => {
        this.setState({ selectedSortOption: selectedOption == null ? 0 : selectedOption.value }, this.loadMoviesWithCriteria);
    }

    loadMovies() {
        axios.get(`${API_BASE_URL}movies/`, { headers: { "Access-Control-Allow-Origin": "*" } })
            .then(res => {
                this.setState({ movies: res.data });
            });
    }

    loadTypes() {
        axios.get(`${API_BASE_URL}movies/types`, { headers: { "Access-Control-Allow-Origin": "*" } })
            .then(res => {
                const movieTypes = res.data.map(function (type) {
                    return { id: type.id, value: type.name, label: type.name };
                });
                this.setState({ movieTypes: movieTypes });
            });
    }

    loadYears() {
        axios.get(`${API_BASE_URL}movies/years`, { headers: { "Access-Control-Allow-Origin": "*" } })
            .then(res => {
                const years = res.data.map(function (element) {
                    return { value: element.year, label: element.year };
                });
                this.setState({ years: years });
            });
    }

    componentDidMount() {
        this.loadMoviesWithCriteria();
        this.loadTypes();
        this.loadYears();
    }

    render() {
        let thumbnails;
        if (this.state.isLoading) {
            thumbnails = <Loader />
        } else {
            thumbnails = this.state.movies.map(function (movie) {
                return <Thumbnail key={movie.id} url={"/movies/" + movie.id} movieId={movie.id} file={movie.image} title={movie.title} rate={movie.rate} />;
            })
            thumbnails = <div className="row">{thumbnails}</div>
        }

        let adminMenuOption;
        // if (this.props.isAuthenticated && this.props.currentUser && this.props.currentUser.admin) {
        //     adminMenuOption = <Link className="" to='/movies/add'>Dodaj Film</Link>;
        // }

        return (
            <div>
                <h5>Filmy</h5>{adminMenuOption}
                <form className="form-inline" style={{ "padding": "5px 15px", "justifyContent": "center" }}>
                    <div className="col-sm-9">
                        <input className="form-control" name="title" onChange={this.handleTitleChange} value={this.state.title} style={{ "width": "100%" }} type="search" placeholder="Tytuł filmu..." aria-label="Search" />
                    </div>
                </form>
                <form className="form-inline" style={{ "padding": "15px", "justifyContent": "center" }}>
                    <Select className="col-sm-3"
                        closeMenuOnSelect={false}
                        onChange={this.handleChangeMovieType}
                        components={makeAnimated()}
                        isMulti
                        options={this.state.movieTypes}
                        placeholder="Gatunek"
                    />
                    <Select className="col-sm-3"
                        closeMenuOnSelect={false}
                        onChange={this.handleChangePremiereYear}
                        components={makeAnimated()}
                        isMulti
                        options={this.state.years}
                        placeholder="Data premiery"
                    />
                    <Select className="col-sm-3"
                        onChange={this.handleChangeSortOption}
                        closeMenuOnSelect={true}
                        components={makeAnimated()}
                        options={sortOptions}
                        isClearable
                        placeholder="Sortuj"
                    />
                </form>
                {thumbnails}
            </div>
        );
    }

}

export default Movies;