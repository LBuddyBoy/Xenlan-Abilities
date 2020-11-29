package services.xenlan.xabilities.gui.give.convo;

import org.bukkit.ChatColor;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import services.xenlan.xabilities.util.interfaces.Callback;
import services.xenlan.xabilities.xAbilities;

public class Coversation {

    public static void conversationDouble(Player p, String prompt, Callback<Double> callback) {
        ConversationFactory factory = new ConversationFactory(xAbilities.getInstance()).withModality(true).withPrefix(new NullConversationPrefix()).withFirstPrompt(new StringPrompt() {

            public String getPromptText(ConversationContext context) {
                return prompt;
            }

            @Override
            public Prompt acceptInput(ConversationContext cc, String s) {
                try {
                    callback.callback(Double.parseDouble(s));
                } catch (NumberFormatException e) {
                    cc.getForWhom().sendRawMessage(ChatColor.RED + s + " is not a number.");
                }

                return Prompt.END_OF_CONVERSATION;
            }

        }).withLocalEcho(false).withEscapeSequence("quit").withTimeout(10).thatExcludesNonPlayersWithMessage("Go away evil console!");

        Conversation con = factory.buildConversation(p);
        p.beginConversation(con);

    }

}
