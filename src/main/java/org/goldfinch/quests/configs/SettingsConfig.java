package org.goldfinch.quests.configs;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.goldfinch.quests.Quests;
import ru.artorium.configs.Config;

@Getter
@NoArgsConstructor
public class SettingsConfig extends Config {

    private final List<String> scoreboardLayout = List.of(
      "§6§lActive Quest",
        "§7",
        "§e§f%quest_name%",
        "§7"
    );

    public SettingsConfig(Quests plugin) {
        super("settings.yml", plugin.getDataFolder().getPath());
        this.reload();
    }

}
