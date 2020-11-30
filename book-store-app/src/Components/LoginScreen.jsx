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

class LoginScreen extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          email: "",
          password: "",
          type: null,
          fname: "",
          redirect: this.props.redirect,
          errorMessage: null
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

    onChangeValue(event) {
        console.log(event.target.value);
    }
    
    handleSubmit = async (event) => {
        //alert(this.state.email + " : " + this.state.password);
        event.preventDefault();
        var message = await this.props.loginFunc(this.state.email, this.state.password)
        this.setState({errorMessage: message});
    }

    render() {
        if(this.props.loggedIn){
            return <Redirect to={this.props.redirect ? this.props.redirect : "/account"} />;
        } 
        
        return ( 
            <div>
            <div style={styles.container}>  
                <div style={styles.containerContent}>
                    <div style={styles.header}>Login</div>
                    <div style={styles.errorMessage}>{this.state.errorMessage}</div>
                    <div style={styles.label}> Email </div>
                    <input style={styles.inputs} type="text" value={this.state.email} onChange={this.handleEmail} />
                    <div style={styles.label}> Password </div>
                    <input style={styles.inputs} type="password" value={this.state.password} onChange={this.handlePassword}/>
                    <div className="button grow" onClick={this.handleSubmit}>Submit</div>
                    <div style={styles.footer}>New to Mom and Pops? <Link to="/register" style={styles.signUp}>Sign up here!</Link></div>
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
    errorMessage: {
        color: "red",
        fontWeight: "bold",
        fontSize: "16px"
    }
}

export default LoginScreen;