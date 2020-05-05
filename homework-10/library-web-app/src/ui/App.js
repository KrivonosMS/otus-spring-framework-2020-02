import React from 'react'
import Library from "./pages/Library";

const Header = (props) => (
    <h1>{props.title}</h1>
);

export default class App extends React.Component {
    render() {
        return (
            <Library />
        )
    }
};