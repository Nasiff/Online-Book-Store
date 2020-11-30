import logo from './logo.svg';
import React from 'react';
import './App.css';
import './Styles/Main.css';
import MainScreen from './Components/MainScreen.jsx'
import CartScreen from './Components/CartScreen.jsx'
import LoginScreen from './Components/LoginScreen.jsx'
import RegisterScreen from './Components/RegisterScreen.jsx'
import Nav from './Components/Nav'
import WebService from './Services/WebService';
import { BrowserRouter as Router,
Switch,
Route,
Link,
useRouteMatch,
useParams,
Redirect} from "react-router-dom";
import AccountScreen from './Components/AccountScreen';
import ShippingScreen from './Components/ShippingScreen';
import SummaryScreen from './Components/SummaryScreen';
import AdminAnalytics from './Components/AdminAnalytics';

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      cart: [],
      loggedIn: false,
      type: null,
      user: null,
      uid: null,
      redirect: "/"
    };
  }

  handleLogin = async (email, password) => {
    // Send request to server to login with user and password as headers
    const headers = { 
      'Content-Type': 'application/json',
      'email' : email,
      'password' : password 
    }

    var message = null;

    console.log(email, password);

    await fetch(WebService.uri + "/user", { headers })
    .then(res => res.json())
    .then(
        //Only accounts for successful logins for now
        (result) => {
            console.log("Result: " + result);
            if(result.result.successful){
              this.setState({type: result.result.user_type});
            }
            this.setState({
                uid: result.result.uid,
                user: result.result,
                loggedIn: result.result.successful,
            });
            console.log("Login was successful: " + this.state.uid);

            if(!this.state.loggedIn){
              //alert("Error logging in: " + result.result.error);
              message = result.result.error;
              console.log(message);
            } 

        },

        /* Any Errors */
        (error) => {
            console.log(error);
            this.setState({
                error
            });
            
            alert(this.state.error);
        }
    )

    return message;
  }

  setRedirect = async (page) => {
    console.log(page);
    await this.setState({redirect: page});
    console.log(this.state.redirect);
  }

  handleSignout = () => {
    // Just clear the user fields
    this.setState({
      loggedIn: false,
      type: null,
      user: null,
      uid: null,
    })
  }

  handleClearCart = () => {
    console.log("clearing cart");
    this.setState({
      cart: []
    })
  }


  handleAddToCart = (id, title, author, qty, price) => {
    var item = {
      bid: id,
      title: title,
      author: author,
      quantity: qty,
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

      newBook.quantity = newBook.quantity + qty;
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

      newBook.quantity = newBook.quantity + updateVal;
      if(newBook.quantity < 1){
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
              <Nav loggedIn={this.state.loggedIn}/>
              <CartScreen cart={this.state.cart} updateCartFunc={this.handleUpdateCart}/>              
            </Route>
            <Route path="/account">
              <Nav loggedIn={this.state.loggedIn}/>
              <AccountScreen loggedIn={this.state.loggedIn} userInfo={this.state.user} signOutFunc={this.handleSignout}/>              
            </Route>
            <Route path="/login">
              <Nav loggedIn={this.state.loggedIn}/>
              <LoginScreen loggedIn={this.state.loggedIn} redirect={this.state.redirect} loginFunc={this.handleLogin}/>
            </Route>
            <Route path="/register">
              <Nav loggedIn={this.state.loggedIn}/>
              <RegisterScreen loggedIn={this.state.loggedIn} loginFunc={this.handleLogin}/>
            </Route>
            <Route path="/shipping">
              <Nav loggedIn={this.state.loggedIn}/>
              <ShippingScreen redirectFunc={this.setRedirect} uid={this.state.uid} loggedIn={this.state.loggedIn} userInfo={this.state.user}/>
            </Route>
            <Route path="/summary">
              <Nav loggedIn={this.state.loggedIn}/>
              <SummaryScreen cart={this.state.cart} clearCart={this.handleClearCart} updateCartFunc={this.handleUpdateCart}/>
            </Route>
            <Route path="/admin">
              <Nav loggedIn={this.state.loggedIn}/>
              <AdminAnalytics uid={this.state.uid} type={this.state.type}/>
            </Route>
            <Route path="/">
              <Nav loggedIn={this.state.loggedIn}/>
              <MainScreen uid={this.state.uid} addToCart={this.handleAddToCart}/>
            </Route>
          </Switch>
      </Router>
    );
  }
}

export default App;
