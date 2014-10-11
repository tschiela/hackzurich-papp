var User = require('../models/User');

exports.checkIn = function(req, res){

  var user = new User({
    firstname: 'Thomas',
    lastname: 'Schiela'
  });

  user.save(function(error, response){
    if(error){
      res.status(400).send(error);
    }

    res.json(response);
  });

}