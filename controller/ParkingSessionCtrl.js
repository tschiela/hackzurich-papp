var ParkingSession = require('../models/ParkingSession');
var redis = require('../redis');

exports.checkIn = function(req, res){
  var parkingSession = new ParkingSession({
    user: req.body.userId
  });

  parkingSession.save(function(error, response){
    if(error){
      res.status(400).send(error);
    } else {
      res.json(response);
      redis.pub.publish('bump', 'open');
    }
  });
}

exports.checkOut = function(req, res){
  ParkingSession.findById(req.body._id, function(error, parkingSession){
    if(error){
      res.status(400).send(error);
    } else {
      parkingSession.time = roundTwoDigits(calcTime(parkingSession.start));
      parkingSession.price = roundTwoDigits(calcPrice(parkingSession.time));
      parkingSession.end = new Date();

      parkingSession.save(function(error, response){
        if(error){
          res.status(400).send(error);
        } else {
          res.json(response);
        }
      });
    }
  });
}

exports.pay = function(req, res){
  ParkingSession.findByIdAndUpdate(
    {_id: req.body._id},
    {$set: {payed: true}},
    function(error, response){
      if(error){
        res.status(400).send(error);
      } else {
        res.json(response);
      }
    }
  );
}

exports.get = function(req, res){
  ParkingSession.findOne({_id: req.params.id}, function(error, parkingSession){
    if(error){
      res.status(400).send(error);
    } else {
      if(!parkingSession.end){
        parkingSession.time = roundTwoDigits(calcTime(parkingSession.start));
        parkingSession.price = roundTwoDigits(calcPrice(parkingSession.time));
      }

      res.json(parkingSession);
    }
  });
}

exports.getForUser = function(req, res){
  ParkingSession.find({user: req.params.id}, function(error, parkingSessions){
    if(error){
      res.status(400).send(error);
    } else {
      res.json(parkingSessions);
    }
  });
}

function calcTime(start){
  var ONE_HOUR = 3600000;
  var now = new Date();
  var difference = now.getTime() - start.getTime();

  return (difference / ONE_HOUR);
}

function calcPrice(hours){
  return hours*1.5;
}

function roundTwoDigits(number){
  return Math.round(number*100)/100;
}