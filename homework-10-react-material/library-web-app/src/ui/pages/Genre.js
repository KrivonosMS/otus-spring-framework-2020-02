import React, {Component} from 'react';
import '../css/LibraryTable.css'
import Paper from "@material-ui/core/Paper";
import {Table, TableBody, TableCell, TableContainer, TableHead, TablePagination, TableRow} from "@material-ui/core";
import EditIcon from '@material-ui/icons/Edit';
import AddIcon from '@material-ui/icons/Add';
import IconButton from "@material-ui/core/IconButton";
import Box from "@material-ui/core/Box";
import Button from "@material-ui/core/Button";
import GenreModalWindow from "../components/GenreModalWindow";

class Genre extends Component {
    constructor() {
        super();
        this.state = {
            genreId: '',
            genreType: '',
            isOpenGenreWindow: false,
            isOpenDeleteGenreConfirmWindow: false,
            page: 0,
            rowsPerPage: 10,
            columns: [
                {id: 'genreType', label: 'Литературный жанр', minWidth: 170},
                {id: 'edit', label: '', width: 10, minWidth: 10}
            ],
            genres: []
        };

        this.updateGrid = this.updateGrid.bind(this);
        this.openGenreWindow = this.openGenreWindow.bind(this);
        this.closeGenreWindow = this.closeGenreWindow.bind(this);
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
        fetch('/library/genre/all')
            .then(response => response.json())
            .then(genres => this.setState({genres: genres}));
    }

    openGenreWindow(genreId = '', genreType = '') {
        this.setState({
            genreId: genreId,
            genreType: genreType,
            isOpenGenreWindow: true
        })
    }

    closeGenreWindow() {
        this.setState({
            genreId: '',
            genreType: '',
            isOpenGenreWindow: false
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
                    <TableContainer >
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
                                {this.state.genres.slice(this.state.page * this.state.rowsPerPage, this.state.page * this.state.rowsPerPage + this.state.rowsPerPage).map((genre) => {
                                    return (
                                        <TableRow hover role="checkbox" tabIndex={-1} key={genre.id}>
                                            <TableCell key='genreType'>{genre.type}</TableCell>
                                            <TableCell key='edit'>
                                                <IconButton variant="contained" onClick={() => this.openGenreWindow(genre.id, genre.type)}>
                                                    <EditIcon/>
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
                        count={this.state.genres.length}
                        rowsPerPage={this.state.rowsPerPage}
                        page={this.state.page}
                        onChangePage={this.handleChangePage}
                        onChangeRowsPerPage={this.handleChangeRowsPerPage}
                    />
                    <GenreModalWindow
                        updateGrid={this.updateGrid}
                        isOpen={this.state.isOpenGenreWindow}
                        closeModal={this.closeGenreWindow}
                        genreId={this.state.genreId}
                        genreType={this.state.genreType}
                    />
                </Paper>
                <Box mt={1}>
                    <Button
                        variant="contained"
                        onClick={() => this.openGenreWindow()}
                        startIcon={<AddIcon />}
                        type="submit">Добавить
                    </Button>
                </Box>
            </Box>
        );
    }
}

export default Genre;