# README - Client (Bitcoin Miner)_

## Introduction

The app retrieves BrezelCoin header data from server and starts mining work (Bitcoin Hashing Alogorithm) immediately after this.

After mining the workload, the miner automatically posts the data back to the server.

## Structure
Since the app is mainly focused on the data-transfer-object (DTO) flow and hashing algorithm, the interface is rather simple.
- A logger to show the process
  - Blockchain Header retrieved from server each time
  - The time cost to finsih the task in milliseconds
  - The nonce found after the hashing algorithm was applied
- An activation button to trigger the data request to the server required for starting the mining task
- None the processing tasks were pushed to the UIthread in order to reduce the rendering work

## Additional Features
- Sound effect ("ka-ching"): Notification for each time a nonce was found
- Logo design for BrezelCoin
