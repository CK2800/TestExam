import React, { Component } from "react";
import {Redirect} from "react-router-dom";



class Login extends Component {
  
  render() {    
    console.log(this.props);
    if (this.props.token !== undefined)
      return (<Redirect to="/" />); // We could have redirected via this.props.history.push("/") if handler was in Login.js
    else
      return (          
      <React.Fragment>
        <h2>Login</h2>
        <form onSubmit={(event) => this.props.onSubmit(event)}>
          Username
          <input name="user" onChange={(event) => this.props.onChange(event)} />
          <br />
          Password
          <input name="password" onChange={(event) => this.props.onChange(event)} />
          <br />
          <input type="submit" />
        </form>
      </React.Fragment>
    );
  }
}

export default Login;
