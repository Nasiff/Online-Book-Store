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
    useParams } from "react-router-dom";

class RegisterScreen extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          email: "",
          password: "",
          type: "",
          fname: "",
          lname: "",
          street: "",
          province_state: "",
          country: "",
          zip: "",
          phone: ""
        };
      }
    
    componentDidMount(){
        console.log("Mounted the Cart: " + this.state)
    }

    handleEmail = (event) => {
        this.setState({email: event.target.value});
    }

    handlePassword = (event) => {
        this.setState({password: event.target.value});
    }

    handleFName = (event) => {
        this.setState({fname: event.target.value});
    }

    handleLName = (event) => {
        this.setState({lname: event.target.value});
    }

    handleStreet = (event) => {
        this.setState({street: event.target.value});
    }

    handleProvinceState = (event) => {
        this.setState({province_state: event.target.value});
    }

    handleCountry = (event) => {
        this.setState({country: event.target.value});
    }

    handleZip = (event) => {
        this.setState({zip: event.target.value});
    }

    handlePhone = (event) => {
        this.setState({phone: event.target.value});
    }

    

    onChangeValue = (event) => {
        this.setState({type: event.target.value});
    }
    
    handleSubmit = (event) => {
        this.handleRegistration();
        event.preventDefault();
    }

    handleRegistration = () => {

        // Build registration JSON
        const registrationJson = {
            email: this.state.email,
            password: this.state.password,
            type: this.state.type,
            fname: this.state.fname,
            lname: this.state.lname,
            address: {
                street: this.state.street,
                province_state: this.state.province_state,
                country: this.state.country,
                zip: this.state.zip,
                phone: this.state.phone
            }
        }

        console.log(registrationJson);

        //Send post request here

        //If successful try to login as the user

        //If failure then alert the user
    }



    render() {
        return ( 
            <div>
            <div style={styles.container}>  
                <div style={styles.containerContent}>
                    <div style={styles.header}>Register</div>

                    <div style={styles.subHeader}>Account Information</div>

                    <div style={styles.label}> First Name </div>
                    <input style={styles.inputs} type="text" value={this.state.fname} onChange={this.handleFName} />

                    <div style={styles.label}> Last Name </div>
                    <input style={styles.inputs} type="text" value={this.state.lname} onChange={this.handleLName} />

                    <div style={styles.label}> Email </div>
                    <input style={styles.inputs} type="text" value={this.state.email} onChange={this.handleEmail} />
                    <div style={styles.label}> Password </div>
                    <input style={styles.inputs} type="text" value={this.state.password} onChange={this.handlePassword} />

                    <div style={styles.inputs} onChange={this.onChangeValue}>
                        <input type="radio" value="CUSTOMER" name="type" /> Customer
                        <input type="radio" value="ADMIN" name="type" /> Admin
                        <input type="radio" value="PARTNER" name="type" /> Partner
                    </div>

                    <div style={styles.subHeader}>Shipping Information</div>

                    
                    <div style={styles.label}> Street Address (Include Street Number)</div>
                    <input style={styles.inputs} type="text" value={this.state.street} onChange={this.handleStreet} />

                    <div style={styles.label}> Province / State </div>
                    <input style={styles.inputs} type="text" value={this.state.province_state} onChange={this.handleProvinceState} />

                    <div style={styles.label}> Country </div>
                    <input style={styles.inputs} type="text" value={this.state.country} onChange={this.handleCountry} />

                    <div style={styles.label}> Zip Code </div>
                    <input style={styles.inputs} type="text" value={this.state.zip} onChange={this.handleZip} />

                    <div style={styles.label}> Phone </div>
                    <input style={styles.inputs} type="text" value={this.state.phone} onChange={this.handlePhone} />

                    <div className="button grow" onClick={this.handleSubmit}>Submit</div>

                    <div style={styles.footer}>New to Mom and Pops? <Link to="/login" style={styles.signUp}>Sign in here!</Link></div>
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
        padding: "5px",
        textTransform: "capitalize"
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

export default RegisterScreen;