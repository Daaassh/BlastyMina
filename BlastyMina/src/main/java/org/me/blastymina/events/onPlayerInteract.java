
package org.me.blastymina.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.database.MySqlUtils;
import org.me.blastymina.inventories.pickaxe.PickaxeInventory;
import org.me.blastymina.utils.players.PlayerManager;

import java.io.IOException;
import java.sql.SQLException;

public class onPlayerInteract
implements Listener {
    FileConfiguration config = BlastyMina.getPlugin(BlastyMina.class).getConfig();
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) throws SQLException, IOException, InvalidConfigurationException {
        Player p = e.getPlayer();
        if (p.getItemInHand().getType() == Material.WOOD_PICKAXE ||
                p.getItemInHand().getType() == Material.GOLD_PICKAXE ||
                p.getItemInHand().getType() == Material.IRON_PICKAXE ||
                p.getItemInHand().getType() == Material.DIAMOND_PICKAXE ) {

            if (p.isSneaking()) {
                if (e.getAction() == Action.RIGHT_CLICK_AIR) {
                    PlayerManager manager = MySqlUtils.getPlayer(p);
                    new PickaxeInventory(manager).setup();
                }
            }
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                e.setCancelled(true);
            }
            else if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                e.setCancelled(true);
            }
        }
        if (p.getItemInHand().getType() == Material.valueOf(config.getString("mina.skins.item.type"))) {
            ItemMeta meta = p.getItemInHand().getItemMeta();
            if (meta.getDisplayName().equalsIgnoreCase(config.getString("mina.skins.item.name"))) {
                PlayerManager manager = MySqlUtils.getPlayer(p);
                manager.setSkin(manager.getSkin() + 1);
                MySqlUtils.updatePlayer(manager, p);
                PlayerManager managers = MySqlUtils.getPlayer(p);
                p.sendMessage(ChatColor.AQUA + "[ Skins ] " + ChatColor.GRAY + "Sua skin foi alterado com sucesso, agora seu nivel é: " + managers.getSkin());
            }
        }
    }

}

