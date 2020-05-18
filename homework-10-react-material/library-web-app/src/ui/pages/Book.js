import React, {Component} from 'react';
import Box from "@material-ui/core/Box";
import Typography from "@material-ui/core/Typography";
import CardContent from "@material-ui/core/CardContent";
import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import Button from "@material-ui/core/Button";
import Grid from "@material-ui/core/Grid";
import InputLabel from "@material-ui/core/InputLabel";
import TextField from "@material-ui/core/TextField";
import Divider from "@material-ui/core/Divider";
import DeleteCommentConfirmWindow from "../components/DeleteCommentConfirmWindow";

class Book extends Component {
    constructor(props) {
        super(props);
        this.state = {
            id: props.match.params.bookId,
            title: '',
            author: '',
            genre: '',
            comments: [],
            newComment: '',
            isOpenDeleteCommentConfirmWindow: false,
            commentId: ''
        };

        this.updateInfo = this.updateInfo.bind(this);
        this.updateComments = this.updateComments.bind(this);
        this.saveComment = this.saveComment.bind(this);
        this.setNewComment = this.setNewComment.bind(this);
        this.openDeleteBookConfirmWindow = this.openDeleteBookConfirmWindow.bind(this);
        this.closeDeleteCommentConfirmWindow = this.closeDeleteCommentConfirmWindow.bind(this);
    }

    componentDidMount() {
        this.updateInfo();
    }

    updateInfo() {
        fetch('/library/book/' + this.state.id)
            .then(response => response.json())
            .then(book => {
                this.setState({title: book.title});
                this.setState({author: book.author.name});
                this.setState({genre: book.genre.type});
                this.setState({comments: book.comments});
            })
    }

    updateComments(commentId) {
        let newArr = this.state.comments.filter(v => v.id !== commentId);
        this.setState({
            comments: newArr
        });
    }

    saveComment(evt) {
        evt.preventDefault();
        fetch('/library/book/' + this.state.id + '/add/comment', {
            method: 'POST',
            headers: new Headers({
                'Content-Type': 'application/x-www-form-urlencoded'
            }),
            body: 'text=' + this.state.newComment
        })
            .then(response => response.json())
            .then(result => {
                if (result.id) {
                    let newArr = this.state.comments;
                    newArr.push({
                        id: result.id,
                        text: result.text,
                        creationDate: result.creationDate
                    });
                    this.setState({
                            comments: newArr
                        }
                    )
                }
            })
    }

    setNewComment(value) {
        this.setState({
                newComment: value
            }
        )
    }

    openDeleteBookConfirmWindow(commentId) {
        this.setState({
            isOpenDeleteCommentConfirmWindow: true,
            commentId: commentId
        })
    }

    closeDeleteCommentConfirmWindow() {
        this.setState({
            isOpenDeleteCommentConfirmWindow: false
        })
    }

    render() {
        return (
            <Grid m={3} container spacing={3}>
                <Grid item xs={6}>
                    <Box m={3}>
                        <Card>
                            <CardContent>
                                <Box mb={2}>
                                    <Box mb={1}>
                                        <InputLabel>книга</InputLabel>
                                        <Typography variant="h4">{this.state.title}</Typography>
                                    </Box>
                                    <Box mb={1}>
                                        <InputLabel>автор</InputLabel>
                                        <Typography variant="h5">{this.state.author}</Typography>
                                    </Box>
                                    <Box mb={1}>
                                        <InputLabel>жанр</InputLabel>
                                        <Typography variant="h5" color="textSecondary">{this.state.genre}</Typography>
                                    </Box>
                                </Box>
                                <Divider variant="middle"/>
                                <Box mt={2}>
                                    <form action="" method="post">
                                        <Box mb={2}>
                                            <TextField
                                                id="comment"
                                                variant="outlined"
                                                required={true}
                                                label="новый комментарий"
                                                onChange={e => this.setNewComment(e.target.value)}
                                                fullWidth
                                                name="comment"/>
                                        </Box>
                                        <Box>
                                            <Box mr={1}>
                                                <Button
                                                    variant="contained"
                                                    color="primary"
                                                    onClick={this.saveComment}
                                                    type="submit">Добавить
                                                </Button>
                                            </Box>
                                        </Box>
                                    </form>
                                </Box>
                            </CardContent>
                        </Card>
                    </Box>
                </Grid>
                <Grid item xs={6}>
                    <Box m={3}>
                        <Typography color="textSecondary" variant="h5">комментарии:</Typography>
                        {this.state.comments.map((comment) => (
                            <Box key={comment.id} mb={1}>
                                <Card key={comment.id} elevation={3}>
                                    <CardContent>
                                        <Typography color="textSecondary" gutterBottom>{comment.creationDate}</Typography>
                                        <Typography variant="body2">{comment.text}</Typography>
                                    </CardContent>
                                    <CardActions>
                                        <Button size="small" variant="contained" onClick={() => this.openDeleteBookConfirmWindow(comment.id)}>Удалить</Button>
                                    </CardActions>
                                </Card>
                            </Box>
                        ))}
                    </Box>
                </Grid>
                <DeleteCommentConfirmWindow
                    updateComments={this.updateComments}
                    isOpen={this.state.isOpenDeleteCommentConfirmWindow}
                    closeModal={this.closeDeleteCommentConfirmWindow}
                    commentId={this.state.commentId}
                />
            </Grid>
        );
    }
}

export default Book;