import React from 'react';
import cart from '../Assets/cart.png'

class BrowseHeader extends React.Component {
    
    render() {
        return ( 
        <div className="inline" id="browseHeader">
            <div className="inline" id="browseTitle">
                <h3 className="center lg-view">Browse By Category</h3>
                <h3 className="center sm-view">Category</h3>
            </div>
            <ul className="inline" id="catergories">
                    <li className="category">Science</li>
                    <li className="category">English</li>
                    <li className="category">Buisness</li>
                    <li className="category">Romance</li>
                    <li className="category">Fantasy</li>
                </ul>
            <div className="inline" id="cart">
                <img style={styles.cart} src={cart} alt="Logo"></img>
                <h3>Cart</h3>
            </div>
        </div> 
        );
    }
}

const styles = {
    cart: {
        height: "35%",
        margin: "5px"
    }
}

export default BrowseHeader;