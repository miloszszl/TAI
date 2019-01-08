import React, { Component } from 'react';
import { Link, Redirect } from "react-router-dom";

class Registered extends Component {
    render() {
        if (this.props.isAuthenticated) {
            return <Redirect to='/' />
        }

        return (
            <div className="row d-flex justify-content-md-center">
                <div className="col-lg-4">
                    <div className="panel">
                        <h2>Rejestracja przebiegła pomyślnie !</h2>
                        <p>Teraz możesz się zalogować</p>
                        <Link className="btn btn-primary" to='/login'>Logowanie</Link>
                    </div>
                </div>
            </div>
        );
    }
}

export default Registered;