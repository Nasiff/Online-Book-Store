import React from 'react';
import BrowseHeader from './BrowseHeader'
import SearchBar from './SearchBar'
import Filters from './Filters'
import Books from './Books'

class Catalouge extends React.Component {

    render() {
        const size={
            height: "100vh",
        }
        return ( 
        <div style={size}>
            <BrowseHeader/>
            <div style={styles.container}>
            <SearchBar/>
            <Filters/>
            <Books/>
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