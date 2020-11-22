import logo from './logo.svg';
import React from 'react';
import './App.css';
import './Styles/Main.css';
import MainScreen from './Components/MainScreen.jsx'
import CartScreen from './Components/CartScreen.jsx'
import Nav from './Components/Nav'
import { BrowserRouter as Router,
Switch,
Route,
Link,
useRouteMatch,
useParams } from "react-router-dom";
import { AnimatedSwitch } from 'react-router-transition';


class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      cart: []
    };
  }


  handleAddToCart = (id, title, author, qty, price) => {
    var item = {
      bid: id,
      title: title,
      author: author,
      qty: qty,
      price: price
    }
    var hasBook = this.state.cart.some(book => book.bid === id);
    console.log("has: " + hasBook);

    if(hasBook) {
      var index = this.state.cart.findIndex(book => {
        return book.bid === id
      });

      var newCart = this.state.cart;
      var newBook = newCart[index];

      console.log("index: " + index + " new Book: " + newBook)

      newBook.qty = newBook.qty + qty;
      newCart[index] = newBook;
      this.setState({cart: newCart}); 

      console.log("Update book");
    } else {
      console.log("Adding new book");
      this.setState(prevState => ({
        cart: [...prevState.cart, item]
      }));
    }
  }

  handleUpdateCart = (id, updateVal) => {
    var hasBook = this.state.cart.some(book => book.bid === id);
    console.log("has: " + hasBook);

    if(hasBook) {
      var index = this.state.cart.findIndex(book => {
        return book.bid === id
      });

      var newCart = this.state.cart;
      var newBook = newCart[index];

      console.log("index: " + index + " new Book: " + newBook)

      newBook.qty = newBook.qty + updateVal;
      if(newBook.qty < 1){
        //Remove the book if qty is 0
        var remCart = newCart.filter(book => {
          return book.bid != id
        })

        this.setState({cart: remCart})
        
      } else {
        newCart[index] = newBook;
        this.setState({cart: newCart}); 
      }

      console.log("Update book");
    }

  }

  render(){
    return (
      <Router>
        <Switch>
            <Route path="/cart">
              <CartScreen cart={this.state.cart} updateCartFunc={this.handleUpdateCart}/>              
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
