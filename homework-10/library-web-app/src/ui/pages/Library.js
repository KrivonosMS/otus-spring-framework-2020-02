import React, {Component} from 'react';
import '../css/LibraryTable.css'
import BookModalWindow from "../components/BookModalWindow";
import DeleteBookConfirmWindow from "../components/DeleteBookConfirmWindow";

class Library extends Component {
    constructor(props) {
        super(props);
        this.state = {
            books: [],
            isOpenBookWindow: false,
            isOpenDeleteBookConfirmWindow: false,
            bookId: '0',
            title: '',
            author: '',
            genreId: ''
        };

        this.updateGrid = this.updateGrid.bind(this);
        this.openBookWindow = this.openBookWindow.bind(this);
        this.closeBookWindow = this.closeBookWindow.bind(this);
        this.closeDeleteBookConfirmWindow = this.closeDeleteBookConfirmWindow.bind(this);
    }

    componentDidMount() {
        this.updateGrid();
    }

    updateGrid() {
        fetch('/library/book/all')
            .then(response => response.json())
            .then(books => this.setState({books}));
    }

    openBookWindow(bookId = '', title = '', author = '', genreId = '') {
        this.setState({
            bookId: bookId,
            title: title,
            author: author,
            genreId: genreId,
            isOpenBookWindow: true
        })
    }

    closeBookWindow() {
        this.setState({
            isOpenBookWindow: false
        })
    }

    openDeleteBookConfirmWindow(bookId = '', title = '', author = '') {
        this.setState({
            bookId: bookId,
            title: title,
            author: author,
            isOpenDeleteBookConfirmWindow: true
        })
    }

    closeDeleteBookConfirmWindow() {
        this.setState({
            isOpenDeleteBookConfirmWindow: false
        })
    }

    render() {
        return (
            <div>
                <React.Fragment>
                    <h2>Список книг</h2>
                    <table className="books">
                        <thead>
                        <tr>
                            <th>Книга</th>
                            <th>Автор</th>
                            <th>Жанр</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.books.map((book, i) => (
                                <tr key={i}>
                                    <td>{book.title}</td>
                                    <td>{book.author.name}</td>
                                    <td>{book.genre.type}</td>
                                    <td>
                                        <button
                                            onClick={() => this.openBookWindow(book.id, book.title, book.author.name, book.genre.id)}>Редактировать
                                        </button>
                                    </td>
                                    <td>
                                        <button
                                            onClick={() => this.openDeleteBookConfirmWindow(book.id, book.title, book.author.name)}>Удалить
                                        </button>
                                    </td>
                                </tr>
                            ))
                        }
                        </tbody>
                    </table>
                    <div>
                        <button onClick={() => this.openBookWindow()}>Добавить книгу</button>
                    </div>
                </React.Fragment>
                <BookModalWindow
                    updateGrid={this.updateGrid}
                    isOpen={this.state.isOpenBookWindow}
                    closeModal={this.closeBookWindow}
                    bookId={this.state.bookId}
                    title={this.state.title}
                    author={this.state.author}
                    genreId={this.state.genreId}
                />
                <DeleteBookConfirmWindow
                    updateGrid={this.updateGrid}
                    isOpen={this.state.isOpenDeleteBookConfirmWindow}
                    closeModal={this.closeDeleteBookConfirmWindow}
                    bookId={this.state.bookId}
                    title={this.state.title}
                    author={this.state.author}
                />
            </div>
        )
    }
}

export default Library;