import React from 'react'
import Library from "./pages/Library";
import Layout from "./components/Layout";
import {BrowserRouter, Switch, Route} from "react-router-dom";

export default class App extends React.Component {
    render() {
        return (
            <BrowserRouter>
                <Layout>
                    <Switch>
                        <Route exact path="/" render={() => <div><Library /></div>} />
                        <Route path="/genre" render={() => <div>Страница в разработке</div>} />
                    </Switch>
                </Layout>
            </BrowserRouter>
        )
    }
};