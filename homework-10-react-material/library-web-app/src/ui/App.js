import React from 'react'
import Library from "./pages/Library";
import Layout from "./components/Layout";
import {BrowserRouter, Switch, Route} from "react-router-dom";
import Genre from "./pages/Genre";

export default class App extends React.Component {
    render() {
        return (
            <BrowserRouter>
                <Layout>
                    <Switch>
                        <Route exact path="/" render={() => <div><Library /></div>} />
                        <Route path="/genre" render={() => <div><Genre /></div>} />
                    </Switch>
                </Layout>
            </BrowserRouter>
        )
    }
};