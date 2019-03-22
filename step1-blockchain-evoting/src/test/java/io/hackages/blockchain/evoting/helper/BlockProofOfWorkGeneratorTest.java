package io.hackages.blockchain.evoting.helper;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The Proof of Work consensus algorithm involves solving a computational 
 * challenging puzzle in order to create new blocks in the blockchain (used in Bitcoin). 
 * 
 * The goal of this test is to emulate a custom simpler implementation of that algorithm
 */
public class BlockProofOfWorkGeneratorTest {

    /**
     * FIXME There is an issue with the current implementation
     */
    @Test
    public void shouldVerifyProofOfWorkAlgorithm() {

        // Computation should not take long
        long proofOfWork = BlockProofOfWorkGenerator.proofOfWork(5L);
        assertThat(proofOfWork).isNotNegative();
    }
}
