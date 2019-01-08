import React, { Component } from 'react';
import axios from 'axios';
import { Link, Redirect } from "react-router-dom";
import { API_BASE_URL, ACCESS_TOKEN } from '../constants'
import ImageUploader from 'react-images-upload';
import '../Styles/Registration.css';

class Registration extends Component {

    constructor(props) {
        super(props);
        this.state = {
            registrationSuccess: false,
            nickname: { value: '', status: '', msg: '' },
            email: { value: '', status: '', msg: '' },
            password: { value: '', status: '', msg: '' },
            password2: { value: '', status: '', msg: '' },
            image: { value: null, status: '#28a745', msg: '' },
            formValid: false
        }

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.onDrop = this.onDrop.bind(this);

        this.checkEmailAvailability = this.checkEmailAvailability.bind(this);
        this.validatePassword2 = this.validatePassword2.bind(this);
        this.checkNicknameAvailability = this.checkNicknameAvailability.bind(this);
        this.validateForm = this.validateForm.bind(this);
    }

    validateForm() {
        this.setState({
            formValid: this.state.nickname.status == 'is-valid' &&
                this.state.email.status == 'is-valid' &&
                this.state.password.status == 'is-valid' &&
                this.state.password2.status == 'is-valid' &&
                this.state.image.status == '#28a745'
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
        }, this.validateForm);
    }

    validateEmail(value) {
        if (value.length > 50) {
            return {
                status: 'is-invalid',
                msg: "Adres e-mail jest za długi (max 50 znaków)"
            }
        } else if (!value.match(/^([\w.%+-]+)@([\w-]+\.)+([\w]{2,})$/i)) {
            return {
                status: 'is-invalid',
                msg: "Niepoprawny adres e-mail"
            }
        } else {
            return {
                status: 'is-valid',
                msg: ""
            }
        }
    }

    checkEmailAvailability() {
        const validationResult = this.validateEmail(this.state.email.value);
        if (validationResult.status == 'is-invalid') {
            this.setState({
                email: {
                    value: this.state.email.value,
                    status: validationResult.status,
                    msg: validationResult.msg
                }
            }, this.validateForm);
        } else {
            axios.get(`${API_BASE_URL}auth/checkEmailAvailability`, {
                params: {
                    email: this.state.email.value
                }
            }).then(res => {
                if (res.data.available == true) {
                    this.setState({
                        email: {
                            value: this.state.email.value,
                            status: 'is-valid',
                            msg: ""
                        }
                    }, this.validateForm);
                } else {
                    this.setState({
                        email: {
                            value: this.state.email.value,
                            status: 'is-invalid',
                            msg: "Ten adres e-mail jest już zajęty"
                        }
                    }, this.validateForm);
                }
            });
        }
    }

