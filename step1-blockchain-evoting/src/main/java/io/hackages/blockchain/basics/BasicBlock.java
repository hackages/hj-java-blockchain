package io.hackages.blockchain.basics;

import lombok.Data;

import java.util.Date;

import static io.hackages.blockchain.basics.BasicChainUtils.applySha256;

@Data
public class BasicBlock {

    public String hash;

    public String previousHash;

    private String data;

    private long timestamp;

    public BasicBlock(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timestamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        String variableToHash = previousHash + timestamp + data;
        return applySha256(variableToHash);
    }
}