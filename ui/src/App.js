import React, {Component} from 'react';
import Api from "./Api";
import Tour from "./Tour";

import './App.css';
import Board from "./Board";

export default class App extends Component {

  constructor(props) {
    super(props);
    this.state = {title: '', tours: [], rows: 10, columns: 10, lastTour: undefined};
    this.getTours = this.getTours.bind(this);
    this.playTourHandler = this.playTourHandler.bind(this);
  }

  async playTourHandler(tour) {
    this.refs.board.unPlayTour(this.state.lastTour);
    this.setState(state => {
      this.refs.board.playTour(tour);
      return {lastTour: tour}
    })
  }

  async componentDidMount() {
    Api.getSummary(summary => {
      this.setState({
        title: summary.content
      });
    });
  }

  getTours(value) {
    const request = {
      board: {
        rows: this.state.rows,
        columns: this.state.columns
      },
      startingSquare: {
        row: value.row,
        column: value.column
      }
    };
    this.setState({tours: []});
    this.refs.board.unPlayTour(this.state.lastTour)
      .then(
        Api.getTours(request, response => {
          this.setState({
            tours: response.tours,
            lastTour: undefined
          });
        }));
  }

  render() {
    return (
      <div className="App">
        <header><h1>{
          this.state.title.length === 0
            ? "Loading, please wait..."
            : "Welcome to the " + this.state.title
        }</h1></header>
        <Board ref="board"
               width={10}
               height={10}
               touringCallback={this.getTours}/>
        {this.state.tours.length === 0
          ? (
            <div><br/>Click any square to generate its possible tours.</div>
          )
          : (
            <div>
              <ul>
                {this.state.tours.map(tour =>
                  <Tour key={"Tour-" + tour.id}
                        tour={tour}
                        touringCallback={this.playTourHandler}/>)
                }
              </ul>
              <br/>"[Click any Tour to (re)play it)]"
            </div>)
        }
      </div>
    );
  }
}
