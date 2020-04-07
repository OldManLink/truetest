import React, {Component} from 'react';
import Api from "./Api";
import Tour from "./Tour";
import reactLogo from './images/react.svg'

import Board from "./Board";

import './App.css';

export default class App extends Component {

  constructor(props) {
    super(props);
    this.state = {title: '', rows: 10, cols: 10, maxTours: 5, tours: [], fetching: false, lastTour: undefined};
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
        columns: this.state.cols
      },
      startSquare: {
        row: value.row,
        column: value.column
      },
      max: this.state.maxTours
    };
    this.setState({tours: [], fetching: true});
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
               rows={this.state.rows}
               cols={this.state.cols}
               touringCallback={this.getTours}/>
        {this.state.tours.length === 0
          ? (this.state.fetching
              ? <div>
                  <div>Fetching, please wait...</div>
                  <img width="200" height="200" src={reactLogo} className="App-logo" alt="React Logo"/>
              </div>
              : this.state.title.length === 0
                 ? null
                 : <div>Click any square to generate {this.state.maxTours} of its possible tours.</div>
          )
          : (
            <div>
              <div>Click any Tour to (re)play it, or click any</div>
              <div>square above to generate another {this.state.maxTours} tours.</div>
              {this.state.tours.map(tour =>
                <Tour key={"Tour-" + tour.id}
                      tour={tour}
                      touringCallback={this.playTourHandler}/>)
              }
            </div>)
        }
      </div>
    );
  }
}
