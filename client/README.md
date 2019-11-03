# README - Client (Bitcoin Miner)_

## Introduction

The app retrieve blockchain header data from server and start to mining work (Bitcoin Hashing Alogorithm) immediately after this.

After mining the work, the miner automatically post the data back to the server

## Structure
As the app mainly focused on the DTO flow and hashing algorithm, so the interface is rather easy.
- A logger to show the process
  - Blockchain Header retrieved from server each time
  - The time cost to finsih the task
  - The nonce found after hashing algorithm was applied
- An activation button to trigger the data retrieving and mining task
- All the processing task were not pushed to the UIthread in order to reduce the rendering work

## Additional Feature
- Sound effect : Each time nonce was found
- Logo design
