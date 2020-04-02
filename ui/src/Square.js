import React, {Component} from 'react';
import './Square.css';

export default class Square extends Component {

  constructor(props) {
    super(props);
    this.state = {visited: false, hover: false};
    this.getTours = this.getTours.bind(this);
  }

  getTours() {
    if (typeof this.props.touringCallback === 'function') {
      const value = (({row, column}) => ({row, column}))(this.props);
      this.props.touringCallback(value)
    }
  }

  visit(visited) {
    this.setState(state => {
      return {visited: visited}
    });
  }

  isOddSquare() {
    const rowOffset = this.isEven(this.props.column) ? 1 : 0;
    return this.isEven(this.props.row + rowOffset) ;
  }

  isEven(value) {
    return value % 2 === 0
  }

  render() {
    return (
      <div className={"Square"
      + (this.state.visited ? " Square-Visited" : "")
      + (this.isOddSquare() ? " Square-Odd" : " Square-Even")
      }
           onClick={this.getTours}> {""}
      </div>
    );
  }
}
