import React from 'react';
import Popup from 'reactjs-popup';

class Book extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            didMount: false,
            open: false,
            bid: props.bid,
            title: props.title,
            price: props.price,
            author: props.author,
            category: props.category,
            review: props.review,
            numOfReviews: props.numOfReviews,
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

    setOpen = (val) => {
        this.setState({open: val});
    }

    closeModal = () => {this.setOpen(false);}

    render() {
        // Can do error check to make sure the title doesnt exceed 30 characters
        return ( 
        <div className={`fade-in ${this.state.didMount && 'visible'}`} style={styles.container} onClick={() => {this.setOpen(true)}}>
            {this.handleImage(this.state.image)}
            <h3 style={styles.bookFont}>{this.state.title} {this.state.author ? "by " + this.state.author : null}</h3> 
            <h5 style={styles.rating}> {(this.state.numOfReviews > 0) ? "Rating: " + this.state.review + "/5 (" + this.state.numOfReviews + ")": "No Reviews"} </h5>
            <h5 style={styles.price}>${this.state.price}</h5>
            <Popup open={this.state.open} closeOnDocumentClick onClose={this.closeModal}>
                <div style={styles.modal}>
                <a className="close" onClick={this.closeModal}>
                    &times;
                </a>

                <div style={styles.modalImage}>{this.handleImage(this.state.image)}</div>
                <div style={styles.modalTitle}>{this.state.title}</div>
                <div style={styles.modalAuthor}>{this.state.author}</div>
                <div style={styles.modalDetails}>{this.state.author}</div>
                <div style={styles.modalPrice}>{this.state.author}</div>

                </div>
            </Popup>
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
    },
    modal: {
        backgroundColor: "white",
        border: "2px solid black",
        borderRadius: "10px",
        padding: "10px",
        width: "80vw",
        height: "80vh",
        overflow: "auto"
    }
}

export default Book;