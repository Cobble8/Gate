package mod.gate.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ChatUtils {
    private static String PREFIX = "[GATE] ";

    public static void sendChatMessage(String string, boolean prefix) {
        if (prefix) string = PREFIX + string;
        if (MinecraftClient.getInstance().player != null)
            MinecraftClient.getInstance().player.sendMessage(Text.of(string), false);
    }

    public static void sendChatMessage(String string) {
        ChatUtils.sendChatMessage(string, true);
    }
}
