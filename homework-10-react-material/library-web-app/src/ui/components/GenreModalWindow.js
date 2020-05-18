import React, {Component} from 'react';
import '../css/BookModalWindow.css';
import Modal from "react-modal";
import Button from "@material-ui/core/Button";
import Box from "@material-ui/core/Box";
import TextField from "@material-ui/core/TextField";
import InputLabel from "@material-ui/core/InputLabel";

class GenreModalWindow extends Component {
    constructor(props) {
        super(props);
        this.state = {
            closeModal: props.closeModal,
            updateGrid: props.updateGrid
        }

        this.saveGenre = this.saveGenre.bind(this);
        this.flushResult = this.flushResult.bind(this);
    }

    saveGenre(evt, genreId) {
        const url = genreId ? '/library/genre/' + genreId + '/edit' : '/library/genre/add';
        const data = new FormData(document.querySelector("form"));

        evt.preventDefault();
        fetch(url, {
            method: 'POST',
            body: data
        })
            .then(response => response.json())
            .then(result => {
                if (result.success === true) {
                    this.setState({result: 'Литературный жанр успешно сохранен'})
                    this.state.updateGrid();
                } else {
                    result.message ?
                        this.setState({result: result.message}) :
                        this.setState({result: "Непредвиденная ошибка"})
                }
            });
    }

    flushResult() {
        this.setState({
            result: ''
        })
    }

    render() {
        const customStyles = {
            content: {
                width: '600px',
                top: '50%',
                left: '50%',
                right: 'auto',
                bottom: 'auto',
                marginRight: '-50%',
                transform: 'translate(-50%, -50%)'
            }
        };
        return (
            <Modal
                isOpen={this.props.isOpen}
                ariaHideApp={false}
                onAfterClose={this.flushResult}
                style={customStyles}
            >
                <span>{this.state.result}</span>
                <Box m={1}>
                    <form action="" method="post">
                        <Box mb={2}>
                            <InputLabel>литературный жанр</InputLabel>
                            <TextField id="genreType" required={true} fullWidth name="genreType" defaultValue={this.props.genreType}/>
                        </Box>
                        <Box className="buttons-horiz-right">
                            <Box mr={1}>
                                <Button
                                    variant="contained"
                                    color="primary"
                                    onClick={(evt) => this.saveGenre(evt, this.props.genreId)}
                                    type="submit">Сохранить
                                </Button>
                            </Box>
                            <Box>
                                <Button variant="contained" color="primary" onClick={this.state.closeModal}>Закрыть</Button>
                            </Box>
                        </Box>
                    </form>
                </Box>
            </Modal>
        );
    }
}

export default GenreModalWindow