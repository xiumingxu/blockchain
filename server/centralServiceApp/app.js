//API
var app = require("express")();
const routes = require("./routes");
// Require the framework and instantiate it
const fastify = require("fastify")({ logger: true });

const header =
	// jobId       : 2,
	// clientId    : 5,
	// blockHeader : {
	// 	version          : 2,
	// 	prevBlockhash    : "00000000000008a3a41b85b8b29ad444def299fee21793cd8b9e567eab02cd81",
	// 	merkleRoot       : "2b12fcf1b09288fcaff797d71e950e71ae42b91e8bdb2304758dfcffc2b620e3",
	// 	timestamp        : 1305998791,
	// 	difficultyTarget : 17,
	// 	Nonce            : 2504433986
	// }
	{
		jobId       : 2,
		clientId    : 5,
		blockHeader : {
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
		}
	};
// Initailize jobs and client id

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
fastify.get("/work", async (request, reply) => {
	var task = getTask();
	allocateNewTask();
	// When a new client coming the Nonce addimng 10000000
	return task;
});

// API route - "submit"
fastify.post("/submit", async (req, res) => {
	// some chunk the miner could do
	// var task = getTask();
	// allocateNewTask();
	return res.status(201).send("Accepted");
});

// Run the server
const start = async () => {
	try {
		await fastify.listen(3000, "0.0.0.0");
		fastify.log.info(`server listening on ${fastify.server.address().port}`);
	} catch (err) {
		fastify.log.error(err);
		process.exit(1);
	}
};

start();

function range (start, end) {
	if (start === end) return [ start ];
	return [ start, ...range(start + 1, end) ];
}

module.exports = app;
