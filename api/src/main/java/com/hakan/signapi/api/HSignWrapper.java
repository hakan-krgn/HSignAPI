package com.hakan.signapi.api;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class HSignWrapper {

    protected final Map<String, Consumer<String[]>> signCallback;
    protected HSignAPI hSignAPI;
    protected Plugin plugin;

    public HSignWrapper(HSignAPI hSignAPI) {
        this.hSignAPI = hSignAPI;
        this.plugin = this.hSignAPI.getPlugin();
        this.signCallback = new HashMap<>();
    }

    public abstract void open(Player player, HSign hSign, Consumer<String[]> signLinesConsumer);

    public abstract void startListener(Player player);

    public abstract void stopListener(Player player);
}