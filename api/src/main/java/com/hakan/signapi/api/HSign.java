package com.hakan.signapi.api;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public interface HSign {

    String[] getLines();

    void setLines(String[] lines);

    Material getSignType();

    void setSignType(Material signType);

    void open(Player player, Consumer<String[]> signLinesConsumer);

}