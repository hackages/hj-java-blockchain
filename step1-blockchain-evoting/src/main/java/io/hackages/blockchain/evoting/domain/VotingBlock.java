package io.hackages.blockchain.evoting.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * This class represents a Block in the Blockchain.
 * It is the basic building block.
 * 
 * @author Tata DA SILVEIRA
 */
@Data
@Builder
@JsonPropertyOrder(alphabetic = true)
@AllArgsConstructor
@NoArgsConstructor
public class VotingBlock implements Serializable {

	private long index;

	private long timestamp;

	private List<Vote> votes;

	private long proof;

	private String hash;

	// hash of the previous Block
	private String previousHash;
}