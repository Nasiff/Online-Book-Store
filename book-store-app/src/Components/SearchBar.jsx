import React from 'react';
import searchIcon from '../Assets/search.png'
class SearchBar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            value: 'Not Implemented'
        };

      }


    handleChange = (event) => {
        this.setState({value: event.target.value});
        //this.props.updateSearchFunc(event.target.value);
    }

    handleSubmit = (event) => {
        //console.log("submit: " + this.state.value)
        event.preventDefault();
    }

    render() {
        const size={
            height: "100vh",
        }

        return ( 
        <div>
            <form style={styles.container} onSubmit={this.handleSubmit}>
                <label style={styles.searchContainer}>
                    <img style={styles.icon} src={searchIcon} alt="Search"/>
                    <input style={styles.input} type="text" value={this.state.value} onChange={this.handleChange} />
                </label>
                <input style={styles.button} type="submit" value="Submit" />
            </form>
        </div> 
        );
    }
}

const styles = {
    container: {
        //margin: "25px 5% 25px 5%",
        display: "flex",
        alignContent: "center",
        justifyContent: "center",
        width: "100%",
        border: "1px black solid",
        boxShadow: "0 2px 2px 0 rgba(0, 0, 0, 0.2), 0 2px 10px 0 rgba(0, 0, 0, 0.19)"
        //backgroundColor: "red"
    }, 
    searchContainer: {
        display: "flex",
        width: "100%"
    },
    input: {
        border: 0,
        padding: "5px 10px 5px 10px",
        width: "100%",
        fontSize: "18px"
    },
    icon: {
        width: "40px",
        padding: "5px 10px 5px 10px"
    },
    button: {
        backgroundColor: "#0184C7",
        boxShadow: "0 2px 2px 0 rgba(0, 0, 0, 0.2), 0 2px 10px 0 rgba(0, 0, 0, 0.19)",
        border: 0,
        color: "white",
        width: "100px",
        fontSize: "18px"
    }

}

export default SearchBar;