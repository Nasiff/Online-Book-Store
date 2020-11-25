import React from 'react';
import BrowseHeader from './BrowseHeader'
import SearchBar from './SearchBar'
import Filters from './Filters'
import Books from './Books'
import Nav from './Nav'
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
        };

      }
    
    componentDidMount(){
        console.log("Mounted the Cart: " + this.state)
    }

    handleSubmit = (event) => {
        event.preventDefault();
        this.props.signOutFunc();
    }

    getShippingInformation = () => {
        //Request to get shipping information
    }

    render() {
        if(!this.props.loggedIn){
            return <Redirect to={this.state.redirect} />;
        } 
        
        return ( 
            <div>
            <div style={styles.container}>  
                <div style={styles.containerContent}>
                    <div style={styles.header}>Register</div>

                    <div style={styles.subHeader}>Account Information</div>

                    <div style={styles.label}> First Name </div>
                    <div style={styles.info}> {this.props.userInfo.fname} </div>

                    <div style={styles.label}> Last Name </div>
                    <div style={styles.info}> {this.props.userInfo.lname} </div>

                    <div style={styles.label}> Email </div>
                    <div style={styles.info}> {this.props.userInfo.email} </div>

                    <div style={styles.label}> Password </div>
                    <div style={styles.info}> ******* </div>

                    <div style={styles.label}> User Type </div>
                    <div style={styles.info}> {this.props.userInfo.user_type} </div>

                    <div style={styles.subHeader}>Shipping Information</div>


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
        width: "80%",
        textAlign: "center",
        alignContent: "center",
        alignItems: "center",
        justifyContent: "center",
        justifyItems: "center",
        alignSelf: "center",
        justifySelf: "center",
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