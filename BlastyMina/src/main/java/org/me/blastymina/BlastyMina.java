
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
import org.me.blastymina.events.onInventoryClick;
import org.me.blastymina.events.onPlayerInteract;
import org.me.blastymina.utils.LocationConfig;


public final class BlastyMina
extends JavaPlugin {
    private static ProtocolManager manager;
    private BlastyMina instance;

    public void onEnable() {
        verifyDependencies();
        instance = this;
        saveDefaultConfig();
        manager = ProtocolLibrary.getProtocolManager();
        registerEvents();
        try {
            new LocationConfig(instance);
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina ]" + ChatColor.GREEN + "Configurac\u0327o\u0303es carregadas.");
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina ]" + ChatColor.RED + "Erro ao carregar as configurac\u0327o\u0303es.");
        }
        getCommand("mina").setExecutor(new MinaCommand());
        getCommand("minaadm").setExecutor(new SetLocationMina());
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina ]" + ChatColor.GREEN + "Plugin carregado.");
    }

    public static ProtocolManager getManager() {
        return manager;
    }

    private void registerEvents() {
        try {
            getServer().getPluginManager().registerEvents(new onInventoryClick(), instance);
            getServer().getPluginManager().registerEvents(new onPlayerInteract(), instance);
            getServer().getPluginManager().registerEvents(new onBlockBreak(), instance);
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ Blasty Mina ]" + ChatColor.GREEN + "Eventos carregados.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void verifyDependencies() {
        if (!Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[ Blasty Mina ]" + ChatColor.RED + "Plugin 'ProtocolLib' n\u00e3o encontrado.");
            getServer().getPluginManager().disablePlugin(this);
        }
        if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[ Blasty Mina ]" + ChatColor.RED + "Plugin 'PlaceholderAPI' n\u00e3o encontrado.");
            getServer().getPluginManager().disablePlugin(this);
        }
    }
}

