import logo from './logo.svg';
import React from 'react';
import './App.css';
import MainScreen from './Components/MainScreen.jsx'
import CartScreen from './Components/CartScreen.jsx'

import { BrowserRouter as Router,
Switch,
Route,
Link,
useRouteMatch,
useParams } from "react-router-dom";


class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      cart: []
    };
  }


  handleAddToCart = (id, name, qty, price) => {
    console.log("Adding to cart: " + id);
  }

  render(){
    return (
      <Router>
          <Switch>
            <Route path="/cart">
              <CartScreen cart={this.state.cart}/>
            </Route>
            <Route path="/">
              <MainScreen addToCart={this.handleAddToCart}/>
            </Route>
          </Switch>
      </Router>
    );
  }
}

export default App;
