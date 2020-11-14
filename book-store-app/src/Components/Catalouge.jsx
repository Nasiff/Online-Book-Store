import React from 'react';
import BrowseHeader from './BrowseHeader'
import SearchBar from './SearchBar'
class Catalouge extends React.Component {

    render() {
        const size={
            height: "100vh",
        }
        return ( 
        <div style={size}>
            <BrowseHeader/>

            <SearchBar/>
        </div> 
        );
    }
}


export default Catalouge;