import React from 'react';
import ReactDOM from 'react-dom';

import App from './App';
import Api from "./Api";

jest.mock('../src/Api');

describe('App tests', () => {

  it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<App/>, div);
    ReactDOM.unmountComponentAtNode(div);
  });

  it('getSummary function should called exactly once', () => {
    expect(Api.getSummary.mock.calls.length).toBe(1);
  });
});
