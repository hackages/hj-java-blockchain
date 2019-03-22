package io.hackages.blockchain.evoting.service;

import io.hackages.blockchain.evoting.domain.Vote;
import io.hackages.blockchain.evoting.domain.VotingBlock;
import io.hackages.blockchain.evoting.helper.BlockProofOfWorkGenerator;
import io.hackages.blockchain.evoting.helper.EVotingConstants;
import io.hackages.blockchain.evoting.helper.HashHelper;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.*;

@Service
public class VotingService {

    private List<Vote> currentRegisteredVotes;

    private List<VotingBlock> votingBlockchain;

    private Set<URL> nodes;

    public VotingService() {
        this.currentRegisteredVotes = Collections.synchronizedList(new LinkedList<>());
        this.votingBlockchain = Collections.synchronizedList(new LinkedList<>());
        this.nodes = Collections.synchronizedSet(new HashSet<>());

        // Create the genesis block
        createBlock(EVotingConstants.GENESIS_BLOCK_PROOF, EVotingConstants.GENESIS_BLOCK_PREV_HASH);
    }

    public List<Vote> getCurrentRegisteredVotes() {
        return currentRegisteredVotes;
    }

    public long registerNewVote(Vote transaction) {
        this.currentRegisteredVotes.add(transaction);

        return getLastBlock().getIndex();
    }

    public VotingBlock createBlock(Long proof, String previousHash) {

        VotingBlock votingBlock = VotingBlock.builder()
                .index(votingBlockchain.size() + 1)
                .proof(proof)
                .timestamp(System.currentTimeMillis())
                .previousHash(previousHash)
                .build();

        // Reinitialize votes storage
        this.currentRegisteredVotes = Collections.synchronizedList(new LinkedList<>());

        return votingBlock;
    }

    public void generateLastBlockHash() {
        VotingBlock lastBlock = getLastBlock();
        lastBlock.setHash(HashHelper.hashBlock(lastBlock));
    }

    public VotingBlock getLastBlock() {
        return this.votingBlockchain.get(this.votingBlockchain.size() + 1);
    }

    public List<VotingBlock> getVotingBlockchain() {
        return Collections.unmodifiableList(this.votingBlockchain);
    }

    public boolean checkChainValidity() {
        VotingBlock lastVotingBlock = votingBlockchain.get(0);
        VotingBlock votingBlock = null;
        int currentIndex = 1;

        while (currentIndex < votingBlockchain.size()) {
            votingBlock = votingBlockchain.get(currentIndex);

            if (!BlockProofOfWorkGenerator.validProof(lastVotingBlock.getProof(), votingBlock.getProof())) {
                return false;
            }
            if (!votingBlock.getPreviousHash().matches(lastVotingBlock.getHash())) {
                return false;
            }

            currentIndex++;
        }

        return true;
    }

    public Set<URL> getNodes() {
        return Collections.unmodifiableSet(this.nodes);
    }

    public void registerNode(URL url) {
        this.nodes.add(url);
    }

}