import React from 'react';
import Popup from 'reactjs-popup';
import MdClose from 'react-ionicons/lib/MdClose'
import WebService from '../Services/WebService';
import BookContent from './BookContent';

const ReviewList = (reviews) => {
    if(reviews.length > 0){
        return reviews.map(review => {
            return <div className="grow" key={review.rid}>
                        <div>{review.uid}</div>
                        <div>{review.score}</div>
                        <div>{review.review}</div>
                    </div>
        });
    } else {
        return <h1>No Reviews for this book</h1>
    }
}


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
            qty: 0,
            loading: false,
            added: false, 
            reviews: []
      }
    }
    
    handleImage = (image, myStyle) => {
        if(image){
            return <img style={myStyle} src={`${process.env.PUBLIC_URL}` + this.state.image} alt="Book Cover"/>
        } else {
            return <img style={myStyle} alt="Book Cover"/>
        }
    }

    componentDidMount(){
        setTimeout(() => {
             this.setState({didMount: true});
         }, 0);
    }

    setOpen = (val) => {
        this.setState({open: val});
    }

    closeModal = () => {this.setOpen(false);}

    updateQty = (x) => {
        var updateVal = this.state.qty + x;
        if(updateVal >= 0){
            this.setState({qty: updateVal, added: false});
        }
    }


    render() {
        // Can do error check to make sure the title doesnt exceed 30 characters
        return ( 
        <div className={`fade-in ${this.state.didMount && 'visible'}`} style={styles.container} onClick={() => {this.setOpen(true)}}>
            {this.handleImage(this.state.image, styles.imageTemplate)}
            <h3 style={styles.bookFont}>{this.state.title} {this.state.author ? "by " + this.state.author : null}</h3> 
            <h5 style={styles.rating}> {(this.state.numOfReviews > 0) ? "Rating: " + this.state.review + "/5 (" + this.state.numOfReviews + ")": "No Reviews"} </h5>
            <h5 style={styles.price}>${this.state.price}</h5>

            <Popup open={this.state.open} closeOnDocumentClick onClose={this.closeModal}>
                <BookContent 
                    uid = {this.props.uid}
                    bid = {this.props.bid}
                    title= {this.props.title}
                    price= {this.props.price}
                    author= {this.props.author}
                    category= {this.props.category}
                    review= {this.props.review}
                    numOfReviews= {this.props.numOfReviews}
                    image= {this.props.image}
                    closeFunc={this.closeModal}
                    addToCart={this.props.addToCart}
                    loadBooksFunc={this.props.loadBooksFunc}
                    />
                    
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
        border: "5px solid black",
        boxShadow: "0 1px 2px 0px rgba(0, 0, 0, 0.6), 1px 2px 4px 0px rgba(0, 0, 0, 0.4)",   
        borderRadius: "10px",
        padding: "10px",
        width: "80vw",
        height: "80vh",
        overflow: "auto",
    },
    modalImageContainer: {
        display: "flex",
        justifyItems: "center",
        padding: "20px",
        //alignItems: "center",
        //backgroundColor: "red",
        height: "100%",
    },
    modalImage: {
        //display: "inline-block",
        justifyItems: "center",
        alignItems: "center",
        backgroundColor: "blue",
        height: "90%",
        verticalAlign: "middle",
        boxShadow: "0 1px 2px 0px rgba(0, 0, 0, 0.6), 1px 2px 4px 0px rgba(0, 0, 0, 0.4)", 
        //backgroundColor: "blue"
    },
    modalTitle: {
        color: "#707070",
        fontSize: "28px"
    },
    modalAuthor: {
        color: "#707070",
        fontSize: "24px"
    },
    modalPrice: {
        color: "#FF9900",
        fontSize: "18px",
        margin: "10px 0px 0px 0px"
    },
    modalReview: {
        fontSize: "14px",
        color: "black",
        margin: "10px 0px 0px 0px"
    },
    content: {
        display: "inline-block",
        //backgroundColor: "green",
        minHeight: "100%",
        verticalAlign: "top",
        padding: "20px",
    },
    quantityContainer: {
        margin: "25px 0px 10px 0px",
    },
    adjust: {
        padding: "10px 20px"
    },
    qty: {
        padding: "10px 20px",
        backgroundColor: "grey"
    },
    close: {
        cursor: "pointer",
        position: "absolute",
        display: "block",
        lineHeight: "20px",
        right: "-15px",
        top: "-15px",
        padding: "3px 3px 0px 3px",
        backgroundColor: "black",
        borderRadius: "500px",
        verticalAlign: "middle",
    }
}

export default Book;