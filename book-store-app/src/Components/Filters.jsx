import React from 'react';


class Filters extends React.Component {
    
    render() {
        return ( 
        <div style={styles.container}>
            <p style = {{marginRight: "20px"}}>Filters by: ________</p>
            <p>Sort by: _________</p>
        </div> 
        );
    }
}

const styles = {
    container: {
        display: "flex",
        justifyContent: "flex-end"
    },
    p: {
        margin: "30px"
    }
}

export default Filters;