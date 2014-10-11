var express = require('express');
var app = express();
var bodyParser = require('body-parser');

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

var port = process.env.PORT || 3000;
var mongodb = require('./mongodb');

// load routes
require('./config/routes')(app);

// init db connection
mongodb.init(function(error){
  if(!error){
    app.listen(port);
    console.log('parkin api server started on port %s', port);
  } else {
    console.error(error);
  }
});


