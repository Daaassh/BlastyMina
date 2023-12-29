package org.me.blastymina.utils.rewards;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.utils.porcentage.PorcentageEnchantsManager;
import org.me.blastymina.utils.porcentage.PorcentageManager;

public class EnchantsRewardsManager {
    private Integer blocks;
    private Player player;
    private String enchant;
    private FileConfiguration config = BlastyMina.getPlugin(BlastyMina.class).getConfig();

    public EnchantsRewardsManager(Player player, Integer blocks, String enchant) {
        this.player = player;
        this.blocks = blocks;
        this.enchant = enchant;
        this.sendRewards();
    }


    private void sendRewards() {
        if (!(player.isOnline())) {
            double multiply = config.getDouble("mina.rewards.enchants." + enchant + ".rewards.multiply");
            double chance = multiply * blocks;
            PorcentageEnchantsManager manager = new PorcentageEnchantsManager(chance);
            if (manager.setup()){
                new CreateItems(player);
            }
        }
    }
}
