import React from 'react';
import MdMenu from 'react-ionicons/lib/MdMenu'
import { BrowserRouter as Router,
    Switch,
    Route,
    Link,
    useRouteMatch,
    useParams } from "react-router-dom";

class Nav extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            open: false  
      }
    }

    toggleMenu = () => {
        this.setState({open: !this.state.open});
        console.log(this.state.open);
    }

    render() {
        if(this.state.open){
            return ( 
                <div className="fade-in.visible" style={styles.container}>
                    <div style={styles.hamburgerContainer}>
                        <MdMenu className="button grow" style = {styles.hamburger} onClick={() => this.toggleMenu()} fontSize="48px" color="white" />
                    </div>
                    <div style={styles.menu}>
                        <div className="menuItem grow" style={styles.menuItem}>Login</div>
                        <Link to="/"><div className="menuItem grow" style={styles.menuItem}>Book Catalouge</div></Link>
                        <Link to="/cart"><div className="menuItem grow" style={styles.menuItem}>Shopping Cart</div></Link>
                        <div className="menuItem grow" style={styles.menuItem}>Admin Analytics</div>
                    </div>
                </div> 
                );
        }
        else {
            return ( 
                <div style={{position: "absolute"}}>
                    <div style={styles.hamburgerContainer}>
                        <MdMenu className="button grow" style = {styles.hamburger} onClick={() => this.toggleMenu()} fontSize="48px" color="white" />
                    </div>
                </div> 
                );
        }
        
    }



}

const styles = {
    container: {
        position: "absolute",
        background: "rgba(0,0,0,0.9) 50%",
        width: "100%",
        height: "100%"
    },
    hamburgerContainer: {
        //backgroundColor: "red",
        //margin: "10px 0 0 10px",
        display: "flex",
        alignItems: "center"
    },
    hamburger: {
        //backgroundColor: "white",
        //float: "left",
        padding: "5px",
        //border: "black 2px solid",
        borderRadius: "500px",
        margin: "20px"
    },
    menu: {
        //backgroundColor: "red",
        alignContent: "flex-start",
    }, 
    menuItem: {
        //backgroundColor: "red",
        textAlign: "start",
        margin: "25px 25px 25px 50px",
        fontSize: "24px",
        color: "white",
        width: "200px"
    }
}

export default Nav;