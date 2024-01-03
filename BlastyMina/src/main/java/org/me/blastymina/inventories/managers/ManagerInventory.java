package org.me.blastymina.inventories.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.api.TitleAPI;
import org.me.blastymina.configs.CustomFileConfiguration;
import org.me.blastymina.database.MySqlUtils;
import org.me.blastymina.inventories.pickaxe.CreatePickaxe;
import org.me.blastymina.inventories.pickaxe.PickaxeInventory;
import org.me.blastymina.inventories.skins.SkinInventory;
import org.me.blastymina.inventories.skins.SkinManagers;
import org.me.blastymina.utils.SendPlayerToSpawn;
import org.me.blastymina.utils.mina.CuboidManager;
import org.me.blastymina.utils.players.PlayerManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import static org.me.blastymina.inventories.skins.SkinManagers.setEnchant;

public class ManagerInventory {
    private final ItemStack item;
    private final Player player;
    private final PlayerManager manager;
    private HashMap<UUID, Player> players = new HashMap<>();
    private final CustomFileConfiguration config = new CustomFileConfiguration("config.yml", BlastyMina.getPlugin(BlastyMina.class));
    TitleAPI api = new TitleAPI();


    public ManagerInventory(PlayerManager manager,ItemStack item, Player player) throws IOException, InvalidConfigurationException {
        this.item = item;
        this.player = player;
        this.manager = manager;
    }


    public void registerPickaxeEventInventory() throws SQLException, IOException, InvalidConfigurationException {
        switch (item.getType()) {
            case EMERALD:
                onBuy("fortuna", "§6§lFortuna");
                break;
            case GOLD_ORE:
                onBuy("multiplicador", "§6§lMultiplicador");
                break;
            case EXP_BOTTLE:
                onBuy("xpbooster", "§6§lXP Booster");
                break;
            case TRIPWIRE_HOOK:
                onBuy("bonus", "§6§lBonus");
                break;
            case GOLD_BOOTS:
                onBuy("velocidade", "§6§lVelocidade");
                break;
            case HOPPER:
                onBuy("britadeira", "§6§lBritadeira");
                break;
            case GHAST_TEAR:
                onBuy("laser", "§6§lLaser");
                break;
            case TNT:
                onBuy("explosao", "§6§lExplosão");
                break;
            case BLAZE_ROD:
                onBuy("raio", "§6§lRaio");
                break;
        }
        MySqlUtils.updatePlayer(manager, player);
    }
    public void registerMinaEventInventory(){
        switch (item.getType()) {
            case DIAMOND_ORE: {
                player.closeInventory();
                try {
                    if (players.containsKey(player.getUniqueId())) {
                        unHide(player);
                        player.teleport(Bukkit.getWorld("world").getSpawnLocation());
                        player.sendMessage(ChatColor.AQUA + "[ Mina ] " + ChatColor.GREEN + "Voce foi teleportado para o spawn.");
                        players.remove(player.getUniqueId());
                    }
                    else {
                        player.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(20 * 10, 999));
                        new CuboidManager(player);
                        api.sendFullTitle(player, 5, 5, 15, "§c§lMina", "§c§lSua mina esta sendo criada, Aguarde alguns segundos.");
                        player.getInventory().setItem(4, new CreatePickaxe(MySqlUtils.getPlayer(player)).setup());
                        players.put(player.getUniqueId(), player);
                        hidePlayer(player);
                        new SendPlayerToSpawn(player);
                    }
                }
                catch (Exception es) {
                    api.sendFullTitle(player, 5, 5, 10, "§c§lMina", "§c§lErro ao enviar você para a mina.!");
                    es.printStackTrace();
                }
                break;
            }
            case DIAMOND_PICKAXE: {
                player.closeInventory();
                try {
                    new SkinInventory(MySqlUtils.getPlayer(player));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void hidePlayer(Player p) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            players.hidePlayer(p);
        }
        for (OfflinePlayer playertwo : Bukkit.getOfflinePlayers()) {
            playertwo.getPlayer().hidePlayer(p);
        }
    }
    private void unHide(Player p) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            players.showPlayer(p);
        }
        for (OfflinePlayer playertwo : Bukkit.getOfflinePlayers()) {
            playertwo.getPlayer().showPlayer(p);
        }
    }
    private int verifyEnchant(String enchant) throws SQLException {
        return SkinManagers.verifyEnchant(enchant, MySqlUtils.getPlayer(player));
    }
    private void setEnchant(String enchant, int level) throws SQLException {
        SkinManagers.setEnchant(enchant, level, MySqlUtils.getPlayer(player));
    }

    private int getInitialCost(String enchant){
        return config.getInt("enchants." + enchant + ".initial-cost");
    }
    private void onBuy(String enchant, String message) throws SQLException, IOException, InvalidConfigurationException {
        Integer cost;
        cost = verifyEnchant(enchant) * getInitialCost(enchant);

        if (manager.getBlocks() >= cost) {
            manager.setBlocks(manager.getBlocks() - cost);
            api.sendTitle(player, 5, 5, 10, "§6§l" + message, "§6§lEvoluida para o nivel: " + verifyEnchant(enchant));
            setEnchant(enchant,  verifyEnchant(enchant) + 1);
            player.closeInventory();
            new PickaxeInventory(MySqlUtils.getPlayer(player));
        }
        else {
            player.sendMessage(ChatColor.RED + "Voce precisa de " + cost + " blocos para evoluir para o nivel " + verifyEnchant(enchant) + 1);
        }

    }
}
