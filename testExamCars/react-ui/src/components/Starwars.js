import React, { Component } from "react";

const claus = "https://kramath.dk/jwtbackend/api/info/fetch";

function makeOptions(token) {
  var opts = {
    method: "GET",
    headers: {
      "Content-type": "application/json",
      "x-access-token": token
    }
  };
  return opts;
}

function handleHttpErrors(result) {
  if (!result.ok) {
    return Promise.reject({ status: result.status, fullError: result.json() });
  }
  return result.json();
}

export default class Starwars extends Component {
  state = {
    characters: []
  };

  fetchData = async () => {
    let options = makeOptions(this.props.token);
    let characters = await fetch(claus, options).then(handleHttpErrors);
    // characters.shift();
    this.setState({ characters });
    // let res = await fetch(swapi).then(res => res.json());
    // let res2 = await fetch(swapi2).then(res => res.json());
    // let res3 = await fetch(swapi3).then(res => res.json());
    // let res4 = await fetch(swapi4).then(res => res.json());
    // this.setState({ characters: [res,res2,res3,res4] });
  };

  showTimeSpent = () => {
    let array = this.state.characters;
    let msg = "";
    if (array.length > 1) {
      msg = array[0].msg;
      console.log(msg);
    }
    return msg;
  };

  render() {
    return (
      <div>
        <h1>Starwars table</h1>
        <table>
          <tbody>
            <tr>
              <th>Name</th>
              <th>Gender</th>
              <th>Height</th>
              <th>Birth Year</th>
              <th>Mass</th>
              <th>Hair Color</th>
              <th>Skin Color</th>
              <th>Eye Color</th>
            </tr>
            {this.state.characters.map((data, index) => (
              <tr key={index}>
                <td>{data.name}</td>
                <td>{data.gender}</td>
                <td>{data.height}</td>
                <td>{data.birth_year}</td>
                <td>{data.mass}</td>
                <td>{data.hair_color}</td>
                <td>{data.skin_color}</td>
                <td>{data.eye_color}</td>
              </tr>
            ))}
          </tbody>
        </table>
        <button onClick={this.fetchData}>Get data</button>
        <br />
        <h3>{this.showTimeSpent()}</h3>
      </div>
    );
  }
}
