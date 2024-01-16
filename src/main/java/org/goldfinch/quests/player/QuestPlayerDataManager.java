package org.goldfinch.quests.player;

import fr.mrmicky.fastboard.adventure.FastBoard;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.goldfinch.quests.Quests;
import org.goldfinch.quests.configs.SettingsConfig;
import org.goldfinch.quests.libs.storages.player.PlayerDataManager;
import org.goldfinch.quests.player.entity.QuestPlayerData;
import org.goldfinch.quests.quest.entity.ActiveQuest;
import ru.artorium.configs.Replacer;

@Getter
public class QuestPlayerDataManager extends PlayerDataManager<QuestPlayerData> {

    private final SettingsConfig settingsConfig;
    private final Map<UUID, FastBoard> scoreboards;

    public QuestPlayerDataManager(Quests plugin) {
        super(plugin, QuestPlayerData.class, plugin.getHibernate());
        this.scoreboards = new HashMap<>();
        this.settingsConfig = plugin.getSettingsConfig();

        this.onLoad(playerData -> {
            final FastBoard fastBoard = new FastBoard(playerData.getBukkitPlayer());
            this.scoreboards.put(playerData.getId(), fastBoard);
        });

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::updateScoreboards, 0L, 20L);
    }

    @Override
    protected QuestPlayerData getTemplate() {
        return new QuestPlayerData();
    }

    private void updateScoreboards() {
        this.scoreboards.forEach((uuid, fastBoard) -> fastBoard.updateLines(this.getScoreboardLines(uuid)));
    }

    private List<Component> getScoreboardLines(UUID uuid) {
        final QuestPlayerData playerData = this.get(uuid);
        final ActiveQuest lastActiveQuest = playerData.getActiveQuests().get(playerData.getActiveQuests().size() - 1);
        final List<Component> lines = new ArrayList<>();
        final Replacer replacer = Replacer.of(
            "%quest%", lastActiveQuest.toScoreboardComponent()
        );

        this.settingsConfig.getScoreboardLayout().forEach(line -> lines.add(replacer.replace(Component.text(line))));
        return lines;
    }

}
