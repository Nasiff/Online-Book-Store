import React from 'react';
import cart from '../Assets/cart.png'
import WebService from '../Services/WebService';
import { BrowserRouter as Router,
    Switch,
    Route,
    Link,
    useRouteMatch,
    useParams } from "react-router-dom";
    
import ScrollContainer from 'react-indiana-drag-scroll';

class BrowseHeader extends React.Component {
    
    constructor(props) {
        super(props);
        this.state = {
            categories: []
        };
      }

    componentDidMount(){
        console.log("Loading Categories");
        fetch(WebService.uri + "/books/categories")
            .then(res => res.json())
            .then(
                (result) => {
                    console.log("Result: " + result);
                    this.setState({
                        categories: result.result.categories
                    });
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



    render() {

        return ( 
        <div className="inline" id="browseHeader">
            <div className="inline" id="browseTitle">
                <h3 className="center lg-view">Browse By Category</h3>
                <h3 className="center sm-view">Category</h3>
            </div>
            <ScrollContainer className="inline" id="catergories">
                <li style={ this.props.category == "All" ? { fontWeight: 'bold' } : 
                            { fontWeight: 'normal' }} className="category grow"
                            onClick={() => this.props.categoryFunc("All")}
                            >
                                All
                            </li>
                            
                {this.state.categories.map(category => {
                    return <div style={ this.props.category == category ? { fontWeight: 'bold' } : 
                            { fontWeight: 'normal' }} className="category grow" key={category} onClick={() => this.props.categoryFunc(category)}>
                                {category}
                            </div>
                })}
            </ScrollContainer>
            <Link className="lg-view" style={{padding: "0px"}}to="/cart">
                <div className="inline growX" id="cart">
                    <img style={styles.cart} src={cart} alt="Logo"></img>
                    <h3>Cart</h3>
                </div>
            </Link>
            <Link className="sm-view" style={{padding: "0px"}}to="/cart">
                <div className="inline growX" id="smallCart">
                    <img style={styles.smallCart} src={cart} alt="Logo"></img>
                </div>
            </Link>
        </div> 
        );
    }
}

const styles = {
    cart: {
        height: "35%",
        margin: "5px",
        overflow: "hidden"
    },
    smallCart: {
        height: "35%",
        margin: "0px",
        overflow: "hidden"
    }
}

export default BrowseHeader;