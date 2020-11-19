import React from 'react';
import cart from '../Assets/cart.png'

class BrowseHeader extends React.Component {
    va
    render() {

        return ( 
        <div className="inline" id="browseHeader">
            <div className="inline" id="browseTitle">
                <h3 className="center lg-view">Browse By Category</h3>
                <h3 className="center sm-view">Category</h3>
            </div>
            <ul className="inline" id="catergories">
                    <li className="category grow">Science</li>
                    <li className="category grow">English</li>
                    <li className="category grow">Buisness</li>
                    <li className="category grow">Romance</li>
                    <li className="category grow">Fantasy</li>
                </ul>
            <div className="inline growX" id="cart">
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
        margin: "5px",
        overflow: "hidden"
    }
}

export default BrowseHeader;