var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var ParkingSession = new Schema({
  user: { type: Schema.Types.ObjectId, ref: 'User' },
  start: Date,
  end: Date,
  price: Number
});

module.exports = mongoose.model('ParkingSession', ParkingSession);