package mod.gate.events;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import mod.gate.utils.ChatUtils;
import mod.gate.utils.Reference;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import net.minecraft.client.gui.screen.multiplayer.SocialInteractionsScreen;
import net.minecraft.server.dedicated.gui.PlayerListGui;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ChatReceiveEvent {
    public static Identifier dialogue = new Identifier(Reference.MODID, "data/npc_dialogue.json");


    //return value is whether it should be cancelled. true = do cancel, false = don't cancel
    public static boolean onChatReceived(String message) {
        String msg = ChatUtils.strip(message);

        if(msg.startsWith("[NPC] ")) {

            manageNpcDialogue(msg);
        }

        return false;
    }
    public static void manageNpcDialogue(String msg) {
        System.out.println("'"+msg+"'");
        try {
            //Lol this is literally the exact same code as the forge 1.8.9 equivalent
            InputStream in = MinecraftClient.getInstance().getResourceManager().getResource(dialogue).getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder data = new StringBuilder();
            String l = reader.readLine();
            while(l != null) {
                data.append(l);
                l = reader.readLine();
            }
            JsonObject obj = JsonParser.parseString(data.toString()).getAsJsonObject();
            System.out.println("'"+obj.toString()+"'");
            JsonObject dial = obj.get(msg).getAsJsonObject();
            long delay = dial.get("delay").getAsLong();
            JsonArray lines = dial.get("dialogue").getAsJsonArray();
            new Thread(() -> {
                for(JsonElement line : lines) {
                    try {Thread.sleep(delay);} catch(Exception ignored) {}
                    assert MinecraftClient.getInstance().player != null;
                    String out = "§e["+Reference.MODID.toUpperCase()+"] "+line.getAsString().replace("{name}", MinecraftClient.getInstance().player.getDisplayName().getString());
                    ChatUtils.sendChatMessage(out, false);
                }
            }).start();

        } catch(Exception e) {e.printStackTrace();}

    }
}
