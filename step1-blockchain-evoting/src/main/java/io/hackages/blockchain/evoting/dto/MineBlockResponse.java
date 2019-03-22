package io.hackages.blockchain.evoting.dto;

import io.hackages.blockchain.evoting.domain.Vote;
import lombok.*;

import java.util.List;

/**
 * Holds the mined block details.
 * 
 * @author Tata DA SILVEIRA
 *
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MineBlockResponse {
	private String message;
	private Long index;
	private List<Vote> votes;
	private Long proof;
	private String hash;
	private String previousHash;
}
