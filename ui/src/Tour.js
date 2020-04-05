import React, {Component} from 'react';

import './Tour.css';
export default class Tour extends Component {

  constructor(props) {
    super(props);
    this.state = {
      played: false,
      name: this.getName(props)
    };
    this.getName = this.getName.bind(this);
    this.startTour = this.startTour.bind(this);
    this.getStartSquare = this.getStartSquare.bind(this);
  }

  getStartSquare(){
    const tour = this.props.tour;
    return [tour.startRow, tour.startColumn]
  }

  getName(){
    return "Tour [" + this.getStartSquare() + "] (" + this.props.tour.id + ")";
  }

  convertMoves(tour) {
    const moves = {
      "N": {row: 3, col: 0},
      "NE": {row: 2, col: 2},
      "E": {row: 0, col: 3},
      "SE": {row: -2, col: 2},
      "S": {row: -3, col: 0},
      "SW": {row: -2, col: -2},
      "W": {row: 0, col: -3},
      "NW": {row: 2, col: -2}
    };

    const id = tour.id;
    const name = this.state.name;
    const squares = tour.moves.reduce((accumulator, move) => {
      const lastSquare = accumulator[accumulator.length - 1];
      const nextMove = moves[move];
      const nextSquare = [lastSquare[0] + nextMove.row, lastSquare[1] + nextMove.col];
      return [...accumulator, nextSquare]
    }, [this.getStartSquare()]);
    return {id, name, squares}
  }

  startTour() {
    if (typeof this.props.touringCallback === 'function') {
      this.props.touringCallback(this.convertMoves(this.props.tour));
      this.setState({played: true})
    }
  }

  render() {
    return (
      <div className={"Tour" + (this.state.played ? " Played" : "")}
           onClick={this.startTour}> {this.state.name}
      </div>
    );
  }
}
