
package org.me.blastymina.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MinaInventory {
    public Player player;

    public MinaInventory(Player player) {
        this.player = player;
        this.setup();
    }

    private void setup() {
        Inventory inventory = Bukkit.createInventory(null, 27, "§cMina");
        ItemStack reset = new ItemStack(Material.DIAMOND_ORE);
        ItemMeta meta = reset.getItemMeta();
        meta.setDisplayName("§cResetar mina");
        reset.setItemMeta(meta);
        ItemStack pickage = new ItemStack(Material.DIAMOND_PICKAXE);
        meta = pickage.getItemMeta();
        meta.setDisplayName("§cPickaxe");
        pickage.setItemMeta(meta);
        inventory.setItem(14, reset);
        inventory.setItem(16, pickage);
        player.openInventory(inventory);
    }
}

