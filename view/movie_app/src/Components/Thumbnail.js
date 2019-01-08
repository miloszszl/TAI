import React, { Component } from 'react';
import '../Styles/Thumbnail.css';
import { Link } from "react-router-dom";
import ImageLoader from 'react-image-file';

class Thumbnail extends Component {
    render() {
        return (
            <div className="cl-xs-12 col-sm-6 col-md-4 col-lg-3 p-1">
                <div className="Thumbnail img-thumbnail" >
                    <Link to={this.props.url}>
                        <div style={{ "overflow": "hidden", "border":"solid 1px lightgrey" }}>
                            {/* <ImageLoader className="thumbnailImage" file={this.props.file} alt={this.props.title} /> */}
                            <img className="thumbnailImage" src={'data:image/jpeg;base64,' + this.props.file} alt={this.props.title} />
                        </div>
                        <div className="caption" style={{ "padding": "5px" }}>
                            {this.props.title}
                            <div>
                                {this.props.rate}/5 <i className="fas fa-star" style={{ "color": "#f4c842" }}></i>
                            </div>
                        </div>
                    </Link>
                </div>
            </div>
        );
    }
}

export default Thumbnail;