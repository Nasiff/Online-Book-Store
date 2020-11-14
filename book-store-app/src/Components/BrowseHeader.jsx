import React from 'react';


class BrowseHeader extends React.Component {
    
    render() {
        return ( 
        <div class="inline" id="browseHeader">
            <div class="inline" id="browseTitle">
                <h3 class="center">Browse By Category</h3>
            </div>
            <ul class="inline" id="catergories">
                    <li class="category">Science</li>
                    <li class="category">English</li>
                    <li class="category">Buisness</li>
                    <li class="category">Romance</li>
                    <li class="category">Fantasy</li>
                </ul>
            <div class="inline" id="cart">
                <h3>Cart</h3>
            </div>
        </div> 
        );
    }
}

export default BrowseHeader;