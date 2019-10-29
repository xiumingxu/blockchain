var app = require("express")();

// Blockchain API connected
var Socket = require("blockchain.info/Socket");
var mySocket = new Socket();
mySocket.onOpen(printout);
// mySocket.onTransaction(printout);
mySocket.onBlock(printout);

function printout (e) {
	console.log("blockchain", e);
}

module.exports = app;
