//API
var app = require("express")();
const routes = require("./routes");
// Require the framework and instantiate it
const fastify = require("fastify")({ logger: true });

let header = {
	jobId       : 2,
	clientId    : 5,
	blockHeader : {
		version          : 2,
		prevBlockhash    : "00000000000008a3a41b85b8b29ad444def299fee21793cd8b9e567eab02cd81",
		merkleRoot       : "2b12fcf1b09288fcaff797d71e950e71ae42b91e8bdb2304758dfcffc2b620e3",
		timestamp        : 1305998791,
		difficultyTarget : 17,
		Nonce            : 2504433986
	}
};

let headerQueue = [ header ];

// Declare an API route - "work"
fastify.get("/work", async (request, reply) => {
	return header;
});

// API route - "submit"
fastify.post("/submit", async (request, reply) => {
	return {
		hat   : 5,
		socks : 5,
		shirt : 25,
		pants : 40
	};
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
