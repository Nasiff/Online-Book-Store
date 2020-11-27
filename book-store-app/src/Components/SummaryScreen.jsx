import React from 'react';
import IosAddCircle from 'react-ionicons/lib/IosAddCircle'
import IosRemoveCircle from 'react-ionicons/lib/IosRemoveCircle'
import { BrowserRouter as Router,
    Switch,
    Route,
    Link,
    useRouteMatch,
    useParams, 
    useLocation} from "react-router-dom";

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
                                {item.qty} 
                            </div>
                        </div>
                        );
    
            builtJSX.push(<div style={styles.itemContainer}>
                            <div style={{textAlign: "center"}}> ${item.price * item.qty} </div>
                        </div>
                        );
            
            subtotal += item.price * item.qty;
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

/*
function placeOrder(order, orderJSON){
    //post to service
    fetch("./Data/user.json")
    .then(res => res.json())
    .then(
        //Only accounts for successful logins for now
        (result) => {
            console.log("Result: " + result);
            this.setState({
                uid: result.uid,
                user: result,
                loggedIn: true
            });
            console.log(this.state.uid);
        },

        /* Any Errors 
        (error) => {
            console.log(error);
            this.setState({
                error
            });
            
            alert(this.state.error);
        }
    )
}
*/

function SummaryScreen(props) {

    const location = useLocation();
    const shippingInfo = location.state.shippingInfo;
    const order = {
        orderPlaced: false,
        orderNumber: ""
    };
    const OrderJSON = {
        lname: shippingInfo.lname,
        fname: shippingInfo.fname,
        address: shippingInfo.address,
        cart: props.cart
    };
   
    return (
        <div>
        <div style={styles.container}>  
            <div style={styles.containerContent}>
                <div style={styles.header}>Summary</div>
                <div style={styles.subHeader}>Shopping Cart</div>
                <div style={styles.cartContainer}>
                    <div style={styles.gridHeader}><div style={{textAlign: "left"}}>Item</div></div>
                    <div style={styles.gridHeader}><div style={{textAlign: "center"}}>Quantity</div></div>
                    <div style={styles.gridHeader}><div style={{textAlign: "center"}}>Price</div></div>
                    {buildCart(props.cart, props.updateCartFunc)}
                    <div/>
                    <div/>
                </div>
                <div>
                    <div style={styles.subHeader}>Shipping Information</div>
                    <div style={styles.label}> Street Address (Include Street Number)</div>
                    <div style={styles.text}> {OrderJSON.address.street} </div>

                    <div style={styles.label}> Province / State </div>
                    <div style={styles.text}> {OrderJSON.address.province_state} </div>

                    <div style={styles.label}> Country </div>
                    <div style={styles.text}> {OrderJSON.address.country} </div>

                    <div style={styles.label}> Zip Code </div>
                    <div style={styles.text}> {OrderJSON.address.zip} </div>

                    <div style={styles.label}> Phone </div>
                    <div style={styles.text}> {OrderJSON.address.phone} </div>
                </div>
                <div>
                <div style={styles.subHeader}>Payment Information</div>
                    <div style={styles.label}> Credit Card </div>
                    <div style={styles.text}> {location.state.credit} </div>
                </div>

                {!order.orderPlaced ? <div style={styles.next} class="button grow">
                    Place Order
                </div> : 
                <div>
                <div style={styles.subHeader}>Order Number</div>
                    <div style={styles.label}> {order.orderNumber} </div>
                </div>                    
                }
                
            </div>
        </div> 
        </div>
    );
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
        textAlign: "center",
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
        margin: "50px 0px 50px 0px",
        float: "left"
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
    },
    label: {
        marginBottom: "10px",
        fontSize: "16px",
        textAlign: "left"
    },
    text: {
        marginBottom: "10px",
        textAlign: "left"
    },
    subHeader: {
        marginTop: "20px",
        marginBottom: "20px",
        fontSize: "22px",
        color: "pink",
        textAlign: "left"
    }
}


export default SummaryScreen;