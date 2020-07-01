const HtmlWebpackPlugin = require('html-webpack-plugin')
const path = require('path');

module.exports = {
    entry: './src/ui/index.js',
    devtool: 'inline-source-map',
    output: {
        path: path.resolve(__dirname),
        filename: 'bundle.js',
        libraryTarget: 'umd'
    },

    devServer: {
        contentBase: path.resolve(__dirname) + '/src/ui',
        compress: true,
        port: 9000,
        host: 'localhost',
        open: true,
        before: (app) => {
            app.get('/library/book/all', (req, res) => res.send(
                [
                    {
                        "id": 1,
                        "title": "Повести Белкина (сборник)",
                        "author": {
                            "id": 1,
                            "name": "Александр Пушкин"
                        },
                        "genre": {
                            "id": 2,
                            "type": "Литература 19 века"
                        }
                    },
                    {
                        "id": 2,
                        "title": "Евгений Онегин",
                        "author": {
                            "id": 1,
                            "name": "Александр Пушкин"
                        },
                        "genre": {
                            "id": 1,
                            "type": "Классическая проза"
                        }
                    }
                ]
            ));
            app.get('/library/book/1', (req, res) => res.send(
                {
                    "id": 1,
                    "title": "Повести Белкина (сборник)",
                    "author": {
                        "id": 1,
                        "name": "Александр Пушкин"
                    },
                    "genre": {
                        "id": 2,
                        "type": "Литература 19 века"
                    },
                    "comments": [
                        {
                            "id": 1,
                            "text": "комментарий 1",
                            "creationDate": "25.04.2019 20:30"
                        },
                        {
                            "id": 2,
                            "text": "комментарий 2",
                            "creationDate": "25.04.2019 20:30"
                        },
                        {
                            "id": 3,
                            "text": "комментарий 3",
                            "creationDate": "25.04.2019 20:30"
                        },
                        {
                            "id": 4,
                            "text": "комментарий 4",
                            "creationDate": "25.04.2019 20:30"
                        }
                    ]
                }
            ));
            app.get('/library/genre/all', (req, res) => res.send(
                [
                    {
                        "id": 1,
                        "type": "Классическая проза"
                    },
                    {
                        "id": 2,
                        "type": "Литература 19 века"
                    }
                ]
            ));
            app.post('/library/book/add', (req, res) => res.send(
                {
                    "success": true
                }
            ));
            app.post('/library/book/1/edit', (req, res) => res.send(
                {
                    "success": true
                }
            ));
            app.post('/library/book/2/edit', (req, res) => res.send(
                {
                    "success": false,
                    "message": "Непредвиденная ошибка при сохранении книги. Обратитесь к администратору"
                }
            ));
            app.post('/library/book/1/delete', (req, res) => res.send(
                {
                    "success": true
                }
            ));
            app.post('/library/book/2/delete', (req, res) => {
                res.statusCode = 401;
                res.send();
            });
            app.post('/library/genre/add', (req, res) => res.send(
                {
                    "success": true
                }
            ));
            app.post('/library/genre/1/edit', (req, res) => res.send(
                {
                    "success": true
                }
            ));
            app.post('/library/genre/2/edit', (req, res) => res.send(
                {
                    "success": false,
                    "message": "Непредвиденная ошибка при сохранении литературного жанра. Обратитесь к администратору"
                }
            ));
            app.post('/library/book/delete/comment/1', (req, res) => res.send(
                {
                    "success": true
                }
            ));
            app.post('/library/book/delete/comment/2', (req, res) => res.send(
                {
                    "success": false,
                    "message": "Непредвиденная ошибка при удалении комментария. Обратитесь к администратору"
                }
            ));
            app.post('/library/book/1/add/comment', (req, res) => res.send(
                {
                    "id": 5,
                    "text": "комментарий 5",
                    "creationDate": "25.04.2019 20:30"
                }
            ));
            app.post('/library/logout', (req, res) => {
                res.send();
            });
            app.post('/j_auth', (req, res) => {
                res.send(
                    {
                        "success": true,
                        "message": null
                    }
                );
            });
            app.post('/auth/roles', (req, res) => {
                console.log(req);
                res.send(["USER", "ADMIN"]);
            });
        }
    },

    module: {
        loaders: [
            {
                test: /\.js$/,
                exclude: /(node_modules|bower_components|build)/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['env', 'react']
                    }
                }
            },
            {
                test: /\.css$/,
                exclude: /node_modules/,
                use: ['style-loader', 'css-loader'],
            }
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            filename: 'index.html',
            inject: false,
            template: 'src/ui/index.html'
        })
    ]
}