
package org.me.blastymina;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.me.blastymina.commands.MinaCommand;
import org.me.blastymina.commands.SetLocationMina;
import org.me.blastymina.events.onBlockBreak;
import org.me.blastymina.events.onPlayerInteract;
import org.me.blastymina.utils.LocationConfig;


public final class BlastyMina
extends JavaPlugin {
    private static ProtocolManager manager;
    private BlastyMina instance;

    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        manager = ProtocolLibrary.getProtocolManager();
        this.registerEvents();
        try {
            new LocationConfig(instance);
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina ]" + ChatColor.GREEN + "Configurac\u0327o\u0303es carregadas.");
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina ]" + ChatColor.RED + "Erro ao carregar as configurac\u0327o\u0303es.");
        }
        this.getCommand("mina").setExecutor((CommandExecutor)new MinaCommand());
        this.getCommand("minaadm").setExecutor((CommandExecutor)new SetLocationMina());
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina ]" + ChatColor.GREEN + "Plugin carregado.");
    }

    public static ProtocolManager getManager() {
        return manager;
    }

    private void registerEvents() {
        try {
            this.getServer().getPluginManager().registerEvents((Listener)new onPlayerInteract(), (Plugin)this.instance);
            this.getServer().getPluginManager().registerEvents((Listener)new onPlayerInteract(), (Plugin)this.instance);
            this.getServer().getPluginManager().registerEvents((Listener)new onBlockBreak(), (Plugin)this.instance);
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina ]" + ChatColor.GREEN + "Eventos carregados.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

