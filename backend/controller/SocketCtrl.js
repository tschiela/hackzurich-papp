var redis = require('redis');

module.exports = function(io){
  io.sockets.on('connection', function (socket) {
    var sub = redis.createClient();

    sub.on('ready', function(){
      sub.subscribe('bump');
    });

    sub.on("message", function (channel, state) {
      socket.emit('stateChange', state);
    });

    socket.on('disconnect', function () {
      sub.unsubscribe('bump');
      sub.quit();
    });
  });
}