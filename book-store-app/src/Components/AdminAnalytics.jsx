import React from 'react';
import WebService from '../Services/WebService';

const BookList = (books) => {
    console.log("books: " + books);
    if(books.length > 0){
        return books.map(book => {
            return <div key={book.bid} style={styles.item}>
                        <div style={{fontWeight: "500"}}>Rank: {book.rank}</div>
                        <div style={{fontWeight: "600", fontSize: "20px", color: "#0184C7"}}>{book.title} by {book.author}</div>
                        <div>Category: {book.category}</div>
                        <div>Sales: {book.sales}</div>
                    </div>
        });
    } else {
        return <h1>Book List is Empty</h1>
    }
}

class AdminAnalytics extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          errorMessage: null,
          monthBooks: [],
          allTimeBooks: [],
        };

      }
    
    componentDidMount(){
        console.log("Mounted the aDMIN: " + this.state);
        if(this.props.type == "ADMIN"){
            this.getMonthly();
            this.getAllTime();
        }
    }

    getMonthly = () => {
        console.log(this.props.uid);
        const headers = { 
            'Content-Type': 'application/json',
            'uid' : this.props.uid
        }

        fetch(WebService.uri + "/user/admin/month", {headers})
        .then(res => res.json())
        .then(
            //Only accounts for successful logins for now
            (result) => {
                console.log("Result: " + result);
                if(result.result.successful){
                    console.log("Successful: " + result.result.message);
                    this.setState({monthBooks: result.result.current_month});
                    console.log(this.state.monthBooks);
                } else {
                    alert("Failed fetch: " + result.result.error);
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

    getAllTime = () => {
        const headers = { 
            'Content-Type': 'application/json',
            'uid' : this.props.uid
        }
    
        fetch(WebService.uri + "/user/admin/alltime", { headers} )
        .then(res => res.json())
        .then(
            //Only accounts for successful logins for now
            (result) => {
                console.log("Result: " + result);
                if(result.result.successful){
                    console.log("Successful: " + result.result.message);
                    this.setState({allTimeBooks: result.result.all_time});
                } else {
                    alert("Failed fetch: " + result.result.error);
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

    render() {
        if(this.props.type == "ADMIN"){
            return (
                <div>
                <div id="banner2" style={styles.container}>  
                    <div style={styles.containerContent}>
                        <div style={styles.header}>Admin Analytics</div>
                        <div style={styles.errorMessage}>{this.state.errorMessage}</div>
                        <div style={styles.subHeader}> Monthly Sales </div>
                            {BookList(this.state.monthBooks)}
                        <div style={styles.subHeader}> All Time Sales </div>
                            {BookList(this.state.allTimeBooks)}


                    </div>
                </div> 
                </div>
            )

        } 
        
        return ( 
            <div>
            <div id="banner2" style={styles.container}>  
                <div style={styles.containerContent}>
                    <div style={styles.header}>Admin Analytics</div>
                    <div style={styles.errorMessage}>This Page is only available to admins</div>
                </div>
            </div> 
            </div>
        );
    }
}

const styles = {
    container: {
        display: "flex",
        backgroundColor:"pink",
        textAlign: "center",
        alignContent: "center",
        alignItems: "center",
        justifyContent: "center",
        justifyItems: "center",
        alignSelf: "center",
        justifySelf: "center",
        width: "100%",
        
    },
    containerContent: {
        minWidth: "60%",
        maxWidth: "80%",
        textAlign: "center",
        alignContent: "center",
        alignItems: "center",
        justifyContent: "center",
        justifyItems: "center",
        alignSelf: "center",
        justifySelf: "center",
        minHeight: "100vh",
        backgroundColor: "white"
    },
    header: {
        color: "#0184C7",
        fontSize: "33px",
        marginTop: "27px",
        textAlign: "center",
        marginBottom: "30px"
    },
    subHeader: {
        color: "pink",
        fontSize: "26px",
        marginTop: "27px",
        textAlign: "center",
        marginBottom: "30px",
        textDecoration: "underline"
    },
    form: {
        display: "flex"
    },
    inputs: {
        display: "block",
        textAlign: "center",
        margin: "auto",
        marginBottom: "20px",
        padding: "5px"
    },
    label: {
        margin: "10px"
    },
    footer: {
        margin: "20px"
    },
    signUp: {
        color: "pink",
        fontWeight: "bold"
    },
    errorMessage: {
        color: "red",
        fontWeight: "bold",
        fontSize: "16px"
    },
    item: {
        margin: "20px",
        padding: "10px",
        borderTop: "1px solid black",
        borderBottom: "1px solid black"
    }
}

export default AdminAnalytics;