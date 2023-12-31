package org.me.blastymina.enchants;

import org.bukkit.configuration.file.FileConfiguration;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.utils.players.PlayerManager;

public class BonusEnchant {
    private PlayerManager manager;
    private FileConfiguration config = BlastyMina.getPlugin(BlastyMina.class).getConfig();
    public BonusEnchant(PlayerManager manager) {
        this.manager = manager;
    }
    public double setup(){
        Integer lvl = manager.getBonus();
        return lvl * config.getDouble("mina.rewards.chance");
    }
}
