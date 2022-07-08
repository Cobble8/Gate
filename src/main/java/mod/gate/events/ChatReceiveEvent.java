package mod.gate.events;

import mod.gate.utils.ChatUtils;
import net.minecraft.client.gui.screen.multiplayer.SocialInteractionsScreen;
import net.minecraft.server.dedicated.gui.PlayerListGui;

public class ChatReceiveEvent {

    //return value is whether it should be cancelled. true = do cancel, false = don't cancel
    public static boolean onChatReceived(String msg) {




        return false;
    }

}
