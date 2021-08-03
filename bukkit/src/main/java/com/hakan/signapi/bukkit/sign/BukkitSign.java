package com.hakan.signapi.bukkit.sign;

import com.hakan.signapi.api.HSign;
import com.hakan.signapi.api.HSignAPI;
import com.hakan.signapi.api.HSignManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class BukkitSign implements HSign {

    private final HSignManager hSignManager;

    private String[] lines;
    private Material signType;

    public BukkitSign(HSignAPI hSignAPI, String[] lines, Material signType) {
        this.hSignManager = hSignAPI.getSignManager();
        this.lines = lines;
        this.signType = signType;
    }

    @Override
    public String[] getLines() {
        return this.lines;
    }

    @Override
    public void setLines(String[] lines) {
        this.lines = lines;
    }

    @Override
    public Material getSignType() {
        return this.signType;
    }

    @Override
    public void setSignType(Material signType) {
        this.signType = signType;
    }

    @Override
    public void open(Player player, Consumer<String[]> signLinesConsumer) {
        this.hSignManager.open(player, this, signLinesConsumer);
    }
}