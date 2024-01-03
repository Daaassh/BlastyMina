
package org.me.blastymina.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.commands.util.CommandSkinFunction;
import org.me.blastymina.configs.CustomFileConfiguration;
import org.me.blastymina.database.MySqlUtils;
import org.me.blastymina.utils.players.PlayerManager;

import java.io.IOException;
import java.sql.SQLException;

public class SetLocationMina
implements CommandExecutor {
    CustomFileConfiguration config = new CustomFileConfiguration("locations.yml",BlastyMina.getPlugin(BlastyMina.class));

    public SetLocationMina() throws IOException, InvalidConfigurationException {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        PlayerManager manager;
        Player p = (Player)sender;
        if (args[0].isEmpty()) {
            p.sendMessage("\u00a7a[ Blasty Mina ]\u00a7cArgumentos vazio.");
            return false;
        }
        if (args[0].equalsIgnoreCase("mina")) {
            if (p.getWorld().getName().equalsIgnoreCase("mina")) {
                setMina(p);
                p.sendMessage("\u00a7a[ Blasty Mina ]\u00a7aLocal da mina definida.");
                try {
                    BlastyMina.getPlugin(BlastyMina.class).saveConfig();
                    BlastyMina.getPlugin(BlastyMina.class).reloadConfig();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                p.sendMessage(ChatColor.RED + "Não é possivel setar uma localização sem ser no mundo da mina");
            }
        } else if (args[0].equalsIgnoreCase("spawn")) {
            if (p.getWorld().getName().equalsIgnoreCase("mina")) {
                setSpawn(p);
                p.sendMessage("\u00a7a[ Blasty Mina ]\u00a7aLocal do spawn definido.");
                try {
                    BlastyMina.getPlugin(BlastyMina.class).saveConfig();
                    BlastyMina.getPlugin(BlastyMina.class).reloadConfig();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } else if (args[0].equalsIgnoreCase("reload")) {
            BlastyMina.getPlugin(BlastyMina.class).reloadConfig();
            try {
                CustomFileConfiguration enchants = new CustomFileConfiguration("enchants.yml", BlastyMina.getPlugin(BlastyMina.class));
                CustomFileConfiguration rewards = new CustomFileConfiguration("rewards.yml", BlastyMina.getPlugin(BlastyMina.class));
                CustomFileConfiguration skins = new CustomFileConfiguration("skins.yml", BlastyMina.getPlugin(BlastyMina.class));
                enchants.reload();
                rewards.reload();
                skins.reload();
                p.sendMessage("Config recarregada!");
            } catch (IOException  | InvalidConfigurationException e) {
                p.sendMessage(ChatColor.RED + "Config não foi recarregada!");
                throw new RuntimeException(e);
            }
        }
        try {
            manager = MySqlUtils.getPlayer(p);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (args[0].equalsIgnoreCase("blocos")) {
            p.sendMessage(ChatColor.GREEN + "O seu total de blocos e: " + manager.getBlocks());
        }
        else if(args[0].equalsIgnoreCase("give")) {
            if (args[1].isEmpty()) {
                p.sendMessage(ChatColor.RED + "\u00a7cUso correto: /minaadm give <player>");
                return false;
            }
            else {
                PlayerManager managertwo = null;
                Player ps = p.getServer().getPlayer(args[1]);
                try {
                    managertwo = MySqlUtils.getPlayer(ps);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                new CommandSkinFunction(managertwo);
                Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina ] " + ChatColor.GOLD + "O jogador " + ps.getName() + " recebeu um item de skins!");
            }
        }
        else if(args[0].equalsIgnoreCase("set")){
            if (args[1].isEmpty() || args[2].isEmpty() || args[3].isEmpty()) {
                p.sendMessage(ChatColor.RED + "\u00a7cUso correto: /minaadm set <player> <blocos> <valor>");
                return false;
            }
            if(args[2].equalsIgnoreCase("blocos")){
                Player ps = p.getServer().getPlayer(args[1]);
                manager.setBlocks(manager.getBlocks() + Integer.parseInt(args[3]));
                MySqlUtils.updatePlayer(manager, ps);
                ps.sendMessage(ChatColor.GREEN + "Seu novo total de blocos e: " + manager.getBlocks());
            }
            else if (args[2].equalsIgnoreCase("nivel")) {
                Player ps = p.getServer().getPlayer(args[1]);
                manager.setNivel(manager.getNivel() + Integer.parseInt(args[3]));
                MySqlUtils.updatePlayer(manager, ps);
                ps.sendMessage(ChatColor.GREEN + "Seu novo nivel é de: " + manager.getBlocks());
            }
            else if (args[2].equalsIgnoreCase("skin")) {
                Player ps = p.getServer().getPlayer(args[1]);
                manager.setSkin(Integer.parseInt(args[3]));
                MySqlUtils.updatePlayer(manager, ps);
                ps.sendMessage(ChatColor.GREEN + "Seu nivel de skin agora e: " + manager.getSkin());
            }
        }
        return false;
    }
    private void setMina(Player p){
        config.set("mina.location.mina.x", p.getLocation().getX());
        config.set("mina.location.mina.y", p.getLocation().getY());
        config.set("mina.location.mina.z", p.getLocation().getZ());
        config.set("mina.location.mina.world", p.getLocation().getWorld().getName());
    }
    private void setSpawn(Player p) {
        config.set("spawn.x", p.getLocation().getX());
        config.set("spawn.y", p.getLocation().getY());
        config.set("spawn.z", p.getLocation().getZ());
        config.set("spawn.yaw", p.getLocation().getYaw());
        config.set("spawn.pitch", p.getLocation().getPitch());
        config.set("spawn.world", p.getLocation().getWorld().getName());
        config.save();
    }
}

