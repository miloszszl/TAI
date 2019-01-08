import 'bootstrap/dist/css/bootstrap.min.css';
import $ from 'jquery';
import Popper from 'popper.js';
import 'bootstrap/dist/js/bootstrap.bundle.min';
import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import { BrowserRouter as Router } from "react-router-dom";

ReactDOM.render(
    <Router >
        <App />
    </Router>
    , document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();



// import React from 'react'
// import ReactDOM from 'react-dom'
// import './index.css'
// import Form from './Form'

// const inputs = [{
//     name: "username",
//     placeholder: "username",
//     type: "text"
// },{
//     name: "password",
//     placeholder: "password",
//     type: "password"
// },{
//     type: "submit",
//     value: "Submit",
//     className: "btn"
// }]

// const props = {
//     name: 'loginForm',
//     method: 'POST',
//     action: '/perform_login',
//     inputs: inputs
// }

// const params = new URLSearchParams(window.location.search)

// ReactDOM.render(
//     <Form {...props} error={params.get('error')} />,
//     document.getElementById('container'))
