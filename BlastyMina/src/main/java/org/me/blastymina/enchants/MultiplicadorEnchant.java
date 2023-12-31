package org.me.blastymina.enchants;

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
            manager.setBlocks(blocks * manager.getMultiplicador());
        }
    }
}
