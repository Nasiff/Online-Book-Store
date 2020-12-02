import React from 'react';
import Popup from 'reactjs-popup';
import MdClose from 'react-ionicons/lib/MdClose'
import WebService from '../Services/WebService';
import Rating from '@material-ui/lab/Rating';
import Box from '@material-ui/core/Box';
import Typography from '@material-ui/core/Typography';
import IosRefresh from 'react-ionicons/lib/IosRefresh';

import { BrowserRouter as Router,
    Switch,
    Route,
    Link,
    useRouteMatch,
    useParams,
    Redirect} from "react-router-dom";
import { Web } from '@material-ui/icons';


class OrderPlaced extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            postStatus: "STANDBY",
            orderInfo: null,
            orderCost: null,
            message: null,

      }
    }
    
    placeOrder = (orderJSON) => {

        console.log(orderJSON);
    
        const data = {
            method: 'POST', 
            mode: 'cors', // no-cors, *cors, same-origin
            cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
            credentials: 'same-origin', // include, *same-origin, omit
            headers: {
              'Content-Type': 'application/json',
            },
            redirect: 'follow', // manual, *follow, error
            referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
            body: JSON.stringify(orderJSON) // body data type must match "Content-Type" header
          }
    
    
        const url = WebService.uri + "/purchase";
        console.log("URL" + url);


        fetch(url, data)
        .then(res => res.json())
        .then(
            //Only accounts for successful logins for now
            (result) => {
                console.log(result);
                if(result.result.successful){
                    this.setState({
                        orderInfo: result.result.order_info,
                        orderCost: result.result.order_cost,
                        message: result.result.message,
                        postStatus: "SENT"
                    })
                    this.props.setOrder(this.state.orderInfo.order_id);
                    //alert("Successful Order: " + result.result.message);
                } else {
                    this.setState({
                        message: result.result.error,
                        postStatus: "FAILED"
                    })
                    alert("Error while Ordering: " + result.result.error);
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
    }

    componentDidMount(){
        console.log("Mounted Purchase: " + this.props.orderJson);
        this.placeOrder(this.props.orderJson);
    }

    render() {
        // Can do error check to make sure the title doesnt exceed 30 characters
        return ( 
            <div className="scrollHide" style={styles.modal}>
                <a className="close" style={styles.close} onClick={() => this.props.closeFunc()}>
                    <MdClose fontSize="40px" color="white"/>
                </a>
                {this.state.postStatus == "STANDBY" ?  
                    <div>
                        <div style={styles.header}>Placing Order</div>
                    </div> 
                    : 
                    <div> 
                        <div style={styles.header}>Order</div>
                        <div>{this.state.message}</div>
                        {this.state.orderInfo && this.state.orderCost ? 
                            <div>
                                <div style={styles.text}>Please keep a copy of the order number for reference: <b>{this.state.orderInfo.order_id}</b></div>
                                <div style={styles.text}>Purchase Date: <b>{this.state.orderInfo.purchase_date}</b></div>
                                <div style={styles.text}>Order Status: <b>{this.state.orderInfo.status}</b></div>
                                <div style={styles.text}>Total: <b>{this.state.orderCost.total}</b></div>
                                <Link to="/"><div className="button grow" onClick={() => this.props.clearAndRedirect()}>Return to Catalouge</div></Link>
                            </div> : null
                        }
                    </div>
                }
            </div>
        
        );
    }
}

const styles = {
    modal: {
        backgroundColor: "white",
        border: "5px solid black",
        borderRadius: "10px",
        padding: "10px",
        width: "80vw",
        height: "80vh",
        overflow: "auto",
        textAlign: "center"
    },
    close: {
        cursor: "pointer",
        position: "absolute",
        display: "block",
        lineHeight: "20px",
        right: "-15px",
        top: "-15px",
        padding: "3px 3px 0px 3px",
        backgroundColor: "black",
        borderRadius: "500px",
        verticalAlign: "middle",
    },
    header: {
        color: "#0184C7",
        fontSize: "33px",
        marginTop: "27px",
        textAlign: "center",
        marginBottom: "30px"
    },
    text: {
        margin: "10px"
    }
}

export default OrderPlaced;