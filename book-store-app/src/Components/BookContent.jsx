import React from 'react';
import Popup from 'reactjs-popup';
import MdClose from 'react-ionicons/lib/MdClose'
import WebService from '../Services/WebService';
import Rating from '@material-ui/lab/Rating';
import Box from '@material-ui/core/Box';
import Typography from '@material-ui/core/Typography';
import IosRefresh from 'react-ionicons/lib/IosRefresh';

import { BrowserRouter as Router,
    Switch,
    Route,
    Link,
    useRouteMatch,
    useParams,
    Redirect} from "react-router-dom";
import { Web } from '@material-ui/icons';

const ReviewList = (reviews) => {
    if(reviews.length > 0){
        return reviews.map(review => {
            return <div style={styles.reviewContainer} key={review.rid}>
                        <div style={styles.user}> User: {review.uid}</div> 
                        <div style={styles.score}>                    
                            <Rating size="small" name="read-only" value={review.score} readOnly />
                        </div>       
                        <div style={styles.text}>{review.review}</div>
                    </div>
        });
    } else {
        return null
    }
}


class BookContent extends React.Component {
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
            reviews: [],
            reviewPrompt: false,
            reviewText: "",
            val: 0,
            postStatus: "STANDBY"
      }
    }
    
    handleImage = (image, myStyle) => {
        if(image){
            return <img style={myStyle} src={`${process.env.PUBLIC_URL}` + this.state.image} alt="Book Cover"/>
        } else {
            return <div>No Book Cover Available</div>
        }
    }

    componentDidMount(){
        this.loadReviews();
        setTimeout(() => {
             this.setState({didMount: true});
         }, 0);
    }

    updateQty = (x) => {
        var updateVal = this.state.qty + x;
        if(updateVal >= 0){
            this.setState({qty: updateVal, added: false});
        }
    }

    addBookToCart = () => {
        if(this.state.added){
            this.setState({added: false});
        }
        else {
            this.props.addToCart(this.props.bid, this.props.title, this.props.author, this.state.qty, this.props.price);
            this.setState({added: true});
            console.log("added: " + this.props.bid + this.props.title);
        }

    }

    loadReviews = () => {
        const headers = { 
            'Content-Type': 'application/json',
          }
          var uri = WebService.uri + "/books/" + this.state.bid + "/review";
          fetch(uri, { headers })
          .then(res => res.json())
          .then(
              //Only accounts for successful logins for now
              (result) => {
                  console.log("Result: " + result);
                  this.setState({
                      reviews: result.result.reviews,
                  });
           
              },
      
              /* Any Errors */
              (error) => {
                  console.log(error);
                  this.setState({
                      error
                  });
                  
                  alert(this.state.error);
              }
          )
    }

    setScore = (val) => {
        console.log(val);
        this.setState({score: val});
        //Error with uncontrolled val but is fine for now
    }

    reviewInputComponent = () => {
        console.log(this.props.uid);
        if(this.props.uid != null){
            //You are logged in
            return <div style={styles.reviewContainer}>
                        <div style={styles.user}> User: {this.props.uid}</div> 
                        <div style={styles.score}>                    
                        <Rating
                            name="simple-controlled"
                            value={this.state.score}
                            onChange={(event, newValue) => {
                                this.setScore(newValue);
                            }}
                            />
                        </div>       
                        <textarea style={styles.textInput} type="text" value={this.state.reviewText} onChange={this.handleReviewBody}/>
                        {this.state.postStatus == "POSTING" ? <div>
                            <div className="button">Submiting Review</div> 
                            <IosRefresh fontSize="60px" color="#347eff" rotate={true} />
                        </div>
                        : 
                        <div className="button" onClick={() => {this.submitReview()}}>Submit Review</div>}
                    </div>
        }
        else {
            return <Link to="/login"><div style={styles.reviewContainer}>
            <div style={styles.user}> Please Sign in to review </div> 
            </div></Link>

        }
    }

    submitReview = () => {
        console.log("submiting Review");
        this.setState({postStatus: "POSTING"})
        const reviewPost = {
            review_score: this.state.score,
            review_body: this.state.reviewText

        };

        const data = {
            method: 'POST', 
            mode: 'cors', // no-cors, *cors, same-origin
            cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
            credentials: 'same-origin', // include, *same-origin, omit
            headers: {
              'Content-Type': 'application/json',
              'uid': this.props.uid
            },
            redirect: 'follow', // manual, *follow, error
            referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
            body: JSON.stringify(reviewPost) // body data type must match "Content-Type" header
          }


        const url = WebService.uri + "/books/" + this.props.bid + "/review";
        console.log("URL" + url);
        console.log(reviewPost);

        fetch(url, data)
        .then(res => res.json())
        .then(
            //Only accounts for successful logins for now
            (result) => {
                console.log(result);
                if(result.result.successful){
                    this.setState({postStatus: "COMPLETE"});
                    this.setState({reviewPrompt: false});
                    this.loadReviews();
                    this.props.loadBooksFunc(); //Use this to update all book ratings after
                } else {
                    this.setState({postStatus: "FAILED"});
                    alert("Error Posting Review: " + result.result.error);
                }
              },
      
              /* Any Errors */
              (error) => {
                  console.log(error);
                  this.setState({
                      error
                  });
                  
                  alert(this.state.error);
              }
          )
        
    }

    handleReviewBody = (event) => {
        this.setState({reviewText: event.target.value});
        console.log(this.state.reviewText)
    }

    render() {
        // Can do error check to make sure the title doesnt exceed 30 characters
        return ( 
            <div className="scrollHide" style={styles.modal}>
                <a className="close" style={styles.close} onClick={() => this.props.closeFunc()}>
                    <MdClose fontSize="40px" color="white"/>
                </a>
                <div style={{display: "inline-block", height: "100%"}}>
                <div style={styles.modalImageContainer}>
                    {this.handleImage(this.state.image, styles.modalImage)}
                </div>
                </div>
                <div style={styles.content}>
                    <div style={styles.modalTitle}>{this.props.title}</div>
                    <div style={styles.modalAuthor}>{this.props.author ? "by " + this.state.author : null} <span style={styles.modalPrice}>${this.props.price} </span></div>
                    <div style={styles.modalReview}>
                        <div style={{display: "table-cell", verticalAlign:"middle"}}>
                            <Rating name="read-only" value={this.state.review} readOnly />
                        </div>  
                        <div style={{display: "table-cell", verticalAlign:"middle"}}>
                            <div style={{fontSize: "15px", marginLeft: "5px"}} >( {this.state.numOfReviews} Review )</div>
                        </div>  
                    </div>

                    <div style={styles.quantityContainer}>
                        <div className="button grow" style={styles.adjust} onClick={() => {this.updateQty(-1)}}>-</div>
                        <div className="button" style={styles.qty}>{this.state.qty}</div>
                        <div className="button grow" style={styles.adjust} onClick={() => {this.updateQty(1)}}>+</div>
                        <div className="button" style={styles.submit} onClick={() => {this.addBookToCart()}}>
                            {!this.state.added ? "Add to Cart" : this.state.qty + " copies added to your cart!"}
                        </div>
                    </div>


                    <div style={styles.subheader}>User Reviews</div>

                    {this.state.reviewPrompt ? 
                        this.reviewInputComponent() :
                        <div className="grow" style={styles.reviewContainer} onClick={() => {this.setState({reviewPrompt: true})}}>
                            <div style={styles.user}> Add Review </div> 
                        </div>
                    }

                    {ReviewList(this.state.reviews)}
                    
                </div>

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
        backgroundColor: "#FAF8FF",
        border: "10px solid white",
        borderRadius: "1px",
        boxShadow: "0 1px 2px 0px rgba(0, 0, 0, 0.6), 1px 2px 4px 0px rgba(0, 0, 0, 0.4)",
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
        margin: "10px 0px 0px 0px",
        display: "table",
        //backgroundColor:"green",
        
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
    submit: {
        padding: "10px 20px",
        marginLeft: "10px"
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
        backgroundColor: "#0184C7",
        border: "5px white solid",
        borderRadius: "500px",
        verticalAlign: "middle",
    },
    reviewContainer: {
        fontSize: "14px",
        //backgroundColor: "red",
        padding: "20px 10px 20px 10px",
        margin: "10px 0 10px 0",
        boxShadow: "0 1px 2px 0px rgba(0, 0, 0, 0.6), 1px 2px 4px 0px rgba(0, 0, 0, 0.4)",     
    },
    user: {
        fontWeight: "bold"
    },
    score: {
        marginTop: "5px"
    },
    text: {
        marginLeft: "10px"
    },
    subheader: {
        margin: "20px 0 20px 0",
        color: "#707070",
        fontSize: "22px"
    },
    textInput: {
        margin: "10px 0 10px 0",
        width: "80%",
        height: "150px",
        textAlign: "left",
        padding: "20px 20px"
    }
}

export default BookContent;