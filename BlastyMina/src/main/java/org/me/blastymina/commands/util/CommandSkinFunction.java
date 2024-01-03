package org.me.blastymina.commands.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.graalvm.compiler.phases.common.UseTrappingNullChecksPhase_OptionDescriptors;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.configs.CustomFileConfiguration;
import org.me.blastymina.database.MySqlUtils;
import org.me.blastymina.utils.players.PlayerManager;

public class CommandSkinFunction {
    private final FileConfiguration config = BlastyMina.getPlugin(BlastyMina.class).getConfig();
    private PlayerManager manager;

    public CommandSkinFunction(PlayerManager manager) {
        this.manager = manager;
        setup();
    }
    public void setup(){
        String s = "mina.skins.item";
        ConfigurationSection section = config.getConfigurationSection(s);
        if (section != null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[§c§lBlastyMina§r§7] §c§lItem para adicionar skins não encontrados!");
        }
        else {
            ItemStack item = new ItemStack(Material.valueOf(config.getString("mina.skins.item.type")));
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(config.getString("mina.skins.item.name"));
            meta.setLore(config.getStringList("mina.skins.item.lore"));
            item.setItemMeta(meta);
            Player p = manager.getPlayer();
            p.getInventory().addItem(item);
        }
    }
}
