import React from 'react';
import MdMenu from 'react-ionicons/lib/MdMenu'


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
                <div style={styles.container}>
                    <div style={styles.hamburgerContainer}>
                        <MdMenu className="button" style = {styles.hamburger} onClick={() => this.toggleMenu()} fontSize="48px" color="white" />
                    </div>
                    <div style={styles.menu}>
                        <div className="menuItem" style={styles.menuItem}>Login</div>
                        <div className="menuItem" style={styles.menuItem}>Book Catalouge</div>
                        <div className="menuItem" style={styles.menuItem}>Shopping Cart</div>
                        <div className="menuItem" style={styles.menuItem}>Admin Analytics</div>
                    </div>
                </div> 
                );
        }
        else {
            return ( 
                <div>
                    <div style={styles.hamburgerContainer}>
                        <MdMenu className="button" style = {styles.hamburger} onClick={() => this.toggleMenu()} fontSize="48px" color="white" />
                    </div>
                </div> 
                );
        }
        
    }



}

const styles = {
    container: {
        background: "rgba(0,0,0,0.9) 50%",
        width: "100%",
        height: "100%"
    },
    hamburgerContainer: {
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
        textAlign: "start",
        margin: "25px",
        fontSize: "24px",
        color: "white"
    }
}

export default Nav;