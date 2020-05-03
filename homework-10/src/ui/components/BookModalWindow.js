import React, {Component} from 'react';
import '../css/LibraryModal.css'
import Modal from "react-modal";

class BookModalWindow extends Component {
    constructor(props) {
        super(props);
        this.state = {
            closeModal: this.props.closeModal,
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
        console.log(bookId);
        const url = bookId ? '/library/book/' + bookId + '/edit' : '/library/book/add';
        const data = new FormData(document.querySelector("form"));
        evt.preventDefault();
        fetch(url, {
            method: 'POST',
            body: data
        })
            .then(response => response.json())
            .then(result => result.success === true ?
                this.setState({result: 'Книга успешно сохранена'}) :
                result.message ?
                    this.setState({result: result.message}) :
                    this.setState({result: "Непредвиденная ошибка"})
            );
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
                onAfterOpen={this.loadGenres}
                onAfterClose={this.flushResult}
                style={customStyles}
            >
                <div>
                    <span>{this.state.result}</span>
                    <form action="/library/book/add" method="post">
                        <div className="row">
                            <label>Название книги:</label>
                            <input id="book-title" type="text" required name="title" defaultValue={this.props.title}
                                   size={100}/>
                        </div>
                        <div className="row">
                            <label>Имя автора:</label>
                            <input id="author-name" type="text" required name="author" defaultValue={this.props.author}
                                   size={100}/>
                        </div>
                        <div className="row">
                            <label>Жар:</label>
                            <select id="book-genre" size="1" required name="genreId" style={{width: 300}}>
                                {
                                    this.state.genres.map((genre, i) => (
                                        this.props.genreId === genre.id ?
                                            <option selected key={genre.id} value={genre.id}>{genre.type}</option> :
                                            <option key={genre.id} value={genre.id}>{genre.type}</option>
                                    ))
                                }
                            </select>
                        </div>
                        <button onClick={(evt) => this.saveBook(evt, this.props.bookId)} type="submit">Сохранить
                        </button>
                        <button onClick={this.state.closeModal}>Закрыть</button>
                    </form>
                </div>
            </Modal>
        );
    }
}

export default BookModalWindow;