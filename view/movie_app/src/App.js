import React, { Component } from 'react';
import './App.css';
import { Route, Switch, withRouter } from "react-router-dom";
import Nav from './Components/Nav';
import Footer from './Components/Footer';
import Movies from './Components/Movies';
import MovieDetails from './Components/MovieDetails';
import Stars from './Components/Stars';
import StarDetails from './Components/StarDetails';
import Login from './Components/Login';
import Registration from './Components/Registration';
import Registered from './Components/Registered';
import ReactNotification from "react-notifications-component";
import "react-notifications-component/dist/theme.css";
import { API_BASE_URL, ACCESS_TOKEN } from './constants'
import axios from 'axios';
import UserProfile from './Components/UserProfile';
// import AddMovie from './Components/AddMovie';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      currentUser: null,
      isAuthenticated: false,
      isLoading: false
    }

    this.notificationDOMRef = React.createRef();
    this.addNotification = this.addNotification.bind(this);
    this.handleLogin = this.handleLogin.bind(this);
    this.loadCurrentUser = this.loadCurrentUser.bind(this);
    this.handleLogout = this.handleLogout.bind(this);
  }

  addNotification(title, message, type) {
    this.notificationDOMRef.current.addNotification({
      isMobile: true,
      title: title,
      message: message,
      type: type,
      insert: "top",
      container: "top-right",
      animationIn: ["animated", "fadeIn"],
      animationOut: ["animated", "fadeOut"],
      dismiss: { duration: 2500 },
      dismissable: { click: true }
    });
  }

  handleLogout() {
    localStorage.removeItem(ACCESS_TOKEN);

    this.addNotification("Wylogowywanie", "Wylogowano pomyślnie", "success");
    this.props.history.push('/movies');
    this.setState({
      currentUser: null,
      isAuthenticated: false
    });
  }

  handleLogin(data) {
    localStorage.setItem(ACCESS_TOKEN, data.accessToken);
    this.loadCurrentUser();
    this.addNotification("Logowanie", "Logowanie przebiegło pomyślnie!", "success");
    this.props.history.push('/movies');
  }

  componentDidMount() {
    this.loadCurrentUser();
  }

  loadCurrentUser() {
    if (localStorage.getItem(ACCESS_TOKEN)) {
      this.setState({
        isLoading: true
      });
      axios.get(`${API_BASE_URL}users/me`, { headers: { 'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN) } })
        .then(response => {
          this.setState({
            currentUser: response.data,
            isAuthenticated: true,
            isLoading: false
          });
        }).catch(error => {
          this.setState({
            isLoading: false
          });
        });
    }
  }

  render() {
    return (
      <div className="App">
        <Nav currentUser={this.state.currentUser} onLogout={this.handleLogout} isAuthenticated={this.state.isAuthenticated} />
        <div className="container" style={{ "paddingTop": "30px" }}>
          <ReactNotification ref={this.notificationDOMRef} />
          <Switch>
          <Route exact path='/' render={(props) => <Movies {...props} notification={this.addNotification} isAuthenticated={this.state.isAuthenticated} currentUser={this.state.currentUser} />} />
            <Route exact path='/movies' render={(props) => <Movies {...props} notification={this.addNotification} isAuthenticated={this.state.isAuthenticated} currentUser={this.state.currentUser} />} />
            {/* <Route exact path='/movies/add' component={AddMovie} /> */}
            <Route path='/movies/:id' render={(props) => <MovieDetails {...props} notification={this.addNotification} isAuthenticated={this.state.isAuthenticated} />} />
            <Route exact path='/stars' render={(props) => <Stars {...props} notification={this.addNotification} isAuthenticated={this.state.isAuthenticated} currentUser={this.state.currentUser} />}  />
            {/* <Route exact path='/stars/add' component={Stars} /> */}
            <Route path='/stars/:id' render={(props) => <StarDetails {...props} notification={this.addNotification} isAuthenticated={this.state.isAuthenticated} />} />

            <Route exact path='/users/:id/:section' render={(props) => <UserProfile {...props} isAuthenticated={this.state.isAuthenticated} />} />

            <Route exact path='/login' render={(props) => <Login {...props} isAuthenticated={this.state.isAuthenticated} onLogin={this.handleLogin} notification={this.addNotification} />} />
            <Route exact path='/registration' render={(props) => <Registration {...props} isAuthenticated={this.state.isAuthenticated} />} />
            <Route exact path='/registered' render={(props) => <Registered {...props} isAuthenticated={this.state.isAuthenticated} />} />
          </Switch>
        </div>
        <Footer />
      </div>
    );
  }
}

export default withRouter(App);
