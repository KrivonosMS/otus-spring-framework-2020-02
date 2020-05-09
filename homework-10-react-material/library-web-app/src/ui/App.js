import React from 'react'
import Library from "./pages/Library";
import Layout from "./components/Layout";

export default class App extends React.Component {
    render() {
        return (
            <Layout>
                <Library/>
            </Layout>
        )
    }
};