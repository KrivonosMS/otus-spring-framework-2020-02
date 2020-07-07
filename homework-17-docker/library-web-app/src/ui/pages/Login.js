import React, {useState} from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import {makeStyles} from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import {useHistory} from "react-router-dom";

const useStyles = makeStyles((theme) => ({
    paper: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.secondary.main,
    },
    form: {
        width: '100%',
        marginTop: theme.spacing(1),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
    },
}));

export default function Login() {
    const classes = useStyles();
    const [username, setUsername] = useState(false);
    const [password, setPassword] = useState(false);
    const [rememberMe, setRememberMe] = useState(false);
    const [error, setError] = useState(false);
    const history = useHistory();

    const auth = (event) => {
        event.preventDefault();
        const formData = new FormData();
        formData.append('j_username', username);
        formData.append('j_password', password);
        formData.append('remember-me', rememberMe);
        fetch("/j_auth", {
            method: "POST",
            body: formData
        })
            .then(response => {
                    if (response.status === 200) {
                        fetch("/auth/roles", {
                            method: "POST"
                        })
                            .then(resp => {
                                if (resp.status === 200) {
                                    resp.json().then(roles => {
                                        localStorage.setItem("username", JSON.stringify(formData.get("j_username")));
                                        localStorage.setItem("roles", JSON.stringify(roles));
                                        history.push("/");
                                    })
                                } else {
                                    localStorage.setItem("username", JSON.stringify(formData.get("j_username")));
                                    localStorage.setItem("roles", JSON.stringify([]));
                                    history.push("/");
                                }
                            });
                    } else if (response.status === 401) {
                        setError("Неправильный логин или пароль. Поробуйте еще раз");
                    } else {
                        setError("Непредвиденная ошибка");
                    }
                }
            );
    }

    return (
        <Container component="main" maxWidth="xs">
            <CssBaseline/>
            <div className={classes.paper}>
                <Avatar className={classes.avatar}>
                    <LockOutlinedIcon/>
                </Avatar>
                <span>{error}</span>
                <form className={classes.form} noValidate>
                    <TextField
                        variant="outlined"
                        margin="normal"
                        required
                        fullWidth
                        id="username"
                        label="Имя пользователя"
                        name="username"
                        onChange={e => setUsername(e.target.value)}
                        autoFocus
                    />
                    <TextField
                        variant="outlined"
                        margin="normal"
                        required
                        fullWidth
                        name="password"
                        label="Пароль"
                        type="password"
                        id="password"
                        onChange={e => setPassword(e.target.value)}
                    />
                    <FormControlLabel
                        control={<Checkbox value="remember-me" color="primary"/>}
                        label="Запомнить меня"
                        onChange={e => setRememberMe(e.target.value)}
                    />
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        color="primary"
                        className={classes.submit}
                        onClick={e => auth(e)}>
                        Войти
                    </Button>
                </form>
            </div>
        </Container>
    );
}