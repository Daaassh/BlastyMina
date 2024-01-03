package org.me.blastymina.enchants.economy;

import org.me.blastymina.utils.players.PlayerManager;

public class MultiplicadorEnchant {
    private PlayerManager manager;
    private Integer blocks;

    public MultiplicadorEnchant(PlayerManager manager, Integer blocks) {
        this.blocks = blocks;
        this.manager = manager;
        setup();
    }

    public void setup(){
        if (manager.getMultiplicador() == 1) {
            manager.setBlocks(manager.getBlocks() + blocks);
        }
        else {
            int newBlocks = manager.getMultiplicador() * blocks;
            manager.setBlocks(newBlocks + manager.getBlocks());
        }
    }
}
