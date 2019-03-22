package io.hackages.blockchain.evoting;

import io.hackages.blockchain.evoting.domain.Vote;
import io.hackages.blockchain.evoting.dto.ChainResponse;
import io.hackages.blockchain.evoting.dto.MineBlockResponse;
import io.hackages.blockchain.evoting.helper.EVotingConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EVotingIntegrationTests {

    @LocalServerPort
    private int port;

    private RestTemplate client;

    private String baseUrl;

    @Before
    public void init() {

        OkHttp3ClientHttpRequestFactory rf = new OkHttp3ClientHttpRequestFactory();
        rf.setConnectTimeout(1000);
        rf.setReadTimeout(1000 * 1000);

        client = new RestTemplate(rf);
        baseUrl = "http://localhost:" + port;
    }

    @Test
    public void shouldFullyCompleteIntegrationTest() {
        Random random = new Random(System.currentTimeMillis());
        List<String> nodesOnTheNetwork = shouldCreateNodesOnTheNetwork();

        log.info("[Second step : Registration] On each node, a random number of voters will be created");
        Iterator<String> stringIterator = nodesOnTheNetwork.iterator();
        Map<String, Integer> quorumMap = new LinkedHashMap<>();
        random.ints(EVotingConstants.MAX_NETWORK_NODES, 50, 1000).forEach(x -> {
                    quorumMap.put(stringIterator.next(), x);
                }
        );
        assertThat(quorumMap.isEmpty()).isFalse();
        assertThat(quorumMap.size()).isEqualTo(EVotingConstants.MAX_NETWORK_NODES);

        log.info("[Third step Part 1] After Genesis block ");
        MineBlockResponse mineBlockResponse = client.getForObject(baseUrl + "/voting/mineBlock", MineBlockResponse.class);
        assertThat(mineBlockResponse).isNotNull();
        assertThat(mineBlockResponse.getMessage()).isNotEmpty();
        assertThat(mineBlockResponse.getIndex()).isEqualTo(1L);
        assertThat(mineBlockResponse.getProof()).isNotNull();
        assertThat(mineBlockResponse.getVotes()).isEmpty();
        assertThat(mineBlockResponse.getPreviousHash()).isNotEmpty();
        assertThat(mineBlockResponse.getHash()).isNull();

        log.info("[Third step Part 2] Voters cast their random vote");
        quorumMap.forEach((nodeId, voterCount) -> shouldExecuteVotersMakingChoices(random, nodeId, voterCount));

        log.info("[Fourth step] Check chain validity");
        ChainResponse chainResponse = client.getForObject(baseUrl + "/voting/chain", ChainResponse.class);
        assert chainResponse != null;
        assertThat(chainResponse.getChain()).isNotEmpty();
        assertThat(chainResponse.getLength()).isEqualTo(EVotingConstants.MAX_NETWORK_NODES +1);
        assertThat(chainResponse.isValid()).isTrue();
        log.info("chainResponse={}", chainResponse);

        log.info("[Fifth step] compute Voting results");
        Map voteResultsResponse = client.getForObject(baseUrl + "/voting/results", Map.class);
        log.info("voteResultsResponse={}, ", voteResultsResponse);
        assertThat(voteResultsResponse.get("total_voters")).isEqualTo(quorumMap.values().stream().mapToInt(i -> i).sum());
        assertThat(voteResultsResponse.get("results")).isNotSameAs("{choiceX: choiceCount, choiceY: choiceCount, choiceZ: choiceCount}");
    }

    private List<String> shouldCreateNodesOnTheNetwork() {
        log.info("[First step] Create Nodes on the network");

        List<String> nodeList = new LinkedList();
        int countNodes = 0;
        while (countNodes < EVotingConstants.MAX_NETWORK_NODES) {
            nodeList.add(RandomStringUtils.randomAlphanumeric(EVotingConstants.NODE_NAME_LENGTH));
            countNodes++;
        }
        Map createNodesResponse = client.postForObject(baseUrl + "/network/registerNodes", nodeList, Map.class);
        log.info("createNodesResponse={}", createNodesResponse);
        assertThat(createNodesResponse).isNotNull();
        assertThat(createNodesResponse.get("total_nodes")).isNotNull();
        assertThat(((List) createNodesResponse.get("total_nodes")).size()).isEqualTo(EVotingConstants.MAX_NETWORK_NODES);

        return nodeList;
    }

    private void shouldExecuteVotersMakingChoices(Random random, String nodeId, int numberOfVoters) {

        for (int i = 0; i < numberOfVoters; i++) {
            Vote vote = Vote.builder()
                    .voterId(UUID.randomUUID().toString())
                    .voterChoice((long) random.nextInt(EVotingConstants.MAX_VOTE_CHOICE))
                    .build();

            Map voteCastedResponse = client.postForObject(baseUrl + "/voting/castVote/" + nodeId, vote, Map.class);
            assertThat(voteCastedResponse).isNotNull();
            assertThat(voteCastedResponse.get("message")).isNotNull();
            assertThat(voteCastedResponse.get("nodeId")).isNotNull();
            log.info("voteCastedResponse={}", voteCastedResponse);
        }

        MineBlockResponse mineBlockResponse = client.getForObject(baseUrl + "/voting/mineBlock", MineBlockResponse.class);
        assertThat(mineBlockResponse).isNotNull();
        assertThat(mineBlockResponse.getMessage()).isNotEmpty();
        assertThat(mineBlockResponse.getIndex()).isNotNull();
        assertThat(mineBlockResponse.getProof()).isNotNull();
        assertThat(mineBlockResponse.getPreviousHash()).isNotEmpty();
        assertThat(mineBlockResponse.getHash()).isNull();

        log.info("mineBlockResponse={}, ", mineBlockResponse);
    }
}