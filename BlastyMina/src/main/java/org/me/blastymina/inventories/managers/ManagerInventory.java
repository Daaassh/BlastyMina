package org.me.blastymina.inventories.managers;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.api.TitleAPI;
import org.me.blastymina.database.MySqlUtils;
import org.me.blastymina.inventories.pickaxe.CreatePickaxe;
import org.me.blastymina.inventories.pickaxe.PickaxeInventory;
import org.me.blastymina.utils.SendPlayerToSpawn;
import org.me.blastymina.utils.mina.CuboidManager;
import org.me.blastymina.utils.players.PlayerManager;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ManagerInventory {
    private ItemStack item;
    private Player player;
    private PlayerManager manager;
    private FileConfiguration config = BlastyMina.getPlugin(BlastyMina.class).getConfig();
    TitleAPI api = new TitleAPI();


    public ManagerInventory(PlayerManager manager,ItemStack item, Player player){
        this.item = item;
        this.player = player;
        this.manager = manager;
    }


    public void registerPickaxeEventInventory() throws SQLException {
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
                    if (ifItemInInventory(player)) {
                        player.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(5, 999));
                        new CuboidManager(player);
                        api.sendFullTitle(player, 5, 5, 10, "§c§lMina", "§c§lSua mina esta sendo criada, Aguarde alguns segundos.");
                        player.getInventory().setItem(5, new CreatePickaxe(MySqlUtils.getPlayer(player)).setup());
                        new SendPlayerToSpawn(player);
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Esvazie seu inventário antes de ir para a mina.");
                    }
                }
                catch (Exception es) {
                    api.sendFullTitle(player, 5, 5, 10, "§c§lMina", "§c§lErro ao enviar você para a mina.!");
                    es.printStackTrace();
                }
                break;
            }
            case DIAMOND_PICKAXE: {
                player.sendMessage(ChatColor.RED + "Em breve...");
                player.closeInventory();
                break;
            }
        }
    }

    private boolean ifItemInInventory(Player p) {
        for (int i = 0; i < p.getInventory().getSize(); i++) {
            if (p.getInventory().getItem(i) == null) {
                return true;
            }
        }
        return false;
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
    private void setEnchant(String enchant, Integer lvl){
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
    private int getInitialCost(String enchant){
        return config.getInt("mina.enchants." + enchant + ".initial-cost");
    }
    private void onBuy(String enchant, String message) throws SQLException {
        Integer cost = null;
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
