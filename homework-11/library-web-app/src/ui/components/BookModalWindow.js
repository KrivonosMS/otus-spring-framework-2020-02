import React, {Component} from 'react';
import '../css/BookModalWindow.css';
import Modal from "react-modal";

class BookModalWindow extends Component {
    constructor(props) {
        super(props);
        this.state = {
            closeModal: this.props.closeModal,
            updateGrid: this.props.updateGrid,
            genres: [],
            bookId: '',
            title: '',
            author: '',
            genreId: '',
            genreType: ''
        }

        this.loadGenresAndSetData = this.loadGenresAndSetData.bind(this);
        this.saveBook = this.saveBook.bind(this);
        this.flushResult = this.flushResult.bind(this);
        this.setTitle = this.setTitle.bind(this);
        this.setAuthor = this.setAuthor.bind(this);
        this.setGenre = this.setGenre.bind(this);
        this.setGenreId = this.setGenreId.bind(this);
        this.setGenreType = this.setGenreType.bind(this);
    }

    loadGenresAndSetData() {
        this.setTitle(this.props.title);
        this.setAuthor(this.props.author);
        this.setGenreId(this.props.genreId);

        fetch('/library/genre/all')
            .then(response => response.json())
            .then(genres => {
                this.setState({genres});
                this.state.genreId === '' ? this.setGenreType(genres[0].type) : this.setGenreType(genres.find(genre => genre.id === this.state.genreId).type);
                this.state.genreId === '' ? this.setGenreId(genres[0].id) : this.setGenreId(this.state.genreId);
            });
    }

    saveBook(evt, bookId) {
        const url = bookId ? '/library/book/' + bookId + '/edit' : '/library/book/add';
        evt.preventDefault();
        fetch(url, {
            method: 'POST',
            body: JSON.stringify({
                "title": this.state.title,
                "author": this.state.author,
                "genreId": this.state.genreId,
                "type": this.state.genreType
            }),
            headers: {
                'Content-Type': 'application/json'
            }
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

    setTitle(value) {
        this.setState({
                title: value
            }
        )
    }

    setAuthor(value) {
        this.setState({
                author: value
            }
        )
    }

    setGenre(target) {
        this.setGenreId(target.value);
        this.setGenreType(target.textContent);
    }

    setGenreId(value) {
        this.setState({
                genreId: value
            }
        );
    }

    setGenreType(value) {
        this.setState({
                genreType: value
            }
        );
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
                onAfterOpen={this.loadGenresAndSetData}
                onAfterClose={this.flushResult}
                style={customStyles}
            >
                <div>
                    <span>{this.state.result}</span>
                    <form action="/library/book/add" method="post">
                        <div className="row">
                            <label>Название книги:</label>
                            <input id="book-title" type="text" required name="title" defaultValue={this.props.title} onChange={e => this.setTitle(e.target.value)} size={100}/>
                        </div>
                        <div className="row">
                            <label>Имя автора:</label>
                            <input id="author-name" type="text" required name="author" defaultValue={this.props.author} onChange={e => this.setAuthor(e.target.value)} size={100}/>
                        </div>
                        <div className="row">
                            <label>Жар:</label>
                            <select id="book-genre" size="1" required name="genreId" style={{width: 300}} onChange={e => this.setGenre(e.target)}>
                                {
                                    this.state.genres.map((genre, i) => (
                                        this.props.genreId === genre.id ?
                                            <option selected key={genre.id} value={genre.id}>{genre.type}</option> :
                                            <option key={genre.id} value={genre.id}>{genre.type}</option>
                                    ))
                                }
                            </select>
                        </div>
                        <div className="buttons-horiz-right">
                            <button onClick={(evt) => this.saveBook(evt, this.props.bookId)} type="submit">Сохранить</button>
                            <button onClick={this.state.closeModal}>Закрыть</button>
                        </div>
                    </form>
                </div>
            </Modal>
        );
    }
}

export default BookModalWindow;