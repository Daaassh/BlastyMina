package org.me.blastymina.inventories.skins;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.configs.CustomFileConfiguration;
import org.me.blastymina.utils.players.PlayerManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SkinInventory {
    private PlayerManager manager;
    private static final CustomFileConfiguration config;

    static {
        try {
            config = new CustomFileConfiguration("skins.yml", BlastyMina.getPlugin(BlastyMina.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public SkinInventory(PlayerManager manager){
        this.manager = manager;
        setup();
    }

    private void setup(){
        createInventory();
    }
    private void createInventory(){
        Inventory inventory = Bukkit.createInventory(null, 9*6, "§cSkins");
        List<ItemStack> skinItems = returnSkins();
        int slot = 11;
        for (ItemStack item : skinItems) {
            if (slot == 17 || slot == 26) {
                slot += 3;
            }
            inventory.setItem(slot, item);
            slot++;
        }
        inventory.setItem(49, returnHead(manager.getPlayer()));
        manager.getPlayer().openInventory(inventory);
    }
    private List<ItemStack> returnSkins(){
        List<ItemStack> skinItems = new ArrayList<>();
        for (String skins : config.getConfigurationSection("skins").getKeys(false)) {
            if (skins == null) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina - Skins ] " + ChatColor.RED + "Erro ao retornar as skins");
                Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina - Skins ] " + ChatColor.RED + "Nenhuma skin encontrada em: mina.skins");
            }
            else {
                ItemStack item = SkinManagers.createItem(skins);
                if (item != null) {
                    skinItems.add(item);
                }
            }
        }
        return skinItems;
    }
    private Integer returnNumberOfSkins(){
        return config.getConfigurationSection("skins").getKeys(false).size();
    }
    private ItemStack returnHead(Player player) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setDisplayName("Suas Skins: ");
        List<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add("Você esta na skin: " + manager.getSkin());
        lore.add("Seu nivel: " + manager.getNivel());
        lore.add("Seus Blocos: " + manager.getBlocks());
        lore.add(" ");
        meta.setOwner(player.getName());
        meta.setLore(lore);
        head.setItemMeta(meta);
        return head;
    }
}
