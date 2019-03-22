package io.hackages.blockchain.evoting.dto;

import io.hackages.blockchain.evoting.domain.VotingBlock;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * Holds Chain details.
 * 
 * @author Tata DA SILVEIRA
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChainResponse implements Serializable {

	private boolean valid;
	private int length;
	private List<VotingBlock> chain;

}
