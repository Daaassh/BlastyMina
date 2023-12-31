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
        else {
            Player p = (Player) commandSender;
            new MinaInventory(p);
        }
        return false;
    }
}

