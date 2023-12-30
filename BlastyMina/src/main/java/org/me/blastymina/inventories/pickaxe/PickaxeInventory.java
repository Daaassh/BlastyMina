package org.me.blastymina.inventories.pickaxe;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.me.blastymina.enchants.EnchantsManagerConfig;
import org.me.blastymina.utils.players.PlayerManager;

public class PickaxeInventory {
    private PlayerManager manager;

    public PickaxeInventory(PlayerManager manager) {
        this.manager = manager;
        setup();
    }

    public void setup(){
        Inventory inv = Bukkit.createInventory(manager.getPlayer(), 9*5, ChatColor.GRAY + "Sua picareta");
        inv.setItem(11, new CreatePickaxe(manager).setup());
        inv.setItem(27, new ItemStack(Material.DARK_OAK_DOOR));
        inv.setItem(13, createItemEnchant("fortuna", Material.EMERALD_ORE));
        inv.setItem(14, createItemEnchant("multiplicador", Material.GOLD_ORE));
        inv.setItem(21, createItemEnchant("xpbooster", Material.EXP_BOTTLE));
        inv.setItem(22, createItemEnchant("bonus", Material.GOLD_BOOTS));
        inv.setItem(23, createItemEnchant("britadeira", Material.HOPPER));
        inv.setItem(30, createItemEnchant("explosao", Material.TNT));
        inv.setItem(31, createItemEnchant("raio", Material.BLAZE_ROD));
        inv.setItem(32, createItemEnchant("laser", Material.ENDER_PEARL));
        manager.getPlayer().openInventory(inv);
    }


    private ItemStack createItemEnchant(String enchant, Material material){
        EnchantsManagerConfig enchantManager = new EnchantsManagerConfig(manager, enchant);
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(enchantManager.returnName());
        meta.setLore(enchantManager.returnLore());
        item.setItemMeta(meta);
        return item;
    }
}
