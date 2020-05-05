import React, {Component} from 'react';
import '../css/DeleteBookConfirmWindow.css'
import Modal from "react-modal";

class DeleteBookConfirmWindow extends Component {
    constructor(props) {
        super(props);
        this.state = {
            closeModal: this.props.closeModal,
            updateGrid: this.props.updateGrid
        }

        this.deleteBook = this.deleteBook.bind(this);
        this.flushResult = this.flushResult.bind(this);
    }

    deleteBook(evt, bookId) {
        const url = '/library/book/' + bookId + '/delete';
        fetch(url, {
            method: 'POST',
            body: {"id": bookId}
        })
            .then(response => response.json())
            .then(result => {
                if (result.success === true) {
                    this.setState({result: 'Книга успешно удалена'})
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
                <div>
                    <span>{this.state.result}</span>
                    <h3>Вы, действительно, хотите удалить книгу?</h3>
                    <div className="row">
                        <span>название: {this.props.title}</span>
                    </div>
                    <div className="row">
                        <span>автор: {this.props.author}</span>
                    </div>
                    <div className="buttons-horiz-centre">
                        <button onClick={(evt) => this.deleteBook(evt, this.props.bookId)} type="submit">Удалить</button>
                        <button onClick={this.state.closeModal}>Закрыть</button>
                    </div>
                </div>
            </Modal>
        );
    }
}

export default DeleteBookConfirmWindow;