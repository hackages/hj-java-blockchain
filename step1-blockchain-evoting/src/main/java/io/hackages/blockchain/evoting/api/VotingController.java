package io.hackages.blockchain.evoting.api;

import io.hackages.blockchain.evoting.domain.VotingBlock;
import io.hackages.blockchain.evoting.domain.Vote;
import io.hackages.blockchain.evoting.dto.ChainResponse;
import io.hackages.blockchain.evoting.helper.BlockProofOfWorkGenerator;
import io.hackages.blockchain.evoting.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/voting")
public class VotingController {

    @Autowired
    private VotingService votingService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/castVote")
    public Map<String, String> castVote(@RequestBody Vote vote) {
        long index = votingService.registerNewVote(vote);

        return Collections.singletonMap("message", String.format("Vote will be added to Block {%d}", index));
    }

    @RequestMapping("/mineBlock")
    public Map<String, Object> mine() {

        // Update lastBlock
        votingService.generateLastBlockHash();

        VotingBlock lastVotingBlock = votingService.getLastBlock();
        long lastProof = lastVotingBlock.getProof();

        // Generate PoW for new Block
        long proof = BlockProofOfWorkGenerator.proofOfWork(lastProof);

        // Create new Block with its PoW and last Block hash
        VotingBlock votingBlock = votingService.createBlock(proof, null);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "New Block Forged");
        response.put("index", votingBlock.getIndex());
        response.put("proof", votingBlock.getProof());
        response.put("previous_hash", votingBlock.getPreviousHash());

        return response;
    }

    @RequestMapping("/chain")
    public ChainResponse chain() {
        ChainResponse chainResponse = ChainResponse.builder()
                .chain(votingService.getVotingBlockchain())
                .build();

        chainResponse.setLength(chainResponse.getChain().size() -1);
        return chainResponse;
    }
}