import React, { Component } from 'react';
import axios from 'axios';
import { Link, Redirect } from "react-router-dom";
import { API_BASE_URL, ACCESS_TOKEN } from '../constants'
import ImageUploader from 'react-images-upload';
import '../Styles/Registration.css';
import Select from 'react-select';
import makeAnimated from 'react-select/lib/animated';

class AddMovie extends Component {

    constructor(props) {
        super(props);
        this.state = {
            movies: [],
            title: { value: '', status: '', msg: '' },
            description: { value: '', status: 'is-valid', msg: '' },
            worldPremiere: { value: '', status: 'is-valid', msg: '' },
            polandPremiere: { value: '', status: 'is-valid', msg: '' },
            movieTypeList: { value: null, status: 'is-valid', msg: '' },
            image: { value: null, status: '#28a745', msg: '' },
            actorList: [{ id: null, roleName: '' }],
            directorList: [],
            scenaristList: [],
            boxoffice: { value: '', status: 'is-valid', msg: '' },
            time: { value: '', status: 'is-valid', msg: '' },
            formValid: false
        }

        this.loadMovies = this.loadMovies.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
        this.checkTitleAvailability = this.checkTitleAvailability.bind(this);
        this.getOptionsByIds = this.getOptionsByIds.bind(this);
        this.handleChangeScenarist = this.handleChangeScenarist.bind(this);
        this.getScenaristInputs = this.getScenaristInputs.bind(this);
        this.handleChangeDirector = this.handleChangeDirector.bind(this);
        this.getDirectorInputs = this.getDirectorInputs.bind(this);
    }

    componentDidMount() {
        this.loadMovies();
    }

