
package org.me.blastymina.events;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.potion.PotionEffectType;
import org.me.blastymina.api.TitleAPI;
import org.me.blastymina.database.MySqlUtils;
import org.me.blastymina.inventories.managers.ManagerInventory;
import org.me.blastymina.inventories.pickaxe.CreatePickaxe;
import org.me.blastymina.utils.SendPlayerToSpawn;
import org.me.blastymina.utils.mina.CuboidManager;

import java.io.IOException;
import java.sql.SQLException;

public class onInventoryClick
implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) throws SQLException, IOException, InvalidConfigurationException {
        Player p = (Player)e.getWhoClicked();
        ManagerInventory managers = new ManagerInventory(MySqlUtils.getPlayer(p), e.getCurrentItem(), p);
        if (e.getClickedInventory().getName().equalsIgnoreCase("§cMina")) {
            managers.registerMinaEventInventory();
            e.setCancelled(true);
        }
        else if(e.getClickedInventory().getName().equalsIgnoreCase(ChatColor.GRAY + "Sua picareta")) {
            managers.registerPickaxeEventInventory();
            e.setCancelled(true);
        }

    }

}

