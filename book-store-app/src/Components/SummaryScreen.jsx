import React, { useState } from 'react';
import IosAddCircle from 'react-ionicons/lib/IosAddCircle'
import IosRemoveCircle from 'react-ionicons/lib/IosRemoveCircle'
import Popup from 'reactjs-popup';
import { BrowserRouter as Router,
    Switch,
    Route,
    Link,
    useRouteMatch,
    useParams, 
    useLocation,
    Redirect} from "react-router-dom";
import WebService from '../Services/WebService';
import OrderPlaced from './OrderPlaced';

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
                                {item.quantity} 
                            </div>
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



function SummaryScreen(props) {

    const location = useLocation();
    const shippingInfo = location.state.shippingInfo;
    var [open, setOpen] = useState(false);
    var [orderPlaced, setOrder] = useState(false);
    var [orderNumber, setOrderNumber] = useState("Problem Ordering");
    var [redirect, setRedirect] = useState(false);
    const OrderJSON = {
        lname: shippingInfo.lname,
        fname: shippingInfo.fname,
        address: shippingInfo.address,
        purchaseOrderItems: props.cart
    };

    const clearCart = props.clearCart;
    
    function clearAndRedirect() {
        clearCart(); 
        setRedirect(true);
    }

    if(redirect){
        return <Redirect to="/" />
    }

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

                {!orderPlaced ? <div style={styles.next} class="button grow" onClick={() => {setOpen(true)}}>
                    Place Order
                </div> : 
                <div>
                    <div style={styles.subHeader}>Order Number</div>
                    <div style={styles.label}>  {orderNumber}</div>
                    <div style={styles.next} class="button grow" onClick={() => clearAndRedirect()}>Return to Catalouge</div>
                </div>                    
                }
            </div>

            <Popup open={open} closeOnDocumentClick onClose={() => setOpen(false)}>
                <OrderPlaced 
                        closeFunc={() => setOpen(false)}
                        orderJson={OrderJSON}
                        setOrder={(orderNum) => {
                            setOrder(true);
                            setOrderNumber(orderNum);
                        }}
                        clearAndRedirect={() => clearAndRedirect()}
                    />
                    
            </Popup>

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