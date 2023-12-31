package org.me.blastymina.inventories.pickaxe;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.me.blastymina.enchants.managers.EnchantsManagerConfig;
import org.me.blastymina.utils.players.PlayerManager;

public class PickaxeInventory {
    private PlayerManager manager;

    public PickaxeInventory(PlayerManager manager) {
        this.manager = manager;
        setup();
    }

    public void setup(){
        Inventory inv = Bukkit.createInventory(manager.getPlayer(), 9*5, ChatColor.GRAY + "Sua picareta");
        inv.setItem(10, new CreatePickaxe(manager).setup());
        inv.setItem(28, new ItemStack(Material.WOOD_DOOR));
        inv.setItem(12, createItemEnchant("fortuna", Material.EMERALD));
        inv.setItem(13, createItemEnchant("multiplicador", Material.GOLD_ORE));
        inv.setItem(14, createItemEnchant("xpbooster", Material.EXP_BOTTLE));
        inv.setItem(21, createItemEnchant("bonus", Material.TRIPWIRE_HOOK));
        inv.setItem(22, createItemEnchant("velocidade", Material.GOLD_BOOTS));
        inv.setItem(23, createItemEnchant("britadeira", Material.HOPPER));
        inv.setItem(30, createItemEnchant("laser", Material.GHAST_TEAR));
        inv.setItem(31, createItemEnchant("explosao", Material.TNT));
        inv.setItem(32, createItemEnchant("raio", Material.BLAZE_ROD));
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
