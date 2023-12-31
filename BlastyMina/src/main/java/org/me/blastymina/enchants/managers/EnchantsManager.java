package org.me.blastymina.enchants.managers;

import org.me.blastymina.enchants.FortuneEnchant;
import org.me.blastymina.utils.players.PlayerManager;

public class EnchantsManager {
    private PlayerManager manager;
    public EnchantsManager(PlayerManager manager) {
        this.manager = manager;
    }

    public double returnCoins(){
        return new FortuneEnchant(manager).setup();
    }
}
