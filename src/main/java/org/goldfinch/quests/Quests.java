package org.goldfinch.quests;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.goldfinch.quests.data.Hibernate;

@Getter
public class Quests extends JavaPlugin {

    @Getter
    private static Quests instance;
    private Hibernate hibernate;

    @Override
    public void onEnable() {
        instance = this;
        this.hibernate = new Hibernate();

    }

    @Override
    public void onDisable() {
        this.hibernate.close();
    }

}