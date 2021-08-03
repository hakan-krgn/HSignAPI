package com.hakan.signapi.api;

import org.bukkit.entity.Player;

import java.util.function.Consumer;

public interface HSignManager {

    void open(Player player, HSign hSign, Consumer<String[]> signLinesConsumer);

    HSignWrapper getSignWrapper();

}