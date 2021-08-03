package com.hakan.signapi.bukkit.listener;

import com.hakan.signapi.api.HSignAPI;
import com.hakan.signapi.api.HSignManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

public class SignListener implements Listener {

    private final Plugin plugin;
    private final HSignManager hSignManager;

    public SignListener(HSignAPI hSignAPI) {
        this.plugin = hSignAPI.getPlugin();
        this.hSignManager = hSignAPI.getSignManager();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
            Player player = event.getPlayer();
            if (player.isOnline()) {
                this.hSignManager.getSignWrapper().startListener(player);
            }
        }, 2);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
            Player player = event.getPlayer();
            if (!player.isOnline()) {
                this.hSignManager.getSignWrapper().stopListener(player);
            }
        }, 2);
    }

    @EventHandler
    public void onDisable(PluginDisableEvent event) {
        if (event.getPlugin().equals(this.plugin)) {
            for (Player loopPlayer : Bukkit.getOnlinePlayers()) {
                this.hSignManager.getSignWrapper().stopListener(loopPlayer);
            }
        }
    }
}