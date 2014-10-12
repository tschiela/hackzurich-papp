var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var ParkingSession = new Schema({
  user: { type: Schema.Types.ObjectId, ref: 'User' },
  start: {type: Date,  default: Date.now },
  end: Date,
  price: Number,
  time: Number,
  payed: { type: Boolean, default: false }
});

module.exports = mongoose.model('ParkingSession', ParkingSession);