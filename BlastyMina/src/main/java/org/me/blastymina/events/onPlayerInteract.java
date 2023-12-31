
package org.me.blastymina.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.me.blastymina.database.MySqlUtils;
import org.me.blastymina.inventories.pickaxe.PickaxeInventory;
import org.me.blastymina.utils.players.PlayerManager;

import java.sql.SQLException;

public class onPlayerInteract
implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) throws SQLException {
        Player p = e.getPlayer();
        if (p.getItemInHand().getType() == Material.WOOD_PICKAXE || p.getItemInHand().getType() == Material.IRON_PICKAXE || p.getItemInHand().getType() == Material.GOLD_PICKAXE || p.getItemInHand().getType() == Material.IRON_PICKAXE || p.getItemInHand().getType() == Material.DIAMOND_PICKAXE ) {
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
    }
}

