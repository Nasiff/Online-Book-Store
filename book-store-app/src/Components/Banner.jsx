import React from 'react';
import logo from '../Assets/momAndPopLogo.png'
import '../Styles/Main.css'

class Banner extends React.Component {

    render() {

        return ( 
        <div id="banner">
            <div>
                <img id="logo" className="alignRight" src={logo} alt="Logo"></img>
            </div>
        </div> 
        );
    }
}

export default Banner;