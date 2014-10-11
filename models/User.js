var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var User = new Schema({
  firstname: String,
  lastname: String,
  registered: {type: Date,  default: Date.now }
});

module.exports = mongoose.model('User', User);