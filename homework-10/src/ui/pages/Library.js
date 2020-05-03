import React, {Component} from 'react';
import '../css/LibraryTable.css'
import BookModalWindow from "../components/BookModalWindow";

class Library extends Component {
    constructor(props) {
        super(props);
        this.state = {
            books: [],
            isOpen: false,
            bookId: '0',
            title: '',
            author: '',
            genreId: ''
        };

        this.openModal = this.openModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
    }

    componentDidMount() {
        fetch('/library/book/all')
            .then(response => response.json())
            .then(books => this.setState({books}));
    }

    openModal(bookId='', title='', author='', genreId='') {
        this.setState({
            bookId: bookId,
            title: title,
            author: author,
            genreId: genreId,
            isOpen: true
        })
    }

    closeModal() {
        this.setState({
            isOpen: false
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
                                        <button onClick={() => this.openModal(book.id, book.title, book.author.name, book.genre.id)}>Редактировать</button>
                                    </td>
                                    <td>
                                        <button onClick={this.openModal}>Удалить</button>
                                    </td>
                                </tr>
                            ))
                        }
                        </tbody>
                    </table>
                    <div>
                        <button onClick={() => this.openModal()}>Добавить книгу</button>
                    </div>
                </React.Fragment>
                <BookModalWindow
                    isOpen={this.state.isOpen}
                    closeModal={this.closeModal}
                    bookId={this.state.bookId}
                    title={this.state.title}
                    author={this.state.author}
                    genreId={this.state.genreId}
                />
            </div>
        )
    }
}

export default Library;