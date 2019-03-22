package io.hackages.blockchain.evoting.helper;

import com.google.common.hash.Hashing;
import io.hackages.blockchain.evoting.domain.VotingBlock;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class HashHelper {

    public static String hashBlock(VotingBlock votingBlock) {
        return hash(votingBlock.toString());
    }

    public static String hash(String content) {
        String hash = Hashing
                .sha256()
                .hashString(content, StandardCharsets.UTF_8)
                .toString();

        return hash;
    }
}