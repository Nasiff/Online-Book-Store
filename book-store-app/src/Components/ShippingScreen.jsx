import React from 'react';
import { BrowserRouter as Router,
    Switch,
    Route,
    Link,
    useRouteMatch,
    useParams,
    Redirect } from "react-router-dom";
import WebService from '../Services/WebService'

class ShippingScreen extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            hasShippingInfo: false,
            shippingInfo: null,
            fname: "",
            lname: "",
            street: "",
            province_state: "",
            country: "",
            zip: "",
            phone: "",
            creditCard: "",
            attempts: 0,
            verified: false,
            redirect: false,
        };
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

    handleCredit = (event) => {
        this.setState({creditCard: event.target.value});
    }

    buildShippingJSON = () => {
        return {
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
    }

    verifyCard = () => {
        if(this.state.attempts < 3){
            if(this.state.creditCard.replace(/ /g,'').length == 16){
                this.setState({verified: true});
            } else {
                var tries = this.state.attempts + 1;
                alert("Failed to authenticate card, Please try again. Attempt: " + tries);
                this.setState({verified: false, attempts: tries});         
            };
        } else {
            alert("You have failed more then 3 times, Please try again later");
        }
    }
    
    componentDidMount(){
        console.log("Mounted the Cart: " + this.state)
        if(this.props.loggedIn){
            //If logged in try to get shipping information
            this.getShippingInformation(this.props.uid);
        }
    }

    getShippingInformation(uid){

        const headers = { 
            'Content-Type': 'application/json',
            'uid': this.props.uid
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
                        shippingInfo: result.result,  
                        hasShippingInfo: result.result.successful,
                        fname: this.props.userInfo.fname,
                        lname: this.props.userInfo.lname,
                        street: result.result.street,
                        province_state: result.result.province_state,
                        country: result.result.country,
                        zip: result.result.zip,
                        phone: result.result.phone                 
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

    buildShippingInfo = () => {
        if(this.state.hasShippingInfo){
            return (
                <div>
                    <div style={styles.label}> Logged in As <span style={{color: "pink", textDecoration: "underline"}}>{this.props.userInfo.fname} </span></div>

                    <div style={styles.label}> Street Address (Include Street Number)</div>
                    <div style={styles.text}> {this.state.street} </div>

                    <div style={styles.label}> Province / State </div>
                    <div style={styles.text}> {this.state.province_state} </div>

                    <div style={styles.label}> Country </div>
                    <div style={styles.text}> {this.state.country} </div>

                    <div style={styles.label}> Zip Code </div>
                    <div style={styles.text}> {this.state.zip} </div>

                    <div style={styles.label}> Phone </div>
                    <div style={styles.text}> {this.state.phone} </div>
                </div>
            );
        } else {
            return (
                <div>
                    <div>You are checking out as a guest would you like to 
                        <span className="grow" onClick={() => {
                            this.props.redirectFunc("./shipping"); 
                            this.setState({redirect: true});
                            }}> login?</span></div>

                    <div style={styles.label}> First Name </div>
                    <input style={styles.inputs} type="text" value={this.state.fname} onChange={this.handleFName} />

                    <div style={styles.label}> Last Name </div>
                    <input style={styles.inputs} type="text" value={this.state.lname} onChange={this.handleLName} />

                    <div style={styles.label}> Street Address </div>
                    <input style={styles.inputs} type="text" value={this.state.street} onChange={this.handleStreet} />

                    <div style={styles.label}> Province / State </div>
                    <input style={styles.inputs} type="text" value={this.state.province_state} onChange={this.handleProvinceState} />

                    <div style={styles.label}> Country </div>
                    <input style={styles.inputs} type="text" value={this.state.country} onChange={this.handleCountry} />

                    <div style={styles.label}> Zip Code </div>
                    <input style={styles.inputs} type="text" value={this.state.zip} onChange={this.handleZip} />

                    <div style={styles.label}> Phone </div>
                    <input style={styles.inputs} type="text" value={this.state.phone} onChange={this.handlePhone} />
                </div>
            )
        }
    }

    render() {
        if(this.state.redirect){
            return <Redirect to="/login" />
        }

        return ( 
        <div>
        <div style={styles.container}>  
            <div style={styles.containerContent}>
                <div style={styles.header}>Shipping Information</div>
                <div style={styles.cartContainer}>
                {this.buildShippingInfo()}
                <div style={styles.header}>Payment Information</div>    
                <div style={styles.label}> Add your 16 digit credit cart number for payment</div>
                <div style={styles.label}> Credit Card </div>
                {(!this.state.verified) ?
                    <div> 
                        <input style={styles.inputs} type="text" value={this.state.creditCard} onChange={this.handleCredit} />
                        <div className="button grow" style={styles.next} onClick={this.verifyCard}>Verify</div>
                        <div className="button grow" style={styles.disableNext}>Summary</div>
                    </div>  : 
                        <div>
                            <div style={styles.text}> {this.state.creditCard} </div>
                            <div className="button" style={styles.next} onClick={() => this.setState({verified: false})}>Verified</div>
                            <Link to={{pathname: '/summary', state: {shippingInfo: this.buildShippingJSON(), credit: this.state.creditCard}}}><div className="button grow" style={styles.next}>Summary</div></Link>  
                        </div>
                    }
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
        margin: "10px",
        textAlign: "center"
    },
    inputs: {
        textTransform: "capitalize",
        textAlign: "center"
    },
    next: {
        borderRadius: "50px",
        fontWeight: "bold",
        fontSize: "18px",
        margin: "auto",
        marginTop: "20px",
        padding: "10px 20px 10px 20px",
        display: "block",
        width: "100px",
        textAlign: "center"
    },
    disableNext: {
        borderRadius: "50px",
        fontWeight: "bold",
        fontSize: "18px",
        margin: "auto",
        marginTop: "20px",
        padding: "10px 20px 10px 20px",
        display: "block",
        width: "100px",
        textAlign: "center",
        backgroundColor: "grey"
    }
}

export default ShippingScreen;