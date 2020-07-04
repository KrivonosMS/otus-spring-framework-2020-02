import React from 'react'
import Library from "./pages/Library";
import Layout from "./components/Layout";
import {BrowserRouter, Route, Switch} from "react-router-dom";
import Genre from "./pages/Genre";
import Book from "./pages/Book";
import Login from "./pages/Login";

export default class App extends React.Component {
    render() {
        return (
            <BrowserRouter>
                <Switch>
                    <Layout>
                        <Route exact path="/" render={(props) => <div><Library {...props}/></div>}/>
                        <Route path="/genre" render={(props) => <div><Genre {...props}/></div>}/>
                        <Route path="/book/:bookId" render={(props) => <div><Book {...props}/></div>}/>
                        <Route exact path="/login" render={(props) => <div><Login {...props}/></div>}/>
                    </Layout>
                </Switch>
            </BrowserRouter>
        )
    }
};