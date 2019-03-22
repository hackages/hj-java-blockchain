# HackJam Java and Blockchain 

## Goal
In this session we hope you will learn how build and/or interact with Blockchain technologies using Java. 
It's a trendy subject so there are thousands of tutorials online, one can easily get lost in that sea of information (we certainly did at first) BUT there's no better way to learn than hacking it yourself.

* We will start by fixing a [broken] custom implementation of a Blockchain System for EVoting use case.
* Then we will interact with one of the most popular implementations of Blockchain which is Ethereum using a Java Library called Web3j.
* Finally, let's talk about Blockchain-as-a-Service.   

## Requirements
- [x] Be comfortable using `java` 1.8 or higher
- [x] Spring Boot
- [x] `maven` 
- [x] `docker`

## Getting started:

- [ ] Clone this [repository](https://github.com/hackages/hj-java-blockchain.git)
`git clone https://github.com/hackages/hj-java-blockchain.git`
- [ ] `git checkout starting-point`

## Check dependencies 
* Mac or Linus command `./mvnw clean install -DskipTests`
* Windows command `mvnw.cmd clean install -DskipTests`

## Slides
- [x] Follow this [presentation](https://slides.com/adjetetatadasilveira/hackjam-java-blockchain-2019-06-13#/0/1) given by the Hackages mentor 

### Things to know about this Hackjam code sources

- You use Test Driven Development to fix the issues   
- You don't need to fix maven's configuration 

### Let's Start

[eVoting use case](./step1-blockchain-evoting/readme.md)