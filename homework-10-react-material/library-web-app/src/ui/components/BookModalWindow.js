import React, {Component} from 'react';
import '../css/BookModalWindow.css';
import Modal from "react-modal";
import Button from "@material-ui/core/Button";
import Box from "@material-ui/core/Box";
import TextField from "@material-ui/core/TextField";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";
import InputLabel from "@material-ui/core/InputLabel";

class BookModalWindow extends Component {
    constructor(props) {
        super(props);
        this.state = {
            closeModal: props.closeModal,
            updateGrid: props.updateGrid,
            genres: []
        }

        this.loadGenres = this.loadGenres.bind(this);
        this.saveBook = this.saveBook.bind(this);
        this.flushResult = this.flushResult.bind(this);
    }

    loadGenres() {
        fetch('/library/genre/all')
            .then(response => response.json())
            .then(genres => this.setState({genres}));
    }

    saveBook(evt, bookId) {
        const url = bookId ? '/library/book/' + bookId + '/edit' : '/library/book/add';
        const data = new FormData(document.querySelector("form"));
        evt.preventDefault();
        fetch(url, {
            method: 'POST',
            body: data
        })
            .then(response => response.json())
            .then(result => {
                if (result.success === true) {
                    this.setState({result: 'Книга успешно сохранена'})
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
                onAfterOpen={this.loadGenres}
                onAfterClose={this.flushResult}
                style={customStyles}
            >
                <span>{this.state.result}</span>
                <Box m={1}>
                    <form action="/library/book/add" method="post">
                        <Box mb={2}>
                            <InputLabel>название книги</InputLabel>
                            <TextField id="book-title" required fullWidth name="title" defaultValue={this.props.title}/>
                        </Box>
                        <Box mb={2}>
                            <InputLabel>имя автора</InputLabel>
                            <TextField id="author-name" required fullWidth name="author" defaultValue={this.props.author}/>
                        </Box>
                        <Box mb={2}>
                            <InputLabel>жанр</InputLabel>
                            <Select
                                id="book-genre"
                                fullWidth
                                defaultValue={this.props.genreId}
                                size="1"
                                required
                                name="genreId">
                                {
                                    this.state.genres.map((genre, i) => (
                                        <MenuItem key={genre.id} value={genre.id}>{genre.type}</MenuItem>
                                    ))
                                }
                            </Select>
                        </Box>
                        <Box className="buttons-horiz-right">
                            <Box mr={1}>
                                <Button
                                    variant="contained"
                                    color="primary"
                                    onClick={(evt) => this.saveBook(evt, this.props.bookId)}
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

export default BookModalWindow