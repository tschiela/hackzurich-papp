var mongoose = require('mongoose');
var env = process.env.NODE_ENV || 'development';
var config = require('./config/config')[env]

exports.init = function(callback){
  mongoose.connect(config.mongodb, function(error){
    callback(error);
  });
}