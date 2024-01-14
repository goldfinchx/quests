package org.goldfinch.quests.language;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;
import org.goldfinch.quests.Quests;
import org.goldfinch.quests.player.entity.QuestPlayerData;
import ru.artorium.configs.Config;
import ru.artorium.configs.Replacer;

@NoArgsConstructor
public class MessagesConfig extends Config {

    private final Map<Message, Localization> tasks = Map.of(
        Message.TASK_KILL_MOBS, new Localization(Map.of(
            Language.ENGLISH, Component.text("Kill %amount% %entity_type%"),
            Language.GERMAN, Component.text("Töte %amount% %entity%")
        )),
        Message.TASK_REACH_LOCATION, new Localization(Map.of(
            Language.ENGLISH, Component.text("Reach %location%"),
            Language.GERMAN, Component.text("Erreiche %location%")
        ))
    );

    private final Map<Message, Localization> conditions = Map.of(
        Message.CONDITIONS_DEALINE, new Localization(Map.of(
            Language.ENGLISH, Component.text("Due to: %deadline%")
        ))
    );

    private final Map<Message, Localization> notifications = Map.of(
        Message.NOTIFICATION_JOIN_MANY_QUESTS, new Localization(Map.of(
            Language.ENGLISH, Component.text("You have %quests_count% active quests!")
        ))
    );

    private final Map<Message, Localization> fillers = Map.of(
        Message.TASKS_LIST_IN_QUEST_INFO, new Localization(Map.of(
            Language.ENGLISH, Component.text("Tasks: ")
        ))
    );


    private final Map<Message, Localization> quests = Map.of(
        Message.QUEST_START, new Localization(Map.of(
            Language.ENGLISH, Component.text("You've started a quest!"),
            Language.GERMAN, Component.text("Du hast eine Quest begonnen!")
        )),
        Message.QUEST_FINISH, new Localization(Map.of(
            Language.ENGLISH, Component.text("You've finished a quest!"),
            Language.GERMAN, Component.text("Du hast eine Quest beendet!")
        )),
        Message.QUEST_CANCEL, new Localization(Map.of(
            Language.ENGLISH, Component.text("You've cancelled a quest!"),
            Language.GERMAN, Component.text("Du hast eine Quest abgebrochen!")
        )),
        Message.ALREADY_COMPLETING_QUEST, new Localization(Map.of(
            Language.ENGLISH, Component.text("You're already completing this quest!"),
            Language.GERMAN, Component.text("Du bearbeitest diese Quest bereits!")
        )),
        Message.REQUIREMENTS_NOT_MET, new Localization(Map.of(
            Language.ENGLISH, Component.text("You don't meet the requirements to start this quest!"),
            Language.GERMAN, Component.text("Du erfüllst die Anforderungen nicht, um diese Quest zu starten!")
        )),
        Message.REQUIREMENTS_NOT_MET_COMPLETED_QUESTS, new Localization(Map.of(
            Language.ENGLISH, Component.text("You don't meet the requirements to start this quest!"),
            Language.GERMAN, Component.text("Du erfüllst die Anforderungen nicht, um diese Quest zu starten!")
        )),
        Message.REQUIREMENTS_NOT_MET_PERMISSIONS, new Localization(Map.of(
            Language.ENGLISH, Component.text("You don't meet the requirements to start this quest!"),
            Language.GERMAN, Component.text("Du erfüllst die Anforderungen nicht, um diese Quest zu starten!")
        )),
        Message.REQUIREMENTS_NOT_MET_LEVEL, new Localization(Map.of(
            Language.ENGLISH, Component.text("You don't meet the requirements to start this quest!"),
            Language.GERMAN, Component.text("Du erfüllst die Anforderungen nicht, um diese Quest zu starten!")
        )),
        Message.REWARD_QUEST_POINTS, new Localization(Map.of(
            Language.ENGLISH, Component.text("You've received %amount% quest points!"),
            Language.GERMAN, Component.text("Du hast %amount% Questpunkte erhalten!")
        ))
    );

    public MessagesConfig(Quests plugin) {
        super("messages.json", plugin.getDataFolder().getPath());
        this.reload();
    }

    public Component get(Message message, Language language, Object... replacements) {
        return Replacer.replace(this.getAll().get(message).getMap().get(language), replacements);
    }

    public Component get(Message message, Language language) {
        return this.getAll().get(message).getMap().get(language);
    }

    public Component get(Message message, QuestPlayerData playerData) {
        return this.get(message, playerData.getLanguage());
    }

    public Component get(Message message, QuestPlayerData playerData, Object... replacements) {
        return this.get(message, playerData.getLanguage(), replacements);
    }


    private Map<Message, Localization> getAll() {
        final Map<Message, Localization> allLocalizations = new java.util.HashMap<>(this.quests);
        allLocalizations.putAll(this.tasks);
        allLocalizations.putAll(this.fillers);
        allLocalizations.putAll(this.conditions);
        allLocalizations.putAll(this.notifications);
        return allLocalizations;
    }

    public void send(QuestPlayerData playerData, Message message, Language language) {
        playerData.getBukkitPlayer().sendMessage(this.get(message, language));
    }

    public void send(QuestPlayerData playerData, Message message) {
        playerData.getBukkitPlayer().sendMessage(this.get(message, playerData.getLanguage()));
    }

    public void send(QuestPlayerData playerData, Message message, Object... replacements) {
        playerData.getBukkitPlayer().sendMessage(this.get(message, playerData.getLanguage(), replacements));
    }



    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Localization {
        private Map<Language, Component> map = Map.of(
            Language.ENGLISH, Component.text(""),
            Language.GERMAN, Component.text("")
        );
    }

    public enum Language {
        ENGLISH,
        GERMAN
    }

    public enum Message {
        QUEST_START,
        QUEST_FINISH,
        QUEST_CANCEL,
        ALREADY_COMPLETING_QUEST,
        REQUIREMENTS_NOT_MET,
        REQUIREMENTS_NOT_MET_COMPLETED_QUESTS,
        REQUIREMENTS_NOT_MET_PERMISSIONS,
        REQUIREMENTS_NOT_MET_LEVEL,
        REWARD_QUEST_POINTS,
        REWARD_EXPERIENCE,
        REWARD_ITEMS,
        TASK_KILL_MOBS,
        TASK_REACH_LOCATION,
        TASKS_LIST_IN_QUEST_INFO,
        CONDITIONS_DEALINE,
        NOTIFICATION_JOIN_MANY_QUESTS,
    }


}
