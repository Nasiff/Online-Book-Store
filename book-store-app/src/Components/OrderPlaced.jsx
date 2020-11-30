import React from 'react';
import Popup from 'reactjs-popup';
import MdClose from 'react-ionicons/lib/MdClose'
import WebService from '../Services/WebService';
import Rating from '@material-ui/lab/Rating';
import Box from '@material-ui/core/Box';
import Typography from '@material-ui/core/Typography';
import IosRefresh from 'react-ionicons/lib/IosRefresh';

import { BrowserRouter as Router,
    Switch,
    Route,
    Link,
    useRouteMatch,
    useParams,
    Redirect} from "react-router-dom";
import { Web } from '@material-ui/icons';


class BookContent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            postStatus: "STANDBY"
      }
    }
    

    componentDidMount(){
 
    }

    render() {
        // Can do error check to make sure the title doesnt exceed 30 characters
        return ( 
            <div className="scrollHide" style={styles.modal}>
                <a className="close" style={styles.close} onClick={() => this.props.closeFunc()}>
                    <MdClose fontSize="40px" color="white"/>
                </a>
                
            </div>
        
        );
    }
}

const styles = {
    modal: {
        backgroundColor: "white",
        border: "5px solid black",
        borderRadius: "10px",
        padding: "10px",
        width: "80vw",
        height: "80vh",
        overflow: "auto",
    },
    close: {
        cursor: "pointer",
        position: "absolute",
        display: "block",
        lineHeight: "20px",
        right: "-15px",
        top: "-15px",
        padding: "3px 3px 0px 3px",
        backgroundColor: "black",
        borderRadius: "500px",
        verticalAlign: "middle",
    }
}

export default OrderPlaced;