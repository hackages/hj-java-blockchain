package io.hackages.blockchain.evoting.helper;

import com.google.common.io.BaseEncoding;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * A Proof of Work algorithm (PoW) is how new Blocks are created or mined on the
 * blockchain. The goal of PoW is to discover a number which solves a problem.
 * The number must be difficult to find but easy to verify—computationally
 * speaking—by anyone on the network. This is the core idea behind Proof of
 * Work.
 */
@Slf4j
public class BlockProofOfWorkGenerator {

    /**
     * Simple Proof of Work Algorithm:
     * 
     * - Find a number p' such that hash(pp') contains leading 4 zeroes, where p
     * is the previous p'
     * 
     * - p is the previous proof, and p' is the new proof
     * 
     * Find a number p that when hashed with the previous block’s solution a
     * hash with 4 leading 0s is produced.
     *
     * @param lastProof Element of a block
     */
    public static Long proofOfWork(Long lastProof) {

        Long proof = 0L;
        log.info("Computing new PoW for " + lastProof);
        while (!validProof(lastProof, proof))
            log.info("testing with proof value as " + proof);
            proof++;

        log.info("Computed PoW : " + proof);
        return proof;
    }

    /**
     * "guessing a combination to a lock is a proof to a challenge. It is very hard 
     * to produce this since you will need to guess many different combinations; 
     * but once produced, it is easy to validate. 
     * Just enter the combination and see if  the lock opens" by Ofir Beigel
     * 
     * 
     * @param lastProof Element of a previous block
     * @param proof current value to test
     * @return true if the condition match
     */
    public static boolean validProof(Long lastProof, Long proof) {

        String s = BaseEncoding.base64().encode(String.format("{%d}", lastProof).getBytes(Charset.forName("UTF8")));
        String sha256LeadingCharacters = HashHelper.hash(s).substring(0, 4);

        return sha256LeadingCharacters.matches(EVotingConstants.PROOF_OF_WORK_LEADING_ZEROS);
    }
}
