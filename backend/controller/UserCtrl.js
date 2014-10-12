var User = require('../models/User');

exports.insert = function(req, res){
  var user = new User({
    firstname: req.body.firstname,
    lastname: req.body.lastname
  });

  user.save(function(error, response){
    if(error){
      res.status(400).send(error);
    } else {
      res.json(response);
    }
  });
}

exports.get = function(req, res){
  var user = new User({
    firstname: 'Thomas',
    lastname: 'Schiela'
  });

  User.findOne({_id: req.params.id}, function(error, response){
    if(error){
      res.status(400).send(error);
    } else {
      res.json(response);
    }
  });
}