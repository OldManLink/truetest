import React, {Component} from 'react';
import Utils from "./Utils";
import Row from "./Row";

export default class Board extends Component {

  constructor(props) {
    super(props);
    this.state = {width: props.width, height: props.height};
    this.getTours = this.getTours.bind(this);
  }

  getTours(value) {
    if (typeof this.props.touringCallback === 'function') {
      this.props.touringCallback(value)
    }
  }

  playTour(tour) {
    return this.basicTour(tour, true, 150);
  }

  unPlayTour(tour) {
    const reverseTour = tour
      ? {...tour, squares: [...tour.squares].reverse().slice(0,-1)}
      : undefined;
    return reverseTour
      ? this.basicTour(tour, false, 50).then(Utils.sleep(150))
      : Promise.resolve()
  }

  async basicTour(tour, visited, delay) {
    return tour.squares.reduce((playPromise, square) => {
      return playPromise.then(touring => {
        this.refs["row" + square[0]].visitColumn(square[1], visited);
        return Utils.sleep(delay)
      })
    }, Promise.resolve(Utils.sleep(delay)))
  }

  render() {
    return (
      <div className="Board">
        <ul>
          {Utils.range(0, this.state.height - 1).reverse()
            .map(rowIndex =>
              <Row key={"Row-" + rowIndex}
                   ref={"row" + rowIndex}
                   touringCallback={this.getTours}
                   index={rowIndex}
                   width={this.state.width}/>
            )}
        </ul>
      </div>
    );
  }
}
