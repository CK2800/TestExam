import React, { Component } from "react";

class Home extends Component {

  render() {
    return (
      <React.Fragment>
        <h1>Hello {this.props.username || "anonymous user"} </h1>
        <button onClick={this.props.logout}>Logout</button>
        <br/>
        Your current token:
        <br/>
        <input key={this.props.token} value={this.props.token ||""} readOnly></input>
      </React.Fragment>
    );
  }
}

export default Home;
