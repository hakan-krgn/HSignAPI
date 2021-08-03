package com.hakan.signapi.api;

import org.bukkit.Material;

import java.util.List;

public interface HSignCreator {

    HSignCreator setLines(String... lines);

    HSignCreator setLines(List<String> lines);

    HSignCreator setLine(int lineNum, String line);

    HSignCreator setType(Material type);

    HSign create();

}