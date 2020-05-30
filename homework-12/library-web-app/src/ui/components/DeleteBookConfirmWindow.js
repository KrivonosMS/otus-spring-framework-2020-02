import React, {Component} from 'react';
import '../css/DeleteBookConfirmWindow.css'
import Modal from "react-modal";
import Box from "@material-ui/core/Box";
import Button from "@material-ui/core/Button";
import InputLabel from "@material-ui/core/InputLabel";
import TextField from "@material-ui/core/TextField";

class DeleteBookConfirmWindow extends Component {
    constructor(props) {
        super(props);
        this.state = {
            closeModal: props.closeModal,
            updateGrid: props.updateGrid
        }

        this.deleteBook = this.deleteBook.bind(this);
        this.flushResult = this.flushResult.bind(this);
    }

    deleteBook(evt, bookId) {
        const url = '/library/book/' + bookId + '/delete';
        fetch(url, {
            method: 'POST',
            body: {"id": bookId},
            redirect: "follow"
        })
            .then(response => {
                if (response.redirected) {
                    document.location = response.url;
                } else {
                    return response;
                }
            })
            .then(response => response.json())
            .then(result => {
                if (result.success === true) {
                    this.state.updateGrid();
                    this.state.closeModal();

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
                <Box>
                    <span>{this.state.result}</span>
                    <h3>Вы, действительно, хотите удалить книгу?</h3>
                    <Box mb={2}>
                        <InputLabel>название книги</InputLabel>
                        <TextField
                            fullWidth
                            InputProps={{
                                readOnly: true,
                            }}
                            defaultValue={this.props.title}
                        />
                    </Box>
                    <Box mb={2}>
                        <InputLabel>автор</InputLabel>
                        <TextField
                            fullWidth
                            InputProps={{
                                readOnly: true,
                            }}
                            defaultValue={this.props.author}
                        />
                    </Box>
                    <Box className="buttons-horiz-centre">
                        <Box mr={1}>
                            <Button
                                onClick={(evt) => this.deleteBook(evt, this.props.bookId)}
                                variant="contained"
                                color="primary"
                                type="submit">Удалить
                            </Button>
                        </Box>
                        <Box>
                            <Button
                                onClick={this.state.closeModal}
                                variant="contained"
                                color="primary">Закрыть
                            </Button>
                        </Box>
                    </Box>
                </Box>
            </Modal>
        );
    }
}

export default DeleteBookConfirmWindow;