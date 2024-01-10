package org.goldfinch.quests.language;

import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;
import org.goldfinch.quests.Quests;
import org.goldfinch.quests.player.entity.QuestPlayerData;
import ru.artorium.configs.Config;

@NoArgsConstructor
public class MessagesConfig extends Config {

    private final Map<Message, Map<Language, Component>> localization = Map.of(
        Message.QUEST_START, Map.of(
            Language.ENGLISH, Component.text("You've started a new quest!"),
            Language.GERMAN, Component.text("Du hast eine neue Quest begonnen!")
        ),
        Message.QUEST_FINISH, Map.of(
            Language.ENGLISH, Component.text("You've finished a quest!"),
            Language.GERMAN, Component.text("Du hast eine Quest abgeschlossen!")
        ),
        Message.QUEST_CANCEL, Map.of(
            Language.ENGLISH, Component.text("You've cancelled a quest!"),
            Language.GERMAN, Component.text("Du hast eine Quest abgebrochen!")
        ),
        Message.ALREADY_COMPLETING_QUEST, Map.of(
            Language.ENGLISH, Component.text("You're already completing a quest!"),
            Language.GERMAN, Component.text("Du hast bereits eine Quest begonnen!")
        ),
        Message.REQUIREMENTS_NOT_MET, Map.of(
            Language.ENGLISH, Component.text("You don't meet the requirements to start this quest!"),
            Language.GERMAN, Component.text("Du erfüllst die Anforderungen nicht, um diese Quest zu beginnen!")
        )
    );

    public Component get(Message message, Language language) {
        return this.localization.get(message).get(language);
    }

    public void send(QuestPlayerData playerData, Message message, Language language) {
       playerData.getBukkitPlayer().sendMessage(this.get(message, language));
    }

    public void send(QuestPlayerData playerData, Message message) {
        playerData.getBukkitPlayer().sendMessage(this.get(message, playerData.getLanguage()));
    }

    public MessagesConfig(Quests plugin) {
        super("messages", plugin.getDataFolder().getPath());
        this.reload();
    }

    @Override
    public MessagesConfig getTemplate() {
        return new MessagesConfig();
    }

    public enum Message {
        QUEST_START,
        QUEST_FINISH,
        QUEST_CANCEL,
        ALREADY_COMPLETING_QUEST,
        REQUIREMENTS_NOT_MET,
    }
}
