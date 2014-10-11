var ParkinCtrl = require('../controller/ParkinCtrl');
var env = process.env.NODE_ENV || 'development';
var config = require('./config')[env]
var apiPrefix = '/api/v' + config.apiVersion;

module.exports = function(app){
  // post session (checkIn)
  app.post(apiPrefix + '/checkIn', ParkinCtrl.checkIn);

  // pay (checkOut)
  app.post(apiPrefix + '/checkOut', function(req, res){});

  // google wallet pay callback
  app.post('/paycallback', function(req, res){});

  // get information (time and price) about one session
  app.get(apiPrefix + '/session/:id', function(req, res){});

  // insert new user
  app.post(apiPrefix + '/user', function(req, res){});

  // get data to a specific user
  app.get(apiPrefix + '/user/:id', function(req, res){});

  // get parking history (all sessions)
  app.get(apiPrefix + '/user/:id/sessions', function(req, res){});
}