import React, {useState} from 'react';
import { BrowserRouter as Router,
    Switch,
    Route,
    Link,
    useRouteMatch,
    useParams, 
    useLocation} from "react-router-dom";



function PaymentScreen (props) {

    const location = useLocation();
    var credit = "";


    return ( 
        <div>
        <div style={styles.container}>  
            <div style={styles.containerContent}>
                <div style={styles.header}>Payment</div>
                <div style={styles.label}>Add payment Information</div>    
                <div style={styles.label}> Credit Card Information </div>
                <input style={styles.inputs} type="text" value={credit} onChange={(event) => {
        credit = event.target.value;
    }} />
       
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
        textAlign: "center"
    },
    label: {
        margin: "10px",
        fontSize: "20px",
    },
    text: {
        margin: "10px"
    },
    inputs: {
        textTransform: "capitalize"
    }
}

export default PaymentScreen;