import React from 'react';
import '../Styles/Main.css';
import Nav from './Nav'
import Banner from './Banner.jsx'
import Catalouge from './Catalouge.jsx'
class MainScreen extends React.Component {
    
    render() {
        return ( 
            <div className="App"> 
            <Banner/>
            <Catalouge addToCart={this.props.addToCart}/>
            </div>
        );
    }
}

export default MainScreen;