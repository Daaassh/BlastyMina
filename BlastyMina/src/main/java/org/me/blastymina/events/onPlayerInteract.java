
package org.me.blastymina.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.me.blastymina.enchants.BritadeiraEnchant;
import org.me.blastymina.enchants.LaserEnchant;
import org.me.blastymina.enchants.RaioEnchant;

public class onPlayerInteract
implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (p.getItemInHand().getType() == Material.GOLD_PICKAXE) {
            if (p.isSneaking()) {
                if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    new RaioEnchant(p, e.getClickedBlock());
                }
            }
            switch (e.getAction()){
                case RIGHT_CLICK_BLOCK:
                    new LaserEnchant(p);
                    break;
                case LEFT_CLICK_BLOCK:
                    new BritadeiraEnchant(p, e.getClickedBlock());
                    break;
            }
        }
    }
}

