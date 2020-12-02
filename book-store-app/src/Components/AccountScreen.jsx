import React from 'react';
import BrowseHeader from './BrowseHeader'
import SearchBar from './SearchBar'
import Filters from './Filters'
import Books from './Books'
import Nav from './Nav'
import WebService from '../Services/WebService'
import { BrowserRouter as Router,
    Switch,
    Route,
    Link,
    useRouteMatch,
    useParams, 
    Redirect} from "react-router-dom";

class AccountScreen extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          redirect: "/login",
          shippingInfo: null,
          errorMessage: null,
        };

      }
    
    componentDidMount(){
        console.log("Mounted the Cart: " + this.state);
        this.getShippingInformation();
    }

    handleSubmit = (event) => {
        event.preventDefault();
        this.props.signOutFunc();
    }

    getShippingInformation = () => {

        //Request to get shipping information

        const headers = { 
            'Content-Type': 'application/json',
            'uid' : this.props.userInfo.uid
          }
        
        console.log(headers);
        fetch(WebService.uri + "/address", { headers })
          .then(res => res.json())
          .then(
              //Only accounts for successful logins for now
              (result) => {
                  console.log("Result: " + result);
                  if(result.result.successful){
                    this.setState({
                        shippingInfo: result.result                   
                      });
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

    render() {
        if(!this.props.loggedIn){
            return <Redirect to={this.state.redirect} />;
        } 
        
        return ( 
            <div>
            <div id="banner2" style={styles.container}>  
                <div id="form" style={styles.containerContent}>
                    <div style={styles.header}>Account Summary</div>

                    <div style={styles.subHeader}>Account Information</div>

                    <div style={styles.label}> Name </div>
                    <div style={styles.info}>{this.props.userInfo.fname} {this.props.userInfo.lname}</div>

                    <div style={styles.label}> Email </div>
                    <div style={styles.info}> {this.props.userInfo.email} </div>

                    <div style={styles.label}> Password </div>
                    <div style={styles.info}> ******* </div>

                    <div style={styles.label}> User Type </div>
                    <div style={styles.info}> {this.props.userInfo.user_type} </div>
                    
                    <div style={styles.subHeader}>Shipping Information</div>

                    {this.state.shippingInfo != null ? (
                        <div style={{marginBottom: "20px"}}>
                            <div style={styles.label}> Street </div>
                            <div style={styles.info}> {this.state.shippingInfo.street} </div>

                            <div style={styles.label}> Province </div>
                            <div style={styles.info}> {this.state.shippingInfo.province_state} </div>

                            <div style={styles.label}> Country </div>
                            <div style={styles.info}> {this.state.shippingInfo.country} </div>

                            <div style={styles.label}> ZIP </div>
                            <div style={styles.info}> {this.state.shippingInfo.zip} </div>

                            <div style={styles.label}> Phone </div>
                            <div style={styles.info}> {this.state.shippingInfo.phone} </div>  
                        </div>
                    ) : (<div style={{marginBottom: "20px"}}>No Shipping Information Associated With the Account</div>)}

                    <div className="button grow" onClick={this.handleSubmit}>Sign Out</div>

                </div>
            </div> 
            </div>
        );
    }
}

const styles = {
    container: {
        display: "flex",
        backgroundColor:"pink",
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
        minWidth: "60%",
        maxWidth: "80%",
        textAlign: "center",
        alignContent: "center",
        alignItems: "center",
        justifyContent: "center",
        justifyItems: "center",
        alignSelf: "center",
        justifySelf: "center",
        minHeight: "100vh",
        backgroundColor: "white"
    },
    header: {
        color: "#0184C7",
        fontSize: "33px",
        marginTop: "27px",
        textAlign: "center",
        marginBottom: "30px"
    },
    form: {
        display: "flex"
    },
    inputs: {
        display: "block",
        textAlign: "center",
        margin: "auto",
        marginBottom: "20px",
        padding: "5px"
    },
    label: {
        margin: "10px"
    },
    footer: {
        margin: "20px"
    },
    signUp: {
        color: "pink",
        fontWeight: "bold"
    },
    subHeader: {
        fontSize: "25px",
        textDecoration: "underline",
        margin: "25px"
    }
}

export default AccountScreen;