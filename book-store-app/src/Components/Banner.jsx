import React from 'react';
import logo from '../Assets/momAndPopLogo.png';
import '../Styles/Main.css';
import Nav from './Nav';

class Banner extends React.Component {

    render() {

        return ( 
        <div id="banner">
            <div>
                <img id="logo" className="alignRight" src={logo} alt="Logo"></img>
            </div>
            <Nav />
        </div> 
        );
    }
}

export default Banner;