    loadMovies() {
        axios.get(`${API_BASE_URL}movies/short`, { headers: { 'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN) } })
            .then(res => {
                const data = res.data.map(function (element) {
                    return { id: element.id, value: element.title, label: element.title };
                });
                this.setState({ movies: data, moviesScenarist: data });
            });
    }


    handleInputChange(e, validationFun) {
        const inputName = e.target.name;
        const inputValue = e.target.value;

        this.setState({
            [inputName]: {
                value: inputValue,
                ...validationFun(inputValue)
            }
        });
    }

    validatePremiere(val) {
        return {
            status: 'is-valid',
            msg: ""
        }
    }

    validateDescription(value) {
        if (value.length > 500)
            return {
                status: 'is-invalid',
                msg: "Maksymalna długość opisu to 500 znaków"
            }
        else
            return {
                status: 'is-valid',
                msg: ""
            }
    }


    onDrop(images) {
        if (images.length == 1) {
            this.setState({
                image: {
                    value: new Blob([images[0]]),
                    status: '#28a745',
                    msg: ""
                }
            });
        } else if (images.length > 1) {
            this.setState({
                image: {
                    value: null,
                    status: 'lightgrey',
                    msg: "Niepoprawna liczba obrazów"
                }
            });
        } else {
            this.setState({
                image: {
                    value: null,
                    status: '#28a745',
                    msg: ""
                }
            });
        }
    }

    checkTitleAvailability() {

    }

    getOptionsByIds(ids) {
        return this.state.movies.map(function (e) {
            if (ids.indexOf(e.id) <= -1) {
                return e;
            }
        }).filter((e) => { return e != undefined });
    }

    validateTitle(value) {
        if (value.length == 0 || value.length > 50) {
            return {
                status: 'is-invalid',
                msg: "Wymagana długość tytułu to 1-50 znaków"
            }
        } else {
            return {
                status: 'is-valid',
                msg: ""
            }
        }
    }

    validateBoxoffice(val) {
        if (val != '' && (val < 0 || val != parseInt(val, 10))) {
            return {
                status: 'is-invalid',
                msg: "Budżet musi być liczbą naturalną"
            }
        } else {
            return {
                status: 'is-valid',
                msg: ""
            }
        }
    }

    validateTime(val) {
        if (val != '' && (val < 0 || val != parseInt(val, 10))) {
            return {
                status: 'is-invalid',
                msg: "Czas trwania musi być liczbą naturalną"
            }
        } else {
            return {
                status: 'is-valid',
                msg: ""
            }
        }
    }

    handleChangeScenarist = (selectedOption, prevId) => {
        let ids = this.state.scenaristList;
        if (selectedOption) {
            ids.push(selectedOption.id)
        }
        else {
            ids.indexOf(prevId)
            ids.splice(ids.indexOf(prevId), 1);
        }
        this.setState({ scenaristList: ids });
    }

    handleChangeDirector = (selectedOption, prevId) => {
        let ids = this.state.directorList;
        if (selectedOption) {
            ids.push(selectedOption.id)
        }
        else {
            ids.indexOf(prevId)
            ids.splice(ids.indexOf(prevId), 1);
        }
        this.setState({ directorList: ids });
    }

    getScenaristInputs(options) {
        let scenaristInputs = this.state.scenaristList.map(function (movieId) {
            let index = this.state.movies.map(function (e) { return e.id; }).indexOf(movieId);
            const movie = this.state.movies[index];
            return (
                <Select className="mb-2"
                    key={movie.id}
                    onChange={(selectedOption) => this.handleChangeScenarist(selectedOption, movie.id)}
                    components={makeAnimated()}
                    value={movie}
                    isClearable
                    closeMenuOnSelect={true}
                    options={options}
                    placeholder="Film"
                />)
        }, this);

        if (options.length > 0)
            scenaristInputs.push(<Select
                value={""}
                key={"new"}
                onChange={(selectedOption) => this.handleChangeScenarist(selectedOption, null)}
                components={makeAnimated()}
                isClearable
                closeMenuOnSelect={true}
                options={options}
                placeholder="Film"
            />)
        return scenaristInputs;
    }

    getDirectorInputs(options) {
        let directorInputs = this.state.directorList.map(function (movieId) {
            let index = this.state.movies.map(function (e) { return e.id; }).indexOf(movieId);
            const movie = this.state.movies[index];
            return (
                <Select className="mb-2"
                    key={movie.id + "d"}
                    onChange={(selectedOption) => this.handleChangeDirector(selectedOption, movie.id)}
                    components={makeAnimated()}
                    value={movie}
                    isClearable
                    closeMenuOnSelect={true}
                    options={options}
                    placeholder="Film"
                />)
        }, this);

        if (options.length > 0)
            directorInputs.push(<Select
                value={""}
                key={"new2"}
                onChange={(selectedOption) => this.handleChangeDirector(selectedOption, null)}
                components={makeAnimated()}
                isClearable
                closeMenuOnSelect={true}
                options={options}
                placeholder="Film"
            />)
        return directorInputs;
    }

    getActorInputs(options) {
        let directorInputs = this.state.directorList.map(function (movieId) {
            let index = this.state.movies.map(function (e) { return e.id; }).indexOf(movieId);
            const movie = this.state.movies[index];
            return (
                <React.Fragment key={movie.id + "d"}>
                    <Select className="mb-2"
                        onChange={(selectedOption) => this.handleChangeDirector(selectedOption, movie.id)}
                        components={makeAnimated()}
                        value={movie}
                        isClearable
                        closeMenuOnSelect={true}
                        options={options}
                        placeholder="Film"
                    />
                    <input type="text" className={`form-control ${this.state.title.status}`} name={`role${movie.id}`} placeholder="Jako..."
                        value={this.state.title.value} onChange={(event) => this.handleInputChange(event, this.validateTitle)}
                        onBlur={this.checkTitleAvailability} required
                    />
                </React.Fragment>)
        }, this);

        if (options.length > 0)
            directorInputs.push(<Select
                value={""}
                key={"new2"}
                onChange={(selectedOption) => this.handleChangeDirector(selectedOption, null)}
                components={makeAnimated()}
                isClearable
                closeMenuOnSelect={true}
                options={options}
                placeholder="Film"
            />)
        return directorInputs;
    }

    render() {
        if (this.props.isAuthenticated) {
            return <Redirect to='/movies' />
        }

        let scenaristInputs = this.getScenaristInputs(this.getOptionsByIds(this.state.scenaristList));
        let directorInputs = this.getDirectorInputs(this.getOptionsByIds(this.state.directorList));

        return (
            <div className="row d-flex justify-content-md-center" >
                <div className="col-lg-4">
                    <div className="panel">
                        <h2>Dodaj film</h2>
                        <p>Wprowadź dane dotyczące filmu</p>
                    </div>
                    <form id="AddMovie" className="md-form" style={{ "textAlign": "left" }} encType="multipart/form-data">
                        <div className="mb-3">
                            *Tytuł:
                            <input type="text" className={`form-control ${this.state.title.status}`} id="titleInput" name="title" placeholder="Tytuł"
                                value={this.state.title.value} onChange={(event) => this.handleInputChange(event, this.validateTitle)}
                                onBlur={this.checkTitleAvailability} required
                            />
                            <div className="invalid-feedback">
                                {this.state.title.msg}
                            </div>
                        </div>
                        <div className="mb-3">
                            Opis:
                            <textarea className={`form-control ${this.state.description.status}`} name="description" rows="5" key="comment" value={this.state.description.value} placeholder="Opis"
                                style={{ "minHeight": "100px" }} onChange={(event) => this.handleInputChange(event, this.validateDescription)}></textarea>
                            <div className="invalid-feedback">
                                {this.state.description.msg}
                            </div>
                        </div>
                        <div className="mb-3">
                            Premiera w Polsce:
                            <input type="date" className={`form-control ${this.state.polandPremiere.status}`} id="polandPremiereId" name="polandPremiere"
                                value={this.state.polandPremiere.value} onChange={(event) => this.handleInputChange(event, this.validatePremiere)}
                            />
                            <div className="invalid-feedback">
                                {this.state.polandPremiere.msg}
                            </div>
                        </div>
                        <div className="mb-3">
                            Premiera na Świecie:
                            <input type="date" className={`form-control ${this.state.worldPremiere.status}`} id="worldPremiereId" name="worldPremiere"
                                value={this.state.worldPremiere.value} onChange={(event) => this.handleInputChange(event, this.validatePremiere)}
                            />
                            <div className="invalid-feedback">
                                {this.state.worldPremiere.msg}
                            </div>
                        </div>
                        <div className="mb-3">
                            Budżet ($):
                            <input type="text" className={`form-control ${this.state.boxoffice.status}`} id="boxofficeInput" name="boxoffice" placeholder="Budżet"
                                value={this.state.boxoffice.value} onChange={(event) => this.handleInputChange(event, this.validateBoxoffice)}
                            />
                            <div className="invalid-feedback">
                                {this.state.boxoffice.msg}
                            </div>
                        </div>
                        <div className="mb-3">
                            Czas trwania (min):
                            <input type="text" className={`form-control ${this.state.time.status}`} id="timeInput" name="time" placeholder="Czas trwania"
                                value={this.state.time.value} onChange={(event) => this.handleInputChange(event, this.validateTime)}
                            />
                            <div className="invalid-feedback">
                                {this.state.time.msg}
                            </div>
                        </div>

                        <div className="mb-3 card" style={{ "border": `1px solid #28a745` }}>
                            <div className="card-body">
                                Jako scenarzysta:
                                {scenaristInputs}
                            </div>
                        </div>

                        <div className="mb-3 card" style={{ "border": `1px solid #28a745` }}>
                            <div className="card-body">
                                Jako reżyser:
                                {directorInputs}
                            </div>
                        </div>

                        <div className="mb-3 card" style={{ "border": `1px solid #28a745` }}>
                            <div className="card-body">
                                Jako aktor:
                                {/* {directorInputs} */}
                            </div>
                        </div>

                        <div className="mb-3" style={{ "border": `1px solid ${this.state.image.status}`, "borderRadius": "5px" }}>
                            <ImageUploader
                                fileTypeError="Nieprawidłowe rozszerzenie"
                                fileSizeError="Plik jest zbyt duży"
                                className="imageUploader"
                                withIcon={true}
                                buttonText='Wybierz obraz'
                                onChange={this.onDrop}
                                imgExtension={['.jpg', '.gif', '.png', '.gif']}
                                maxFileSize={15242880}
                                label="Maksymalny rozmiar pliku: 15MB, rozszerzenia: jpg, gif, png, gif"
                                withPreview={true}
                                singleImage={true}
                            />
                            <div style={{ "color": "#dc3545", "marginTop": ".25rem", "fontSize": "80%" }}>
                                {this.state.image.msg}
                            </div>
                        </div>
                        <button type="submit" id="submitFormBtn" className="btn btn-primary" style={{ "width": "100%" }} disabled={!this.state.formValid}>Zarejestruj</button>
                    </form>
                    <div style={{ "textAlign": "left", "margin": "8px 0px" }}>
                        Masz już konto? <Link to='/login'>Zaloguj się</Link>
                    </div>
                    <hr style={{ "margin": "8px 0px" }} />
                    <p style={{ "textAlign": "left" }}>* - pole wymagane</p>
                </div>
            </div >
        );
    }
}

export default AddMovie;