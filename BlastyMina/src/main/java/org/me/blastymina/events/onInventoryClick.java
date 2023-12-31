
package org.me.blastymina.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.potion.PotionEffectType;
import org.me.blastymina.api.TitleAPI;
import org.me.blastymina.utils.PacketsManager;
import org.me.blastymina.utils.SendPlayerToSpawn;
import org.me.blastymina.utils.config.CuboidManager;

public class onInventoryClick
implements Listener {
    TitleAPI api = new TitleAPI();
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory().getName().equalsIgnoreCase("§cMina")) {
            Player p = (Player)e.getWhoClicked();
            switch (e.getCurrentItem().getType()) {
                case DIAMOND_ORE: {
                    p.closeInventory();
                    try {
                        p.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(5, 999));
                        new CuboidManager(p);
                        api.sendFullTitle(p, 5, 5, 10, "§c§lMina", "§c§lSua mina esta sendo criada, Aguarde alguns segundos.");
                        new SendPlayerToSpawn(p);
                    }
                    catch (Exception es) {
                        api.sendFullTitle(p, 5, 5, 10, "§c§lMina", "§c§lErro ao enviar você para a mina.!");
                        es.printStackTrace();
                    }
                    break;
                }
                case DIAMOND_PICKAXE: {
                    p.sendMessage(ChatColor.RED + "Em breve...");
                    p.closeInventory();
                    break;
                }
            }
        }
        else {
            return;
        }
    }
}

