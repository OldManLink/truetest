import React, {Component} from 'react';
import ReactDOM from 'react-dom';

import './Canvas.css';
export default class Canvas extends Component {

  constructor(props) {
    super(props);
    this.state = {canvas: undefined, context: undefined};
  }

 componentDidMount() {
   const canvas = ReactDOM.findDOMNode(this);
   const context = canvas.getContext('2d');

   this.setState({canvas: canvas, context: context, hovered: false});
 }

 clear(){
   const canvas = this.state.canvas;
   this.state.context.clearRect(0, 0, canvas.width, canvas.height)
 }

  drawLine(fromSquare, toSquare, count){
    const context = this.state.context;

    context.beginPath();
    context.moveTo(fromSquare[1] * 42 + 21, fromSquare[0] * 42 + 21);
    context.lineTo(toSquare[1] * 42 + 21, toSquare[0] * 42 + 21);
    context.fill();
    context.lineWidth = 4;
    context.strokeStyle = `hsla(${count * 3}deg, 100%, 50%)`; // 'rgba(255, 0, 0, 0.6)';
    context.stroke();
  }

  render() {
    return <canvas
      width={this.props.width}
      height={this.props.height}
    />
  }
}
