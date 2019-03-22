package io.hackages.blockchain.evoting.helper;

import org.apache.commons.lang3.RandomStringUtils;

public interface EVotingConstants {

    String PROOF_OF_WORK_LEADING_ZEROS = "0000";
    Long GENESIS_BLOCK_PROOF = Long.valueOf(RandomStringUtils.randomNumeric(2));
    String GENESIS_BLOCK_PREV_HASH = RandomStringUtils.randomAlphanumeric(50);
    int MAX_NETWORK_NODES = 20;
    int NODE_NAME_LENGTH = 10;
    int MAX_VOTE_CHOICE = 3;
}