import React from 'react'
import Library from "./pages/Library";
import Layout from "./components/Layout";
import {BrowserRouter, Switch, Route} from "react-router-dom";
import Genre from "./pages/Genre";
import Book from "./pages/Book";

export default class App extends React.Component {
    render() {
        return (
            <BrowserRouter>
                <Layout>
                    <Switch>
                        <Route exact path="/" render={() => <div><Library /></div>} />
                        <Route path="/genre" render={() => <div><Genre /></div>} />
                        <Route path="/book/:bookId" render={(props) => <div><Book {...props}/></div>} />
                    </Switch>
                </Layout>
            </BrowserRouter>
        )
    }
};