package org.me.blastymina.enchants.economy;

import org.me.blastymina.BlastyMina;
import org.me.blastymina.utils.players.PlayerManager;

public class XpBoosterEnchant {

    private PlayerManager manager;

    public XpBoosterEnchant(PlayerManager manager) {
        this.manager = manager;
        setup();
    }
    public void setup(){
        Integer lvl = manager.getXpbooster();
        manager.setXp(lvl * BlastyMina.getPlugin(BlastyMina.class).getConfig().getDouble("mina.blocks.xp-por-block"));
    }
}
