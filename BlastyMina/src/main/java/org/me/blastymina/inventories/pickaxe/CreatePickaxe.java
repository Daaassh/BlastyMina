package org.me.blastymina.inventories.pickaxe;

import com.sun.tools.javac.code.Attribute;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.utils.players.PlayerManager;
import sun.jvm.hotspot.oops.Array;

import java.util.Arrays;
import java.util.List;

public class CreatePickaxe {
    private PlayerManager manager;
    private String enchant;
    private FileConfiguration config = BlastyMina.getPlugin(BlastyMina.class).getConfig();
    public CreatePickaxe(PlayerManager manager) {
        this.manager = manager;
    }
    public ItemStack setup(){
        ItemStack item = new ItemStack(verifySkin());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("mina.pickaxe.name") + " | " + manager.getBreakblocks()));
        meta.setLore(returnLore());
        try {
            meta.spigot().setUnbreakable(true);
        }
        catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA+ "[ Blasty Mina ] " + ChatColor.RED + "Erro ao setar unbreakable no item!");
            e.printStackTrace();
        }
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
    public List<String> returnLore() {
        List<String> lore = translateColors(config.getStringList("mina.pickaxe.lore"));
        replaceEnchants(lore);
        replaceMaxLevel(lore);
        replaceBlocksBreak(lore);
        return lore;
    }

    private void replaceEnchants(List<String> input) {
        for (int i = 0; i < input.size(); i++) {
            String elemento = input.get(i);
            for (String enchant : getEnchantments()) {
                elemento = elemento.replace("{" + enchant + "}", String.valueOf(verifyEnchant(enchant)));
            }
            input.set(i, elemento);
        }
    }
    private void replaceMaxLevel(List<String> input) {
        for (int i = 0; i < input.size(); i++) {
            String elemento = input.get(i);
            elemento = elemento.replace("@max_level", getMaxLevel());
            input.set(i, elemento);
        }
    }

    private List<String> translateColors(List<String> input) {
        for (int i = 0; i < input.size(); i++) {
            input.set(i, ChatColor.translateAlternateColorCodes('&', input.get(i)));
        }
        return input;
    }
    private void replaceBlocksBreak(List<String> input) {
        for (int i = 0; i < input.size(); i++) {
            String elemento = input.get(i);
            elemento = elemento.replace("@blocks_break", String.valueOf(manager.getBreakblocks()));
            input.set(i, elemento);
        }
    }

    private List<String> getEnchantments() {
        return Arrays.asList("fortuna", "britadeira", "raio", "bonus", "laser", "explosao", "multiplicador", "velocidade", "xpbooster");
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
