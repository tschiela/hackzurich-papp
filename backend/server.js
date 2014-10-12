var http = require('http');
var express = require('express');
var app = express();
var port = process.env.PORT || 3000;
var mongodb = require('./mongodb');
var server = http.createServer(app);
var io = require('socket.io').listen(server);
var bodyParser = require('body-parser');
var logger = require('morgan');
var redis = require('./redis');


// configure express
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(express.static(__dirname + '/public'));
app.use(logger('combined'));

// load routes
require('./config/routes')(app);

// init db connection
mongodb.init(function(error){
  if(!error){
    server.listen(port);
    console.log('parkin api server started on port %s', port);
  } else {
    console.error(error);
  }
});

// init redis
redis.init();

// handle websockets
require('./controller/SocketCtrl')(io);


