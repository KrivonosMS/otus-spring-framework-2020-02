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
            app.post('/library/book/2/delete', (req, res) => res.send(
                {
                    "success": false,
                    "message": "Непредвиденная ошибка при удалении книги. Обратитесь к администратору"
                }
            ));
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