import React from 'react';
import BrowseHeader from './BrowseHeader'
import SearchBar from './SearchBar'
import Filters from './Filters'
import Books from './Books'

class Catalouge extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            error: null,
            books: [],
            loadAmount: 8,
            category: "All",
            
        };
      }

    updateCategory = (val) => {
        this.setState({category: val});
    }


    render() {
        const size={
            marigin: "50px"
        }
        return ( 
        <div style={size}>
            <BrowseHeader category={this.state.category} categoryFunc={this.updateCategory}/>
            <div style={styles.container}>
            <SearchBar/>
            <Filters/>
            <Books category={this.state.category} addToCart={this.props.addToCart} uid={this.props.uid}/>
            </div>
        </div> 
        );
    }
}

const styles = {
    container: {
        margin: "25px 5% 25px 5%",
    }
}

export default Catalouge;