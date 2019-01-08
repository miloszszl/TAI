import React, { Component } from 'react';

class UserAvatar extends Component {
    render() {
        return (
            <div style={{ "overflow": "hidden" }}>
                <img className="avatar-big" src={'data:image/jpeg;base64,' + this.props.file} alt="Obraz" />
            </div>
        );
    }
}

export default UserAvatar;