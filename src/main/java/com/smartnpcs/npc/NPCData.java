package com.smartnpcs.npc;

import org.bukkit.Location;

import java.util.List;

public class NPCData {
    public String id;
    public String name;
    public Location location;
    public List<String> dialogue;
    public List<String> actions;
    public String permission;

    public NPCData(String id, String name, Location location, List<String> dialogue, List<String> actions, String permission) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.dialogue = dialogue;
        this.actions = actions;
        this.permission = permission;
    }
}