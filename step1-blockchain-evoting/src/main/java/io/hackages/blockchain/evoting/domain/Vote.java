package io.hackages.blockchain.evoting.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Represents a vote event in the Block.
 *
 * @author adasilveir
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vote {

    private String voterId;

    @NotNull
    private Long voterChoice;

    private String recipientNodeId;
}