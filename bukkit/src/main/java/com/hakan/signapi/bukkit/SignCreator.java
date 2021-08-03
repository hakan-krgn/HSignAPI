package com.hakan.signapi.bukkit;

import com.hakan.signapi.api.HSignAPI;
import com.hakan.signapi.bukkit.sign.BukkitSign;
import org.bukkit.Material;

import java.util.List;

public class SignCreator {

    private final HSignAPI hSignAPI;

    private String[] lines = new String[]{"", "", "", ""};
    private Material signType;

    SignCreator(HSignAPI hSignAPI) {
        this.hSignAPI = hSignAPI;
    }

    public SignCreator setLines(String... lines) {
        if (lines.length <= 4) {
            this.lines = lines;
            if (lines.length < 4) {
                for (int m = 0; m < 4 - lines.length; m++) {
                    lines[4 - m] = "";
                }
            }
        }
        return this;
    }

    public SignCreator setLines(List<String> lines) {
        return this.setLines(lines.toArray(new String[]{}));
    }

    public SignCreator setLine(int lineNum, String line) {
        if (lineNum < 4 && lineNum >= 0) {
            this.lines[lineNum] = line;
        }
        return this;
    }

    public SignCreator setType(Material type) {
        if (type.name().contains("SIGN")) {
            this.signType = type;
        }
        return this;
    }

    public BukkitSign create() {
        return new BukkitSign(this.hSignAPI, this.lines, this.signType);
    }
}