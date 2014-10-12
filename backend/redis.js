var redis = require('redis');

module.exports.init = function(){
  var pub = redis.createClient();

  // make the connection accessible for all other modules
  module.exports.pub = pub;
}