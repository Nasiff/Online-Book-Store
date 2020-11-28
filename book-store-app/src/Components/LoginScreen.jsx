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
          redirect: "/account",
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
    
    handleSubmit = (event) => {
        alert(this.state.email + " : " + this.state.password);
        event.preventDefault();
        this.props.loginFunc(this.state.email, this.state.password);
    }

    render() {
        if(this.props.loggedIn){
            return <Redirect to={this.state.redirect} />;
        } 
        
        return ( 
            <div>
            <div style={styles.container}>  
                <div style={styles.containerContent}>
                    <div style={styles.header}>Login</div>
                    <div style={styles.label}> Email </div>
                    <input style={styles.inputs} type="text" value={this.state.email} onChange={this.handleEmail} />
                    <div style={styles.label}> Password </div>
                    <input style={styles.inputs} type="text" value={this.state.password} onChange={this.handlePassword} />
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
    }
}

export default LoginScreen;