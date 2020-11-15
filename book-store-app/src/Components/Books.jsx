import React from 'react';
import '../Styles/Main.css'
import Book from './Book'

const BookList = (books) => {
    return books.map(book => {
        return <Book style={styles.item} key={book.bid} bid={book.bid} />
    });
}

class Books extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            books: [
                {
                    bid: 21312321,
                    title: "The Foundations of Blogging",
                    price: "15.99",
                    author: "Allie Wang",
                    category: "How To's",
                    review: "4",
                    image: null
                },
                {
                    bid: 21312322,
                    title: "The Foundations of Blogging",
                    price: "15.99",
                    author: "Allie Wang",
                    category: "How To's",
                    review: "4",
                    image: null
                },
                {
                    bid: 21312323,
                    title: "The Foundations of Blogging",
                    price: "15.99",
                    author: "Allie Wang",
                    category: "How To's",
                    review: "4",
                    image: null
                },
                {
                    bid: 21312324,
                    title: "The Foundations of Blogging",
                    price: "15.99",
                    author: "Allie Wang",
                    category: "How To's",
                    review: "4",
                    image: null
                },
                {
                    bid: 21312325,
                    title: "The Foundations of Blogging",
                    price: "15.99",
                    author: "Allie Wang",
                    category: "How To's",
                    review: "4",
                    image: null
                },
                {
                    bid: 21312326,
                    title: "The Foundations of Blogging",
                    price: "15.99",
                    author: "Allie Wang",
                    category: "How To's",
                    review: "4",
                    image: null
                },
            ]
        };
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