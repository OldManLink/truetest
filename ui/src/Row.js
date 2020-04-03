import React, {Component} from 'react';
import Square from "./Square";
import Utils from "./Utils";

export default class Row extends Component {

  constructor(props) {
    super(props);
    this.getTours = this.getTours.bind(this);
  }

  getTours(value) {
    if (typeof this.props.touringCallback === 'function') {
      this.props.touringCallback(value)
    }
  }

  visitColumn(col, visited) {
    this.refs["col" + col].visit(visited);
  }

  render() {
    return (
      <div className="Row">
        <ul>
          {Utils.range(0, this.props.cols - 1)
            .map(index => <Square
              key={"Square" + index + this.props.index}
              ref={"col" + index}
              row={this.props.index}
              column={index}
              touringCallback={this.getTours}/>
            )}
        </ul>
      </div>
    );
  }
}
