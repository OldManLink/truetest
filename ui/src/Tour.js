import React, {Component} from 'react';
import './Tour.css';

export default class Tour extends Component {

  constructor(props) {
    super(props);
    this.state = {played: false};
    this.startTour = this.startTour.bind(this);
  }

  startTour() {
    if (typeof this.props.touringCallback === 'function') {
      this.props.touringCallback(this.props.tour);
      this.setState({played: true})
    }
  }

  render() {
    return (
      <div className={"Tour" + (this.state.played ? " Played" : "")}
           onClick={this.startTour}> {this.props.tour.name}
      </div>
    );
  }
}
