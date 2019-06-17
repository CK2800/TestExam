import React, { Component } from "react";
import { BrowserRouter, Route, Switch, Redirect } from "react-router-dom";
import Error from "./components/Error";
import Navigation from "./components/Navigation";
import Login from "./components/Login";
import Home from "./components/Home";
import jwt_decode from "jwt-decode";
import Starwars from "./components/Starwars";

const loginURL = "https://kramath.dk/jwtbackend/api/login";


function handleHttpErrors(result) {
  if (!result.ok) {
    return Promise.reject({ status: result.status, fullError: result.json() });
  }
  return result.json();
}

function makeOptions(method, body, token) {
  var opts = {
    method: method,
    headers: {
      "Content-type": "application/json",
      "x-access-token": token
    }
  };
  if (body) {
    opts.body = JSON.stringify(body);
  }
  return opts;
}

class App extends Component {
  constructor(props) {
    super(props);
    this.state = { username: "" };
  }

  componentDidMount() {
    let token = sessionStorage.getItem("ca3token");
    if (token !== null) {
      let decode = jwt_decode(token);
      this.setState({ username: decode.username, token: token });
    }
  }

  handleSubmit = evt => {
    evt.preventDefault();
    let body = this.state;
    let options = makeOptions("POST", body);
    fetch(loginURL, options)
    .then(handleHttpErrors)
    .then(res => this.setState({ ...this.state, token: res.token }))
    .then(() => sessionStorage.setItem("ca3token", this.state.token));
    // alert("You are now logged in as " + this.state.username); //current solution. the code below refreshes page
    
    // window.location.href = "/"; 
  };

  onChange = evt => {
    const value = evt.target.value;
    if (evt.target.name === "user") {
      this.setState({ ...this.state, username: value });
    }
    if (evt.target.name === "password") {
      this.setState({ ...this.state, password: value });
    }
  };  

  logout = () => {
    sessionStorage.removeItem("ca3token");
    console.log("logging out");
    // document.location.reload();
    this.setState({token: undefined});
  };

  render() {
    return (
      <BrowserRouter>
        <Navigation />
        <Switch>
          <Route
            path="/"
            exact
            render={props => (
              <Home
                {...props}
                username={this.state.username}
                logout={this.logout}
                token={this.state.token}
              />
            )}
          />
          <Route
            path="/login"
            render={props => (
              <Login
                {...props}
                onChange={this.onChange}
                token={this.state.token}
                onSubmit={this.handleSubmit}
              />
            )}
          />
          <Route
            path="/starwars"
            render={props => (
              <Starwars
                {...props}
                token={this.state.token}
                errors={this.handleHttpErrors}
              />
            )}
          />
          <Route component={Error} />
        </Switch>
      </BrowserRouter>
    );
  }
}

export default App;
