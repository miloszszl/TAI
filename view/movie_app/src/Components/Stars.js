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
    { value: '4', label: 'Ocena malejąco' }
];

class Stars extends Component {

    constructor(props) {
        super(props);
        this.state = {
            selectedSortOption: 0,
            fullName: '',
            stars: [],
            isLoading: false
        }

        this.loadStars = this.loadStars.bind(this);
        this.handleFullNameChange = this.handleFullNameChange.bind(this);
    }

    handleFullNameChange(e) {
        this.setState({
            [e.target.name]: e.target.value,
        }, this.loadStars);
    }

    loadStars() {
        this.setState({
            isLoading: true
        });
        axios.get(`${API_BASE_URL}stars/`, {
            params:
            {
                fullName: this.state.fullName,
                sortOpt: this.state.selectedSortOption
            },
            headers: { "Access-Control-Allow-Origin": "*" }
        }).then(res => {
            this.setState({
                isLoading: false
            });
            this.setState({ stars: res.data });
        }).catch(error => {
            this.setState({
                isLoading: false
            });
        });
    }

    handleChangeSortOption = (selectedOption) => {
        this.setState({ selectedSortOption: selectedOption == null ? 0 : selectedOption.value }, this.loadStars);
    }

    componentDidMount() {
        this.loadStars();
    }

    render() {
        let thumbnails;
        if (this.state.isLoading) {
            thumbnails = <Loader />;
        } else {
            thumbnails = this.state.stars.map(function (star) {
                return <Thumbnail key={star.id} url={"/stars/" + star.id} starId={star.id} file={star.image} title={star.fullName} rate={star.rate} />;
            });
            thumbnails = <div className="row">{thumbnails}</div>
        }

        let adminMenuOption;
        // if (this.props.isAuthenticated && this.props.currentUser && this.props.currentUser.admin) {
        //     adminMenuOption = <Link className="" to='/stars/add'>Dodaj gwiazdę filmową</Link>;
        // }
        

        return (
            <div>
                <h5>Gwiazdy filmowe</h5>{adminMenuOption}
                <form className="form-inline" style={{ "padding": "5px 15px", "justifyContent": "center" }}>
                    <div className="col-sm-6">
                        <input className="form-control" onChange={this.handleFullNameChange} value={this.state.fullName} name="fullName" style={{ "width": "100%" }} type="search" placeholder="Nazwa..." aria-label="Search" />
                    </div>
                </form>
                <form className="form-inline" style={{ "padding": "15px", "justifyContent": "center" }}>
                    <Select className="col-sm-6"
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

export default Stars;