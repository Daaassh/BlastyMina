
package org.me.blastymina.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.potion.PotionEffectType;
import org.me.blastymina.utils.PacketsManager;
import org.me.blastymina.utils.SendPlayerToSpawn;

public class onInventoryClick
implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory().getName().equalsIgnoreCase("§cMina")) {
            Player p = (Player)e.getWhoClicked();
            switch (e.getCurrentItem().getType()) {
                case DIAMOND_ORE: {
                    p.closeInventory();
                    p.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(5, 5));
                    new PacketsManager(p, p.getLocation(), 20);
                    new SendPlayerToSpawn(p);
                    break;
                }
                case DIAMOND_PICKAXE: {
                    p.sendMessage(ChatColor.RED + "Em breve...");
                    p.closeInventory();
                    break;
                }
            }
        }
    }
}

