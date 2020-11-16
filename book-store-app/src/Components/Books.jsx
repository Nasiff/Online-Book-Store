import React from 'react';
import '../Styles/Main.css'
import Book from './Book'

const BookList = (books) => {
    if(books.length > 0){
        return books.map(book => {
            return <Book style={styles.item} key={book.bid} bid={book.bid} />
        });
    } else {
        return <h1>Book List is Empty</h1>
    }
}

class Books extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            error: null,
            books: []
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
    
    render() {
        return ( 
        <div style={styles.container}>
            {BookList(this.state.books)}
        </div> 
        );
    }
}

const styles = {
    container: {
        display: "grid",
        gridTemplateColumns: "repeat(4, 1fr)",
        gridRowGap: ".5em",
        gridColumnGap: "1em",
        backgroundColor: "grey",
        height: "80%"
    }, 
    item: {

    }
}

export default Books;