    validatePassword(value) {
        const passwordValid = value.match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{6,20})/i)
        if (passwordValid) {
            return {
                status: 'is-valid',
                msg: ""
            }
        } else {
            return {
                status: 'is-invalid',
                msg: "Hasło musi mieć 6-20 znaków, zamierać 1 małą i 1 dużą literę, cyfrę oraz znak specjalny"
            }
        }
    }

    validatePassword2(value) {
        const password2Valid = this.state.password.value == value;
        if (password2Valid) {
            return {
                status: 'is-valid',
                msg: ""
            }
        } else {
            return {
                status: 'is-invalid',
                msg: "Hasła są różne"
            }
        }
    }

    validateNickname(value) {
        if (value.length > 4 && value.length < 26) {
            return {
                status: 'is-valid',
                msg: ""
            }
        } else {
            return {
                status: 'is-invalid',
                msg: "Nickname musi mieć 5-25 znaków"
            }
        }
    }

    checkNicknameAvailability() {
        const validationResult = this.validateNickname(this.state.nickname.value);
        if (validationResult.status == 'is-invalid') {
            this.setState({
                nickname: {
                    value: this.state.nickname.value,
                    status: validationResult.status,
                    msg: validationResult.msg
                }
            }, this.validateForm);
        } else {
            axios.get(`${API_BASE_URL}auth/checkNicknameAvailability`, {
                params: {
                    nickname: this.state.nickname.value
                }
            }).then(res => {
                if (res.data.available == true) {
                    this.setState({
                        nickname: {
                            value: this.state.nickname.value,
                            status: 'is-valid',
                            msg: ""
                        }
                    }, this.validateForm);
                } else {
                    this.setState({
                        nickname: {
                            value: this.state.nickname.value,
                            status: 'is-invalid',
                            msg: "Ten nickname jest już zajęty"
                        }
                    }, this.validateForm);
                }
            });
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
                    status: '#dc3545',
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

    handleSubmit(event) {
        event.preventDefault();

        const data = new FormData();
        data.append('email', this.state.email.value);
        data.append('nickname', this.state.nickname.value);
        data.append('password', this.state.password.value);
        data.append('image', new Blob([this.state.image.value]));

        axios.post(`${API_BASE_URL}auth/signup`,
            data).then(res => {
                if (res.data.success) {
                    this.props.history.push('/registered')
                } else {
                    this.setState({
                        [res.data.fieldName]: {
                            value: this.state[res.data.fieldName].value,
                            status: 'is-invalid',
                            msg: res.data.messsage
                        }
                    }, this.validateForm);
                }
            }).catch(error => {
                if (error.response.status == 400) {
                    this.setState({
                        [error.response.data.fieldName]: {
                            value: this.state[error.response.data.fieldName].value,
                            status: 'is-invalid',
                            msg: error.response.data.message
                        }
                    }, this.validateForm);
                }
            });
    }

    render() {
        if (this.props.isAuthenticated) {
            return <Redirect to='/' />
        }

        return (
            <div className="row d-flex justify-content-md-center">
                <div className="col-lg-4">
                    <div className="panel">
                        <h2>Rejestracja</h2>
                        <p>Wprowadź dane niezbędne do rejestracji</p>
                    </div>
                    <form id="Registration" className="md-form" style={{ "textAlign": "left" }} onSubmit={this.handleSubmit} encType="multipart/form-data">
                        <div className="mb-3">
                            *Nickname:
                            <input type="text" className={`form-control ${this.state.nickname.status}`} id="nicknamelInput" name="nickname" placeholder="Nickname"
                                value={this.state.nickname.value} onChange={(event) => this.handleInputChange(event, this.validateNickname)}
                                onBlur={this.checkNicknameAvailability} required />
                            <div className="invalid-feedback">
                                {this.state.nickname.msg}
                            </div>
                        </div>
                        <div className="mb-3">
                            *Adres e-mail:
                            <input type="email" className={`form-control ${this.state.email.status}`} id="emailInput" name="email" placeholder="Adres e-mail"
                                value={this.state.email.value} onChange={(event) => this.handleInputChange(event, this.validateEmail)}
                                onBlur={this.checkEmailAvailability} required />
                            <div className="invalid-feedback">
                                {this.state.email.msg}
                            </div>
                        </div>
                        <div className="mb-3">
                            *Hasło:
                            <input type="password" className={`form-control ${this.state.password.status}`} id="passwordlInput" name="password" placeholder="Hasło"
                                value={this.state.password.value} onChange={(event) => this.handleInputChange(event, this.validatePassword)} required />
                            <div className="invalid-feedback">
                                {this.state.password.msg}
                            </div>
                        </div>
                        <div className="mb-3">
                            *Powtórz hasło:
                            <input type="password" className={`form-control ${this.state.password2.status}`} id="password2lInput" name="password2" placeholder="Powtórz hasło"
                                value={this.state.password2.value} onChange={(event) => this.handleInputChange(event, this.validatePassword2)} required />
                            <div className="invalid-feedback">
                                {this.state.password2.msg}
                            </div>
                        </div>
                        <div className="mb-3" style={{ "border": `1px solid ${this.state.image.status}`, "borderRadius": "5px" }}>
                            Obraz:
                            <ImageUploader 
                                fileTypeError="Nieprawidłowe rozszerzenie"
                                fileSizeError="Plik jest zbyt duży"
                                className="imageUploader"
                                withIcon={true}
                                buttonText='Wybierz obraz'
                                onChange={this.onDrop}
                                imgExtension={['.jpg', '.gif', '.png', '.gif']}
                                maxFileSize={2097152}
                                label="Maksymalny rozmiar pliku: 2MB, rozszerzenia: jpg, gif, png, gif"
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

export default Registration;