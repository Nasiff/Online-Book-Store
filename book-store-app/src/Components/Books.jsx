import React, {useEffect} from 'react';
import Book from './Book';

const BookList = (books, loadAmount, addToCart) => {
    if(books.length > 0){
        return books.slice(0, loadAmount).map(book => {
            return <div className="grow" key={book.bid} style={styles.item}>
                        <Book addToCart={addToCart} bid={book.bid} title={book.title} price={book.price} image={book.image} author={book.author} review={book.review} numOfReviews={book.numOfReviews}/>
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
            loadAmount: 8
        };
      }

    
    componentDidMount() {
        this.loadBooks();
    }

    loadBooks = () => {
        console.log("Loading Books");
        fetch("./Data/books.json")
            .then(res => res.json())
            .then(
                (result) => {
                    console.log("Result: " + result);
                    this.setState({
                        books: result.books
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
      
    render() {
        return ( 
        <div style={styles.container}>
            {BookList(this.state.books, this.state.loadAmount, this.props.addToCart)}
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