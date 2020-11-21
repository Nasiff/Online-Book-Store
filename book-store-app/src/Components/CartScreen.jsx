import React from 'react';
import logo from '../Assets/momAndPopLogo.png';
import Nav from './Nav';

class CartScreen extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          cart: this.props.cart
        };
      }
    
    componentDidMount(){
        console.log("Mounted the Cart: " + this.state)
    }

    render() {

        return ( 
        <div>
            <Nav />
            Shopping Cart Size: {this.state.cart.length}
        </div> 
        );
    }
}

export default CartScreen;