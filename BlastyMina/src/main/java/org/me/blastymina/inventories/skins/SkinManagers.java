package org.me.blastymina.inventories.skins;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.configs.CustomFileConfiguration;
import org.me.blastymina.utils.players.PlayerManager;

import java.io.IOException;
import java.util.List;

public class SkinManagers {
    private static final CustomFileConfiguration config;

    static {
        try {
            config = new CustomFileConfiguration("skins.yml", BlastyMina.getPlugin(BlastyMina.class));
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }


    public static int verifyEnchant(String enchant, PlayerManager manager){
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
    public static void setEnchant(String enchant, Integer lvl, PlayerManager manager){
        switch (enchant) {
            case "fortuna":
                manager.setFortuna(lvl);
                break;
            case "britadeira":
                manager.setBritadeira(lvl);
                break;
            case "raio":
                manager.setRaio(lvl);
                break;
            case "bonus":
                manager.setBonus(lvl);
                break;
            case "laser":
                manager.setLaser(lvl);
                break;
            case "explosao":
                manager.setExplosao(lvl);
                break;
            case "multiplicador":
                manager.setMultiplicador(lvl);
                break;
            case "velocidade":
                manager.setVelocidade(lvl);
                break;
            case "xpbooster":
                manager.setXpbooster(lvl);
                break;
        }
    }
    public static ItemStack createItem(String skins){
        String sectionName = "skins";
        ConfigurationSection section = config.getConfigurationSection(sectionName);
        try {
            String name = translateColor(section.getString(skins + ".name"));
            List<String> lore = translateColors(section.getStringList(skins + ".lore"));
            Material material = Material.valueOf(section.getString(skins + ".type"));
            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(name);
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }
        catch (Exception e){
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Item inválido para " + skins);
            e.printStackTrace();
        }
        return null;
    }
    private static List<String> translateColors(List<String> input) {
        for (int i = 0; i < input.size(); i++) {
            input.set(i, ChatColor.translateAlternateColorCodes('&', input.get(i)));
        }
        return input;
    }
    private static String translateColor(String input){
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}
