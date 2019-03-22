package io.hackages.blockchain.basics;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static io.hackages.blockchain.basics.BasicChainUtils.isChainValid;

/**
 * These tests are already OK - No need to fix them
 */
@Slf4j
public class TestBasicChain {

    @Test
    public void shouldCreateBasicBlockWithUniqueHash() {

        BasicBlock firstBlock = new BasicBlock("first basic block ", "0000");
        Assert.assertNotNull(firstBlock.getHash());
        log.info("Hash for block1 " + firstBlock.getHash());

        BasicBlock secondBlock = new BasicBlock("second basic block ", firstBlock.hash);
        Assert.assertNotNull(secondBlock.getHash());
        Assert.assertFalse(secondBlock.getHash().matches(firstBlock.getHash()));
        Assert.assertEquals(firstBlock.getHash() , secondBlock.getPreviousHash());
        log.info("Hash for block2 " + secondBlock.getHash());

    }

    @Test
    public void shouldVerifyChainValidity() {

        ArrayList<BasicBlock> blockchain = new ArrayList<>();

        BasicBlock firstBlock = new BasicBlock("first basic block ", "0000");
        blockchain.add(firstBlock);

        BasicBlock secondBlock = new BasicBlock("second basic block ", firstBlock.hash);
        blockchain.add(secondBlock);

        Assert.assertTrue(isChainValid(blockchain));

        BasicBlock thirdBlock = new BasicBlock("third basic block ", firstBlock.hash);
        blockchain.add(thirdBlock);
        Assert.assertFalse(isChainValid(blockchain));
    }
}
