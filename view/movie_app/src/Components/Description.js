import React, { Component } from 'react';

class Description extends Component {
    render() {
        return (
            <div className="card" style={{ "textAlign": "left", "marginTop": "20px" }}>
                <div className="card-header"><strong>Opis</strong></div>
                <div className="card-body">
                    {this.props.content}
                </div>
            </div>
        );
    }
}

export default Description;