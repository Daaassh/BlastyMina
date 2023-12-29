/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.Player
 */
package org.me.blastymina.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.me.blastymina.BlastyMina;
import org.me.blastymina.utils.LocationConfig;

public class SetLocationMina
implements CommandExecutor {
    private LocationConfig createConfig = new LocationConfig(BlastyMina.getPlugin(BlastyMina.class));

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("\u00a7a[ Blasty Mina ]\u00a7cComando apenas para players.");
            return false;
        }
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
                    createConfig.saveConfig();
                    createConfig.reload();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                p.sendMessage(ChatColor.RED + "Não é possivel setar uma localização sem ser no mundo da mina");
            }
        } else if (args[0].equalsIgnoreCase("spawn") && p.getWorld().getName().equalsIgnoreCase("mina")) {
            setSpawn(p);
            p.sendMessage("\u00a7a[ Blasty Mina ]\u00a7aLocal do spawn definido.");
            try {
                createConfig.saveConfig();
                createConfig.reload();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (args[0].equalsIgnoreCase("reload")) {
            BlastyMina.getPlugin(BlastyMina.class).reloadConfig();
            p.sendMessage(ChatColor.GREEN + "Config recarregada");
        }
        return false;
    }
    private void setMina(Player p){
        createConfig.set("mina.location.x", p.getLocation().getX());
        createConfig.set("mina.location.y", p.getLocation().getY());
        createConfig.set("mina.location.z", p.getLocation().getZ());
        createConfig.setWorld("mina.location.world", p.getLocation().getWorld().getName());
    }
    private void setSpawn(Player p) {
        createConfig.set("spawn.location.x", p.getLocation().getX());
        createConfig.set("spawn.location.y", p.getLocation().getY());
        createConfig.set("spawn.location.z", p.getLocation().getZ());
        createConfig.setWorld("spawn.location.world",p.getLocation().getWorld().getName());
    }
}

