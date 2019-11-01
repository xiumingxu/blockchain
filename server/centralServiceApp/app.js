//API
var app = require("express")();
const routes = require("./routes");
// Require the framework and instantiate it
const fastify = require("fastify")({ logger: true });

let header = {
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
	jobId       : 2,
	clientId    : 5,
	blockHeader : {
		version          : "20000000",
		prevBlockhash    : "00000000000000000061abcd4f51d81ddba5498cff67fed44b287de0990b7266",
		merkleRoot       : "871148c57dad60c0cde483233b099daa3e6492a91c13b337a5413a4c4f842978",
		timestamp        : "5A50EB51",
		bits             : "180091c1",
		difficultyTarget : 17,
		Nonce            : 45291990
	}
};

// Chun the header for big data later
let headerQueue = [ header ];

// Declare an API route - "work"
fastify.get("/work", async (request, reply) => {
	console.log("coming");
	return header;
});

// API route - "submit"
fastify.post("/submit", async (req, res) => {
	console.log("something");
	console.log(req.body);

	// some chunk the miner could do
	var newBlock = header;
	return res.status(201).end(newBlock);
});

// Run the server
const start = async () => {
	try {
		await fastify.listen(3000);
		fastify.log.info(`server listening on ${fastify.server.address().port}`);
	} catch (err) {
		fastify.log.error(err);
		process.exit(1);
	}
};

start();

module.exports = app;
