import React from 'react';
import '../Styles/Main.css';
import Nav from './Nav'
import Banner from './Banner.jsx'
import Catalouge from './Catalouge.jsx'
class MainScreen extends React.Component {
    
    render() {
        return ( 
            <div> 
            <Banner/>
            <Catalouge/>
            </div>
        );
    }
}

export default MainScreen;