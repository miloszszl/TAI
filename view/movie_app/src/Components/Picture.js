import React, { Component } from 'react';

class Picture extends Component {
    render() {
        return (
            <div className="col-md-3">
                <div className="MovieShortDetails img-thumbnail" >
                    <div style={{ "overflow": "hidden" }}>
                        <img className="thumbnailImage" src={'data:image/jpeg;base64,' + this.props.file} alt="Obraz" /> 
                    </div>
                </div>
            </div>
        );
    }
}

export default Picture;