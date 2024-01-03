package org.me.blastymina.utils.rewards;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.configs.CustomFileConfiguration;
import org.me.blastymina.utils.porcentage.PorcentageEnchantsManager;

import java.io.IOException;


public class EnchantsRewardsManager {
    private final Integer blocks;
    private final Player player;
    private final String enchant;
    private final CustomFileConfiguration enchants = new CustomFileConfiguration("enchants.yml", BlastyMina.getPlugin(BlastyMina.class));

    public EnchantsRewardsManager(Player player, Integer blocks, String enchant) throws IOException, InvalidConfigurationException {
        this.player = player;
        this.blocks = blocks;
        this.enchant = enchant;
        this.sendRewards();
    }


    private void sendRewards() throws IOException, InvalidConfigurationException {
        if (player.isOnline()) {
            double multiply = enchants.getDouble("enchants." + enchant + ".rewards.multiply");
            double chance = (multiply * blocks) / 100;
            PorcentageEnchantsManager manager = new PorcentageEnchantsManager(chance);
            if (manager.setup()){
                new CreateItems(player);
            }
        }
    }
}
