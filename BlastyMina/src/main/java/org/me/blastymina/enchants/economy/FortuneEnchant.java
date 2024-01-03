package org.me.blastymina.enchants.economy;

import org.bukkit.configuration.file.FileConfiguration;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.utils.players.PlayerManager;

public class FortuneEnchant {
    private PlayerManager manager;
    private FileConfiguration config = BlastyMina.getPlugin(BlastyMina.class).getConfig();
    public FortuneEnchant(PlayerManager manager) {
        this.manager = manager;
    }
    public Double setup(){
        return manager.getFortuna() * config.getDouble("mina.blocks.coin-por-block");
    }
}
