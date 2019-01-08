import React, { Component } from 'react';
import '../Styles/Loader.css';

class Loader extends Component {
    render() {
        return (
            <div className="d-flex justify-content-center" style={{ 'padding': "8px 0px" }}>
                <div className="loader"></div>
            </div>
        );
    }
}

export default Loader;