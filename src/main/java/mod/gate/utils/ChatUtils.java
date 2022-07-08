package mod.gate.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.regex.Pattern;

public class ChatUtils {
    private static final String PREFIX = "§6[GATE]§f ";
    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)§[0-9A-FK-ORZ]");
    public static void sendChatMessage(String string, boolean prefix) {
        if (prefix) string = PREFIX + string;
        if (MinecraftClient.getInstance().player != null)
            MinecraftClient.getInstance().player.sendMessage(Text.of(string), false);
    }

    public static void sendChatMessage(Object string) {
        ChatUtils.sendChatMessage(string+"", true);
    }


    /**
     * Strips MC color codes out of a string
     * @param input Any string
     * @return String
     */
    public static String strip(String input) {
        return STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }
}
