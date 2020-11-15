import React from 'react';


class Book extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
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
            return <img src={image} alt="Book Cover"/>
        } else {
            return <img style={styles.imageTemplate}/>
        }
    }

    render() {
        return ( 
        <div style={styles.container}>
            {this.handleImage(this.state.image)}
            <h1>{this.state.bid}</h1>
        </div> 
        );
    }
}

const styles = {
    container: {
        display: "flex-block",
        height: "100%",
        width: "100%",
        overflow: "hidden",
        backgroundColor: "blue"
    },
    cover: {
        //maxWidth: "100%",
        //height: "auto"
    },
    imageTemplate: {
        height: "400px",
        maxHeight: "100%",
        width: "200px",
        maxWidth: "100%",
        backgroundColor: "red"
    }
}

export default Book;