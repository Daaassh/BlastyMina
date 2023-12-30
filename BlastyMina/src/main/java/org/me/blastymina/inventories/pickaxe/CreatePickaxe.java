package org.me.blastymina.inventories.pickaxe;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.utils.players.PlayerManager;

import java.util.List;

public class CreatePickaxe {
    private PlayerManager manager;
    private String enchant;
    private FileConfiguration config = BlastyMina.getPlugin(BlastyMina.class).getConfig();
    public CreatePickaxe(PlayerManager manager){
        this.manager = manager;
    }
    public ItemStack setup(){
        ItemStack item = new ItemStack(verifySkin());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("mina.pickaxe.name")));
        meta.setLore(returnLore());
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        return item;
    }

    private Material verifySkin(){
        switch (manager.getSkin()) {
            case 1:
                return Material.WOOD_PICKAXE;
            case 2:
                return Material.IRON_PICKAXE;
            case 3:
                return Material.GOLD_PICKAXE;
            case 4:
                return Material.DIAMOND_PICKAXE;
        }
        return null;
    }
    public List<String> returnLore(){
        List<String> lore = translateColors(config.getStringList("mina.pickaxe.lore"));
        replaceEnchants(lore);
        return lore;
    }

    private List<String> translateColors(List<String> input) {
        for (int i = 0; i < input.size(); i++) {
            input.set(i, ChatColor.translateAlternateColorCodes('&', input.get(i)));
        }
        return input;
    }
    private List<String> replaceEnchants(List<String> input){
        for (int i = 0; i < input.size(); i++) {
            String elemento = input.get(i);
            elemento = elemento.replace("{" + enchant + "}", String.valueOf(verifyEnchant(enchant)));
            elemento = elemento.replace("{max_level}", getMaxLevel());
            input.set(i, elemento);
        }
        return input;
    }
    private int verifyEnchant(String enchant){
        switch (enchant) {
            case "fortuna":
                return manager.getFortuna();
            case "britadeira":
                return manager.getBritadeira();
            case "raio":
                return manager.getRaio();
            case "bonus":
                return manager.getBonus();
            case "laser":
                return manager.getLaser();
            case "explosao":
                return manager.getExplosao();
            case "multiplicador":
                return manager.getMultiplicador();
            case "velocidade":
                return manager.getVelocidade();
            case "xpbooster":
                return manager.getXpbooster();
        }
        return 0;
    }
    private String getMaxLevel(){
        return String.valueOf(config.getInt("mina.enchants." + enchant + ".max-level"));
    }

}
