import React, {Component} from 'react';
import '../css/LibraryTable.css'
import Paper from "@material-ui/core/Paper";
import {Table, TableBody, TableCell, TableContainer, TableHead, TablePagination, TableRow} from "@material-ui/core";
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';
import AddIcon from '@material-ui/icons/Add';
import SettingsEthernetIcon from '@material-ui/icons/SettingsEthernet';
import BookModalWindow from "../components/BookModalWindow";
import DeleteBookConfirmWindow from "../components/DeleteBookConfirmWindow";
import IconButton from "@material-ui/core/IconButton";
import Box from "@material-ui/core/Box";
import Button from "@material-ui/core/Button";
import {Link} from "react-router-dom";

class Library extends Component {
    constructor() {
        super();
        this.state = {
            books: [],
            isOpenBookWindow: false,
            isOpenDeleteBookConfirmWindow: false,
            bookId: '0',
            title: '',
            author: '',
            genreId: '',
            page: 0,
            rowsPerPage: 10,
            columns: [
                {id: 'title', label: 'Книга', minWidth: 170},
                {id: 'author', label: 'Автор', minWidth: 170},
                {id: 'genre', label: 'Жанр', minWidth: 170},
                {id: 'edit', label: '', width: 10, minWidth: 10},
                {id: 'delete', label: '', width: 10, minWidth: 10},
                {id: 'link', label: '', width: 10, minWidth: 10}
            ],

            rows: []
        };

        this.updateGrid = this.updateGrid.bind(this);
        this.openBookWindow = this.openBookWindow.bind(this);
        this.closeBookWindow = this.closeBookWindow.bind(this);
        this.closeDeleteBookConfirmWindow = this.closeDeleteBookConfirmWindow.bind(this);
        this.handleChangePage = this.handleChangePage.bind(this);
        this.handleChangeRowsPerPage = this.handleChangeRowsPerPage.bind(this);
    }

    componentDidMount() {
        this.updateGrid();
    }

    handleChangePage(event, newPage) {
        this.setState({
            page: newPage
        })
    };

    handleChangeRowsPerPage(event) {
        this.setState({
            page: 0,
            rowsPerPage: +event.target.value
        })
    };

    updateGrid() {
        fetch('/library/book/all', {
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
            .then(books => this.setState({rows: books}));
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
        const styles = {
            root: {
                width: '100%'
            }
        };

        return (
            <Box>
                <Paper style={styles.root}>
                    <TableContainer>
                        <Table stickyHeader aria-label="sticky table">
                            <TableHead>
                                <TableRow>
                                    {this.state.columns.map((column) => (
                                        <TableCell
                                            key={column.id}
                                            style={{minWidth: column.minWidth, width: column.width ? column.width : ''}}
                                        >
                                            {column.label}
                                        </TableCell>
                                    ))}
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {this.state.rows.slice(this.state.page * this.state.rowsPerPage, this.state.page * this.state.rowsPerPage + this.state.rowsPerPage).map((row) => {
                                    return (
                                        <TableRow hover role="checkbox" tabIndex={-1} key={row.id}>
                                            <TableCell key='title'>{row.title}</TableCell>
                                            <TableCell key='author'>{row.author.name}</TableCell>
                                            <TableCell key='genre'>{row.genre.type}</TableCell>
                                            <TableCell key='edit'>
                                                <IconButton variant="contained" onClick={() => this.openBookWindow(row.id, row.title, row.author.name, row.genre.id)}>
                                                    <EditIcon/>
                                                </IconButton>
                                            </TableCell>
                                            <TableCell key='delete'>
                                                <IconButton variant="contained" onClick={() => this.openDeleteBookConfirmWindow(row.id, row.title, row.author.name)}>
                                                    <DeleteIcon/>
                                                </IconButton>
                                            </TableCell>
                                            <TableCell key='link'>
                                                <IconButton variant="contained" component={Link} to={'/book/' + row.id}>
                                                    <SettingsEthernetIcon/>
                                                </IconButton>
                                            </TableCell>
                                        </TableRow>
                                    );
                                })}
                            </TableBody>
                        </Table>
                    </TableContainer>
                    <TablePagination
                        rowsPerPageOptions={[10, 25, 100]}
                        component="div"
                        count={this.state.rows.length}
                        rowsPerPage={this.state.rowsPerPage}
                        page={this.state.page}
                        onChangePage={this.handleChangePage}
                        onChangeRowsPerPage={this.handleChangeRowsPerPage}
                    />
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
                </Paper>
                <Box mt={1}>
                    <Button
                        variant="contained"
                        onClick={() => this.openBookWindow()}
                        startIcon={<AddIcon/>}
                        type="submit">Добавить
                    </Button>
                </Box>
            </Box>
        );
    }
}

export default Library;