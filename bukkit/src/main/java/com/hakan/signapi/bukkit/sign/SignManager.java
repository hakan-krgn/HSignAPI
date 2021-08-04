package com.hakan.signapi.bukkit.sign;

import com.hakan.signapi.api.HSign;
import com.hakan.signapi.api.HSignAPI;
import com.hakan.signapi.api.HSignManager;
import com.hakan.signapi.api.HSignWrapper;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;

public class SignManager implements HSignManager {

    private final HSignAPI hSignAPI;
    private final HSignWrapper hSignWrapper;

    public SignManager(HSignAPI hSignAPI) {
        this.hSignAPI = hSignAPI;
        this.hSignWrapper = this.setupSignWrapper();
    }

    @Override
    public void open(Player player, HSign hSign, Consumer<String[]> signLinesConsumer) {
        this.hSignWrapper.open(player, hSign, signLinesConsumer);
    }

    @Override
    public HSignWrapper getSignWrapper() {
        return this.hSignWrapper;
    }

    private HSignWrapper setupSignWrapper() {
        String serverVersion = this.hSignAPI.getPlugin().getServer().getClass().getName().split("\\.")[3];
        try {
            return (HSignWrapper) Class.forName("com.hakan.signapi.nms.HSignWrapper_" + serverVersion).getConstructor(HSignAPI.class).newInstance(this.hSignAPI);
        } catch (InvocationTargetException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}