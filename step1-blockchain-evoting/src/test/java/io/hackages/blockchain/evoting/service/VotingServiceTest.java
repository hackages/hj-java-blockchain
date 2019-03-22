package io.hackages.blockchain.evoting.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.hackages.blockchain.evoting.domain.Vote;
import io.hackages.blockchain.evoting.domain.VotingBlock;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class VotingServiceTest {

    VotingService votingService = new VotingService();

    @Test
    public void test1_shouldCreateVoteAndReturnIndexOfFutureBlockWhereVoteWillBeStored() {
        Vote vote = new Vote(UUID.randomUUID().toString(), 3L, UUID.randomUUID().toString());
        assertThat(votingService.registerNewVote(vote)).isEqualTo(0);
        assertThat(votingService.getVotingBlockchain().size()).isEqualTo(1);
    }

    @Test
    public void test2_shouldCreateVotingBlockAndCheck() throws JsonProcessingException {

        VotingBlock createdVotingBlock = votingService.createBlock(100L, RandomStringUtils.randomAlphanumeric(50));

        assertThat(createdVotingBlock).isNotNull();
        assertThat(createdVotingBlock.getIndex()).isEqualTo(1);
        assertThat(createdVotingBlock.getProof()).isNotNegative();
        assertThat(createdVotingBlock.getPreviousHash()).isNotBlank();

        assertThat(votingService.getCurrentRegisteredVotes().isEmpty()).isTrue();
        assertThat(votingService.getVotingBlockchain().size()).isEqualTo(2);
        assertThat(votingService.getLastBlock()).isNotNull();
        assertThat(votingService.getLastBlock().getProof()).isNotNegative();
    }
}
