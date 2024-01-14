package org.goldfinch.quests.listener;

import java.util.List;
import java.util.UUID;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.goldfinch.quests.Quests;
import org.goldfinch.quests.language.MessagesConfig;
import org.goldfinch.quests.player.entity.QuestPlayerData;
import org.goldfinch.quests.player.QuestPlayerDataManager;
import org.goldfinch.quests.quest.entity.ActiveQuest;
import org.goldfinch.quests.tasks.impl.KillMobsTask;
import org.goldfinch.quests.tasks.impl.ReachLocationTask;

public class QuestListener implements Listener {

    private final QuestPlayerDataManager playerDataManager;
    private final MessagesConfig config;

    public QuestListener(Quests plugin) {
        this.playerDataManager = plugin.getPlayerDataManager();
        this.config = plugin.getMessagesConfig();
    }

    @EventHandler
    public void on(PlayerJoinEvent event) {
        final UUID uuid = event.getPlayer().getUniqueId();
        final QuestPlayerData playerData = this.playerDataManager.get(uuid);
        final List<ActiveQuest> activeQuests = playerData.getActiveQuests();

        if (activeQuests.size() <= 3) {
            activeQuests.forEach(activeQuest -> {
                playerData.getBukkitPlayer().sendMessage(activeQuest.toComponent());
            });
        } else {
            this.config.send(playerData, MessagesConfig.Message.NOTIFICATION_JOIN_MANY_QUESTS, "%quests_count%", activeQuests.size());
        }
    }

    @EventHandler
    public void on(PlayerMoveEvent event) {
        if (!event.hasChangedBlock()) {
            return;
        }

        final UUID uuid = event.getPlayer().getUniqueId();
        final QuestPlayerData playerData = this.playerDataManager.get(uuid);

        playerData.getActiveTasks(ReachLocationTask.class).forEach(activeTask -> activeTask.tryProgress(event));
    }

    @EventHandler
    public void on(EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null) {
            return;
        }

        final UUID uuid = event.getEntity().getKiller().getUniqueId();
        final QuestPlayerData playerData = this.playerDataManager.get(uuid);

        playerData.getActiveTasks(KillMobsTask.class).forEach(activeTask -> activeTask.tryProgress(event));
    }

}
