import React from 'react';
import logo from '../Assets/momAndPopLogo.png';
import Nav from './Nav';
import IosAddCircle from 'react-ionicons/lib/IosAddCircle'
import IosRemoveCircle from 'react-ionicons/lib/IosRemoveCircle'
import { BrowserRouter as Router, Link} from "react-router-dom";

const buildCart = (cart, updateCart) => {
    var builtJSX = [];
    var subtotal = 0;
    cart.forEach((item) => {

        builtJSX.push(<div style={styles.itemContainer}>
                        <div style={{textAlign: "left"}}> {item.title} </div>
                    </div>
                    );

        builtJSX.push(<div style={styles.itemContainer}>
                        <div style={{textAlign: "center"}}> 
                            <div style={styles.edit} onClick={() => updateCart(item.bid, -1)}><IosRemoveCircle className="grow" fontSize="20px" color="#347eff" /></div> 
                            {item.quantity} 
                            <div style={styles.edit} onClick={() => updateCart(item.bid, 1)}><IosAddCircle className="grow" fontSize="20px" color="#347eff" /></div></div>
                    </div>
                    );

        builtJSX.push(<div style={styles.itemContainer}>
                        <div style={{textAlign: "center"}}> ${item.price * item.quantity} </div>
                    </div>
                    );
        
        subtotal += item.price * item.quantity;
    });


    /* Space between items and subtotals */
    for(var i = 0; i < 2; i++){
        builtJSX.push(
            <div> </div>
        )
        builtJSX.push(
            <div> _ </div>
        )
        builtJSX.push(
            <div>  </div>
        )
    }


    /* Format Subtotal */
    builtJSX.push(
        <div style={styles.itemContainer}>
            <div style={{textAlign: "left"}}> Subtotal </div>
        </div>
    )

    builtJSX.push(
        <div></div>
    )

    builtJSX.push(<div style={styles.itemContainer}>
        <div style={{textAlign: "center"}}> ${subtotal.toFixed(2)} </div>
    </div>
    );

    /* Format Taxes */
    builtJSX.push(
        <div style={styles.itemContainer}>
            <div style={{textAlign: "left"}}> Taxes ( +13% ) </div>
        </div>
    )

    builtJSX.push(
        <div></div>
    )

    builtJSX.push(<div style={styles.itemContainer}>
        <div style={{textAlign: "center"}}> ${(subtotal * 0.13).toFixed(2)} </div>
    </div>
    );

    /* Format Total */
    builtJSX.push(
        <div style={styles.itemContainer}>
            <div style={{textAlign: "left", fontWeight: "bold"}}> Total </div>
        </div>
    )

    builtJSX.push(
        <div></div>
    )

    builtJSX.push(<div style={styles.itemContainer}>
        <div style={{textAlign: "center", fontWeight: "bold"}}> ${(subtotal * 1.13).toFixed(2)} </div>
    </div>
    );

    return builtJSX;
}

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
        <div style={styles.container}>  
            <div style={styles.containerContent}>
                <div style={styles.header}>Shopping Cart</div>
                <div style={styles.cartContainer}>
                    <div style={styles.gridHeader}><div style={{textAlign: "left"}}>Item</div></div>
                    <div style={styles.gridHeader}><div style={{textAlign: "center"}}>Quantity</div></div>
                    <div style={styles.gridHeader}><div style={{textAlign: "center"}}>Price</div></div>
                    {buildCart(this.props.cart, this.props.updateCartFunc)}
                    <div/>
                    <div/>
                    {(this.props.cart.length > 0) ? <Link to="/shipping"><div className="button grow" style={styles.next}>Checkout</div></Link> : <div className="button" style={styles.empty}>Empty Cart</div>}
                </div>
            </div>
        </div> 
        </div>
        );
    }
}

const styles = {
    container: {
        display: "flex",
        //backgroundColor:"yellow",
        textAlign: "center",
        alignContent: "center",
        alignItems: "center",
        justifyContent: "center",
        justifyItems: "center",
        alignSelf: "center",
        justifySelf: "center",
        width: "100%",
    },
    containerContent: {
        width: "80%"
    },
    header: {
        color: "#0184C7",
        fontSize: "33px",
        marginTop: "27px",
        textAlign: "left",
    },
    cartContainer: {
        margin: "20px 0 20px 0",
        display: "grid",
        gridTemplateColumns: "5fr 1fr 1fr"
    },
    gridHeader: {
        textAlign: "left",
        justifyItems: "center",
        alignItems: "center",
        color: "#2E2E2E",
        fontSize: "20px",
        paddingBottom: "10px",
        borderBottom: "5px solid #707070",
    },
    itemContainer: {
        margin: "10px 0 0px 0"
    },
    next: {
        borderRadius: "50px",
        fontWeight: "bold",
        fontSize: "18px",
        margin: "50px 0px 0px 0px",
        padding: "10px 20px 10px 20px",
        float: "right"
    },
    edit: {
        display: "inline-block",
        verticalAlign: "middle",
        margin: "0 5px 0 5px"
    },
    empty: {
        borderRadius: "50px",
        fontWeight: "bold",
        fontSize: "18px",
        margin: "50px 0px 0px 0px",
        padding: "10px 20px 10px 20px",
        float: "right",
        backgroundColor: "grey"
    }
}

export default CartScreen;