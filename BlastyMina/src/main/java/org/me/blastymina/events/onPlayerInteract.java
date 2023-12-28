
package org.me.blastymina.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.me.blastymina.enchants.BritadeiraEnchant;
import org.me.blastymina.enchants.LaserEnchant;

public class onPlayerInteract
implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p;
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player p2 = e.getPlayer();
            if (p2.getItemInHand().getType() == Material.GOLD_PICKAXE) {
                new LaserEnchant(p2, e.getClickedBlock());
            }
        } else if (e.getAction() == Action.LEFT_CLICK_BLOCK && (p = e.getPlayer()).getItemInHand().getType() == Material.GOLD_PICKAXE) {
            new BritadeiraEnchant(p, e.getClickedBlock());
        }
    }
}

