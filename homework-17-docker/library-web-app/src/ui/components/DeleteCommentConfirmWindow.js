import React, {Component} from 'react';
import '../css/DeleteBookConfirmWindow.css'
import Modal from "react-modal";
import Box from "@material-ui/core/Box";
import Button from "@material-ui/core/Button";
import {withRouter} from 'react-router-dom'

class DeleteCommentConfirmWindow extends Component {
    constructor(props) {
        super(props);
        this.state = {
            closeModal: props.closeModal,
            updateComments: props.updateComments,
            result: ''
        }

        this.deleteComment = this.deleteComment.bind(this);
        this.flushResult = this.flushResult.bind(this);
    }

    deleteComment(evt, commentId) {
        fetch('/library/book/delete/comment/' + commentId, {
            method: 'POST',
            redirect: "follow"
        })
            .then(response => {
                if (response.status === 200) {
                    response.json().then(result => {
                        if (result.success === true) {
                            this.state.updateComments(commentId);
                            this.state.closeModal();
                        } else {
                            const msg = result.message ? result.message : "Возникла непредвиденная ошибка";
                            this.setState({result: msg})
                        }
                    });
                } else if (response.status === 403) {
                    return Promise.reject(new Error("У Вас недостаточно прав на выполнение этой операции"));
                } else if (response.status === 401) {
                    this.state.closeModal();
                    this.props.history.push("/login");
                } else {
                    return Promise.reject(new Error("Возникла непредвиденная ошибка"));
                }
            })
            .catch(err => {
                this.setState({result: err.message})
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
                    <h3>Вы, действительно, хотите удалить комментарий?</h3>
                    <Box className="buttons-horiz-centre">
                        <Box mr={1}>
                            <Button
                                onClick={(evt) => this.deleteComment(evt, this.props.commentId)}
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

export default withRouter(DeleteCommentConfirmWindow);