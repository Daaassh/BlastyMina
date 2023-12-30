package org.me.blastymina.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.me.blastymina.database.MySqlUtils;

import java.sql.SQLException;

public class onPlayerJoin implements Listener {

    @EventHandler
    public void onJOIN(PlayerJoinEvent event) throws SQLException {
        Player p = event.getPlayer();
        MySqlUtils.existPlayer(p);
    }
}
