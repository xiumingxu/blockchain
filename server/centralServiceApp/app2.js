var express = require("express");
var app = require("express")();
var path = require("path");
var http = require("http").Server(app);
var ip = require("ip");
var io = require("socket.io")(http);

app.use(express.static("public"));
console.dir("currentIP: " + ip.address());

app.use("/public", express.static(path.join(__dirname + "/public")));
// app.get('/', function(req, res){
//   // The interface to change the server file
//   res.sendfile('index.html');
// });

io.on("connection", function (socket) {
	console.log("A user connected");

	// When the control interface sends a message.
	socket.on("message", function (data) {
		// ...emit a "message" event to every other socket
		console.log("got " + data + " from interface");
		socket.broadcast.emit("server", data);
	});
	socket.on("emotion", function (data) {
		// ...emit a "message" event to every other socket
		console.log("got " + data + " from emotion Detector");
		socket.broadcast.emit("server", data);
	});

	socket.on("disconnect", function () {
		console.log("A user disconnected");
	});
});

http.listen(3000, function () {
	console.log("listening message on port:3000");
});
