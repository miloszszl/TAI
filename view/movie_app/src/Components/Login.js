import React, { Component } from 'react';
import '../Styles/Login.css';
import { API_BASE_URL, ACCESS_TOKEN } from '../constants'
import { Link } from "react-router-dom";
import axios from 'axios';
import { Redirect } from 'react-router-dom'
import { simpleHandleInputChange } from '../Utils'

class Login extends Component {

    constructor(props) {
        super(props);
        this.state = {
            email: '',
            password: '',
            errorMsg: ''
        }

        this.handleSubmit = this.handleSubmit.bind(this);
        this.simpleHandleInputChange = simpleHandleInputChange.bind(this);
    }

    handleSubmit(e) {
        e.preventDefault();
        axios.post(`${API_BASE_URL}auth/signin`, { email: this.state.email, password: this.state.password })
            .then(res => {
                this.props.onLogin(res.data);
            }).catch(error => {
                if (error.response.status == 401) {
                    this.setState({
                        errorMsg: "Niepoprawny login lub hasło"
                    }, () => { this.props.notification("Błąd logowania", "Niepoprawny login lub hasło!", "danger") });
                }
            });
    }

    // handleInputChange(e) {
    //     this.setState({
    //         [e.target.name]: e.target.value,
    //     });
    // }

    render() {
        if (this.props.isAuthenticated) {
            return <Redirect to='/' />
        }

        return (
            <div className="row justify-content-md-center" style={{"textAlign":"left"}}>
                <div className="col-sm-4">
                    <div className="panel">
                        <h2>Logowanie</h2>
                        <p>Wprowadź adres <strong>e-mail</strong> oraz <strong>hasło</strong></p>
                    </div>
                    <form id="Login" onSubmit={this.handleSubmit}>
                        <div className="mb-3">
                            Adres e-mail:
                            <input type="text" className='form-control' id="emailInput" name="email" placeholder="Adres e-mail"
                                value={this.state.email} onChange={this.simpleHandleInputChange} required />
                        </div>
                        <div className="mb-3">
                            Hasło:
                            <input type="password" className='form-control' id="passwordInput" name="password" placeholder="Hasło"
                                value={this.state.password} onChange={this.simpleHandleInputChange} required />
                        </div>
                        <hr />
                        <button type="submit" className="btn btn-primary" style={{ "width": "100%" }}>Zaloguj</button>
                    </form>
                    <div style={{ "textAlign": "left", "margin": "8px 0px" }}>
                        Nie masz konta? <Link to='/registration'>Zarejestruj się</Link>
                    </div>
                </div>
            </div>
        );
    }
}

export default Login;