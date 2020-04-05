import React, {Component} from 'react';
import Utils from './Utils';
import Row from "./Row";
import Canvas from "./Canvas";

import './Board.css';

export default class Board extends Component {

  constructor(props) {
    super(props);
    this.state = {rows: props.rows, cols: props.cols, count: props.rows * props.cols};
    this.invertY = this.invertY.bind(this);
    this.getTours = this.getTours.bind(this);
  }

  invertY([y, x]) {
    return [this.state.rows - (y + 1), x]
  }

  getTours(value) {
    if (typeof this.props.touringCallback === 'function') {
      this.props.touringCallback(value)
    }
  }

  playTour(tour) {
    return this.basicTour(tour, true, 5000 / this.state.count);
  }

  unPlayTour(tour) {
    this.refs.canvas.clear();
    return (tour
      ? {...tour, squares: [...tour.squares].reverse().slice(0, -1)}
      : undefined)
      ? this.basicTour(tour, false, 250 / this.state.count).then(Utils.sleep(1000))
      : Promise.resolve()
  }

  async basicTour(tour, visited, delay) {
    var previousSquare = undefined;
    let count = 0;
    return tour.squares.reduce((playPromise, square) => {
      return playPromise.then(touring => {
        this.refs["row" + square[0]].visitColumn(square[1], visited);
        if (visited && previousSquare) {
          this.refs.canvas.drawLine(this.invertY(previousSquare), this.invertY(square), count++);
        }
        previousSquare = square;
        return Utils.sleep(delay)
      })
    }, Promise.resolve(Utils.sleep(delay)))
  }

  render() {
    return (
      <div className="Board">
        <Canvas ref="canvas" width={this.state.cols * 42} height={this.state.rows * 42}/>
        <div>
          {Utils.range(0, this.state.rows - 1).reverse()
            .map(rowIndex =>
              <Row key={"Row-" + rowIndex}
                   ref={"row" + rowIndex}
                   touringCallback={this.getTours}
                   index={rowIndex}
                   cols={this.state.cols}/>
            )}
        </div>
      </div>
    );
  }
}
