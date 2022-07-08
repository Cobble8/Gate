package mod.gate.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.regex.Pattern;

public class ChatUtils {
    public static final String PREFIX = "§6["+ Reference.MODID.toUpperCase() +"] ";
    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)§[0-9A-FK-ORZ]");

    //region SendChatMessage
    public static void sendChatMessage(Text text) {
        if (MinecraftClient.getInstance().player != null)
            MinecraftClient.getInstance().player.sendMessage(text, false);
    }

    public static void sendChatMessage(String string, boolean prefix) {
        if (prefix) string = PREFIX + string;
        ChatUtils.sendChatMessage(Text.of(string));
    }

    public static void sendChatMessage(String string) {
        ChatUtils.sendChatMessage(string, true);
    }
    //endregion

    /**
     * Strips color codes from string
     * */
    public static String strip(String input) {
        return STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }
}
