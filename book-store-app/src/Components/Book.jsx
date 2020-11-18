import React from 'react';


class Book extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            didMount: false,
            bid: props.bid,
            title: props.title,
            price: props.price,
            author: props.author,
            category: props.category,
            review: props.review,
            image: props.image,     
      }
    }

    handleImage = (image) => {
        if(image){
            return <img style={styles.imageTemplate} src={`${process.env.PUBLIC_URL}` + this.state.image} alt="Book Cover"/>
        } else {
            return <img style={styles.imageTemplate} alt="Book Cover"/>
        }
    }

    componentDidMount(){
        setTimeout(() => {
             this.setState({didMount: true})
         }, 0)
    }
    
    render() {
        // Can do error check to make sure the title doesnt exceed 30 characters
        return ( 
        <div className={`fade-in ${this.state.didMount && 'visible'}`} style={styles.container}>
            {this.handleImage(this.state.image)}
            <h3 style={styles.bookFont}>{this.state.title} {this.state.author ? "by " + this.state.author : null}</h3> 
            <h5 style={styles.rating}>Rating: {this.state.review} / 5</h5>
            <h5 style={styles.price}>${this.state.price}</h5>
        </div> 
        );
    }
}

const styles = {
    container: {
        maxHeight: "500px",
        //minWidth: "200px",
        maxWidth: "250px",
        overflow: "hidden",
        //backgroundColor: "blue",
        justifyItems: "center",
        alignItems: "center"
    },
    cover: {
        //maxWidth: "100%",
        //height: "auto"
    },
    imageTemplate: {
        height: "60%", 
        maxHeight: "300px",
        boxShadow: "0 1px 2px 0px rgba(0, 0, 0, 0.6), 1px 2px 4px 0px rgba(0, 0, 0, 0.4)",     
        backgroundColor: "grey"
    },
    bookFont: {
        //display: "inline-block",
        //textAlign: "left",
        //backgroundColor: "green",
        color: "#707070",
        margin: "5px"
    },
    rating: {
        margin: "5px"
    },
    price: {
        color: "#FF9900",
        margin: "5px"
    }
}

export default Book;