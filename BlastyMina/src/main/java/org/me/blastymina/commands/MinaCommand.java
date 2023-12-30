/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package org.me.blastymina.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.me.blastymina.database.MySqlUtils;
import org.me.blastymina.inventories.MinaInventory;
import org.me.blastymina.utils.players.PlayerManager;

import java.sql.SQLException;

public class MinaCommand
implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("\u00a7cApenas players podem executar este comando.");
            return false;
        }
        Player p = (Player) commandSender;
        PlayerManager manager;
        try {
            manager = MySqlUtils.getPlayer(p);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (args[0] == null) {
            Player player = (Player) commandSender;
            new MinaInventory(player);
        } else if (args[0].equalsIgnoreCase("blocos")) {
            p.sendMessage(ChatColor.GREEN + "O seu total de blocos e: " + manager.getBlocks());
        }
        else if(args[0].equalsIgnoreCase("set")){
            if (args[1].isEmpty() || args[2].isEmpty() || args[3].isEmpty()) {
                p.sendMessage(ChatColor.RED + "\u00a7cUso correto: /mina set <player> <blocos> <valor>/");
                return false;
            }
            if(args[2].equalsIgnoreCase("blocos")){
                PlayerManager two;
                try {
                    two = MySqlUtils.getPlayer(p.getServer().getPlayer(args[1]));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Player ps = p.getServer().getPlayer(args[1]);
                manager.setBlocks(Integer.parseInt(args[3]));
                MySqlUtils.updatePlayer(manager, ps);
                ps.sendMessage(ChatColor.GREEN + "Seu novo total de blocos e: " + manager.getBlocks());
            }
            else if (args[2].equalsIgnoreCase("nivel")) {
                PlayerManager two;
                try {
                    two = MySqlUtils.getPlayer(p.getServer().getPlayer(args[1]));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Player ps = p.getServer().getPlayer(args[1]);
                manager.setNivel(Integer.parseInt(args[3]));
                MySqlUtils.updatePlayer(manager, ps);
                ps.sendMessage(ChatColor.GREEN + "Seu novo total de blocos e: " + manager.getBlocks());
            }
        }
        return false;
    }
}

