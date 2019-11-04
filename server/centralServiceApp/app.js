// Require the framework and instantiate it
const express = require("express");
const app = express();

const bodyParser = require("body-parser");
let jwt = require("jsonwebtoken");
let config = require("./config");
let middleware = require("./middleware");
//Enhancements: Finish security login mechanism
class HandlerGenerator {
	login (req, res) {
		console.log(req, res);
		let username = req.body.username;
		let password = req.body.password;

		// For the given username fetch user from DB
		let mockedUsername = "admin";
		let mockedPassword = "password";

		if (username && password) {
			if (username === mockedUsername && password === mockedPassword) {
				let token = jwt.sign({ username: username }, config.secret, {
					expiresIn : "24h" // expires in 24 hours
				});
				// return the JWT token for the future API calls
				res.json({
					success : true,
					message : "Authentication successful!",
					token   : token
				});
			}
			else {
				res.send(403).json({
					success : false,
					message : "Incorrect username or password"
				});
			}
		}
		else {
			res.send(400).json({
				success : false,
				message : "Authentication failed! Please check the request"
			});
		}
	}
	index (req, res) {
		res.json({
			success : true,
			message : "Index page"
		});
	}
}

var jobId = 0;
var clientId = 0;
const blockHeader = {
	version          : "20000000",
	prevBlockhash    : "00000000000000000061abcd4f51d81ddba5498cff67fed44b287de0990b7266",
	merkleRoot       : "871148c57dad60c0cde483233b099daa3e6492a91c13b337a5413a4c4f842978",
	timestamp        : "5A50EB51",
	// bits were added for general bitcoin hashing algorithm
	bits             : "180091C1",
	difficultyTarget : 17,
	//    maximum      0xFFFFFFFF
	//    current      0x 2B319D6
	Nonce            : 45291990
};

const getTask = () => {
	console.log({ jobId, clientId, blockHeader });
	return { jobId, clientId, blockHeader };
};
// pepare new task
const allocateNewTask = () => {
	jobId++;
	clientId++;
	// Distribute task in terms of nonce
	blockHeader.Nonce += 0x10000000;
	blockHeader.Nonce %= 0x100000000;
};

// Declare an API route - "work"
app.get("/work", async (req, res) => {
	var task = getTask();
	allocateNewTask();
	// When a new client coming the Nonce addimng 10000000
	res.send(task);
});

// API route - "submit"
app.post("/submit", async (req, res) => {
	// some chunk the miner could do
	// var task = getTask();
	// allocateNewTask();
	return res.status(201).send("Accepted\n");
});

let handlers = new HandlerGenerator();

app.use(
	bodyParser.urlencoded({
		// Middleware
		extended : true
	})
);

app.use(bodyParser.json());

app.post("/login", async (req, res) => {
	return handlers.login(req, res);
});

app.get("/", async (request, reply) => {
	console.log(request);
	console.log(reply);
	middleware.checkToken(request, reply);
	return handlers.index;
});

// Run the server
const start = async () => {
	try {
		await app.listen(3000, "0.0.0.0");
	} catch (err) {
		// app.log.error(err);
		process.exit(1);
	}
};

start();

function range (start, end) {
	if (start === end) return [ start ];
	return [ start, ...range(start + 1, end) ];
}
