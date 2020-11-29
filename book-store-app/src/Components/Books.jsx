import React, {useEffect} from 'react';
import Book from './Book';
import WebService from '../Services/WebService';

const BookList = (books, loadAmount, addToCart, uid) => {
    console.log("books: " + books);
    if(books.length > 0){
        return books.slice(0, loadAmount).map(book => {
            return <div className="grow" key={book.bid} style={styles.item}>
                        <Book uid={uid} addToCart={addToCart} bid={book.bid} title={book.title} price={book.price} image={book.image_url} author={book.author} review={book.review_score} numOfReviews={book.number_of_reviews}/>
                    </div>
        });
    } else {
        return <h1>Book List is Empty</h1>
    }
}


//Refactor to function to use useEffect to prevent Scroll
class Books extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            error: null,
            books: [],
            loadAmount: 8,
            category: this.props.category
        };
      }

    
    componentDidMount() {
        this.loadBooks();
    }

    shouldComponentUpdate(nextProps, nextState) {
        if (this.props.category !== nextProps.category) {
            this.loadBooksByCategory(nextProps.category);
        }
        return true;}

    loadBooks = () => {
        console.log("Loading Books");
        fetch(WebService.uri + "/books")
            .then(res => res.json())
            .then(
                (result) => {
                    console.log("Result: " + result);
                    this.setState({
                        books: result.result.books
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

    
    loadXMoreBooks = (e) => {
        e.preventDefault();
        var lastY = window.pageYOffset;
        console.log("Loading More books:" + e);
        var newLoad = this.state.loadAmount + 8;
        // Should check if the new load is larger then the array size and remove button
        this.setState({
            loadAmount: newLoad
        });
        window.scrollTo(window.screenX, lastY);
    }

    loadBooksByCategory = (category) => {
        console.log("loadBooksByCategory");
        if(category === "All"){
            fetch(WebService.uri + "/books")
                .then(res => res.json())
                .then(
                    (result) => {
                        console.log("Result: " + result);
                        this.setState({
                            books: result.result.books
                        });
                    },
    
                    /* Any Errors */
                    (error) => {
                        console.log(error);
                        
                        alert(this.state.error);
                        this.setState({
                            error
                        });
                    }
                )
        } else {
            fetch(WebService.uri + "/books/categories/" + category)
            .then(res => res.json())
            .then(
                (result) => {
                    console.log("Result: " + result);
                    this.setState({
                        books: result.result.books
                    });
    
                },
    
                /* Any Errors */
                (error) => {
                    console.log(error);
                    
                    alert(this.state.error);
                    this.setState({
                        error
                    });
                }
            )
        }
    }
      
    render() {
        return ( 
        <div style={styles.container}>
            {BookList(this.state.books, this.state.loadAmount, this.props.addToCart, this.props.uid)}
            <div>
                <button className="button" style={styles.loadButton} onClick={this.loadXMoreBooks}>Load More Books</button>
            </div>
        </div>

        );
    }
}

const styles = {
    container: {
        //backgroundColor: "green",
    }, 
    item: {
        display: "inline-block",
        margin: "25px"
    },
    loadButton: {
        fontSize: "20px",
        margin: "50px"
    },
}


export default Books;