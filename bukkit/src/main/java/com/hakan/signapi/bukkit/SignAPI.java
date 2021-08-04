package com.hakan.signapi.bukkit;

import com.hakan.signapi.api.HSignAPI;
import com.hakan.signapi.api.HSignCreator;
import com.hakan.signapi.api.HSignManager;
import com.hakan.signapi.bukkit.listener.SignListener;
import com.hakan.signapi.bukkit.sign.SignManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class SignAPI implements HSignAPI {

    private static SignAPI instance;

    private final Plugin plugin;
    private final SignManager signManager;

    private SignAPI(Plugin plugin) {
        this.plugin = plugin;
        this.signManager = new SignManager(this);

        if (SignAPI.instance == null) {
            SignAPI.instance = this;

            for (Player loopPlayer : Bukkit.getOnlinePlayers()) {
                this.signManager.getSignWrapper().startListener(loopPlayer);
            }

            PluginManager pm = Bukkit.getPluginManager();
            pm.registerEvents(new SignListener(this), plugin);
        }
    }

    public static SignAPI getInstance(Plugin plugin) {
        return SignAPI.instance == null ? new SignAPI(plugin) : SignAPI.instance;
    }

    @Override
    public Plugin getPlugin() {
        return this.plugin;
    }

    @Override
    public HSignManager getSignManager() {
        return this.signManager;
    }

    @Override
    public HSignCreator getSignCreator() {
        return new SignCreator(this);
    }
}