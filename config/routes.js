var env = process.env.NODE_ENV || 'development';
var config = require('./config')[env]
var apiPrefix = '/api/v' + config.apiVersion;

var UserCtrl = require('../controller/UserCtrl');
var ParkingSessionCtrl = require('../controller/ParkingSessionCtrl');

module.exports = function(app){
  // post session (checkIn)
  app.post(apiPrefix + '/checkIn', ParkingSessionCtrl.checkIn);

  // checkOut
  app.post(apiPrefix + '/checkOut', ParkingSessionCtrl.checkOut);

  // pay
  app.post(apiPrefix + '/pay', ParkingSessionCtrl.pay);

  // get information (time and price) about one session
  app.get(apiPrefix + '/session/:id', ParkingSessionCtrl.get);

  // insert new user
  app.post(apiPrefix + '/user', UserCtrl.insert);

  // get data to a specific user
  app.get(apiPrefix + '/user/:id', UserCtrl.get);

  // get parking history (all sessions)
  app.get(apiPrefix + '/user/:id/sessions', ParkingSessionCtrl.getForUser);

  app.get('/test', function(req, res){
    res.json({ok: true});
  });

  // TODO: google wallet postback url
  app.post('/postback', function(req, res){});
}