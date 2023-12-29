package org.me.blastymina.utils.rewards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.utils.porcentage.PorcentageManager;

import java.util.List;

public class CreateItems {
    private Player player;

    public CreateItems(Player player) {
        this.player = player;
        setup();
    }

    private void setup() {
        BlastyMina plugin = BlastyMina.getPlugin(BlastyMina.class);
        FileConfiguration config = plugin.getConfig();

        String sectionName = "mina.rewards.itens";

        ConfigurationSection section = config.getConfigurationSection(sectionName);
        if (section != null) {
            for (String itemName : section.getKeys(false)) {
                double chance = section.getDouble(itemName + ".chance");
                PorcentageManager manager = new PorcentageManager(chance);

                if (manager.setup()) {
                    createItemFromConfig(section, itemName);
                }
            }
        }
    }

    private void createItemFromConfig(ConfigurationSection section, String itemName) {
        Material material = Material.getMaterial(section.getInt(itemName + ".id"));

        if (material != null) {
            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(translateColors(section.getString(itemName + ".name")));
                List<String> lore = section.getStringList(itemName + ".lore");
                meta.setLore(translateColors(lore));
                item.setItemMeta(meta);
                if (section.getBoolean(itemName + ".commands.usage")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), section.getString(itemName + ".commands.command").replace("{player}", player.getDisplayName()));
                } else {
                    player.getInventory().addItem(item);
                }
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Material inválido para " + itemName);
        }
    }

    private String translateColors(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    private List<String> translateColors(List<String> input) {
        for (int i = 0; i < input.size(); i++) {
            input.set(i, ChatColor.translateAlternateColorCodes('&', input.get(i)));
        }
        return input;
    }
}

