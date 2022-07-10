package mod.gate.features;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import mod.gate.Gate;
import mod.gate.core.events.ChatReceiveEvent;
import mod.gate.core.events.Event;
import mod.gate.utils.ChatUtils;
import mod.gate.utils.Reference;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class NPCDialogue {
        private final String ERROR_PREFIX = "NPCDialogue: ";
        private final Identifier dialogue = new Identifier(Reference.MODID, "data/npc_dialogue.json");
        private static boolean inConversation = false;

        public NPCDialogue() {}

        @Event(event = ChatReceiveEvent.class)
        public void OnChatReceive(ChatReceiveEvent event) {
            String msg = ChatUtils.strip(event.message);

            if (!msg.startsWith("[NPC] ")) return;


            JsonArray lines = getDialogue(msg);
            if (lines == null) {
                Gate.LOGGER.error(ERROR_PREFIX + "Dialogue lines were empty");
                return;
            }

            //This will basically prevent duplicate conversations from taking place if they spam click the NPC
            if(inConversation) {
                event.info.cancel();
                return;
            }
            inConversation = true;


            Object delay = getDelay(msg);
            if (delay == null ) {
                Gate.LOGGER.error(ERROR_PREFIX + "Delay was null");
                return;
            }
            boolean cancel = cancel(msg);
            if(cancel) event.info.cancel();

            new Thread(() -> {
                for(int i=0;i<lines.size();i++) {
                    JsonElement line = lines.get(i);

                    //If they cancel the initial message, there shouldn't be delay onto the first dialogue line.
                    if(!(i == 0 && cancel)) {try { Thread.sleep((long) delay); } catch(Exception ignored) {}}

                    assert MinecraftClient.getInstance().player != null;
                    String message = line.getAsString().replace("{name}", MinecraftClient.getInstance().player.getDisplayName().getString());

                    //Starting a line with '{!}' will make it so the [NPC] prefix isn't there.
                    if(message.startsWith("{!}")) message = message.substring(3);
                    else message = "§b[NPC] "+message;

                    Text prefix = Text.of(message);
                    Style style = Style.EMPTY.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.of(ChatUtils.PREFIX)));

                    List<Text> list = prefix.getWithStyle(style);

                    if(list.size() >= 1) ChatUtils.sendChatMessage(list.get(0));
                }
                inConversation = false;
            }).start();
        }

        private JsonArray getDialogue(String msg) {
            JsonObject rawDialogue = getRawDialogue(msg);
            if (rawDialogue == null) return null;
            return rawDialogue.get("dialogue").getAsJsonArray();
        }

        private Object getDelay(String msg) {
            JsonObject rawDialogue = getRawDialogue(msg);
            if (rawDialogue == null) return null;
            return rawDialogue.get("delay").getAsLong();
        }

        private boolean cancel(String msg) {
            JsonObject rawDialogue = getRawDialogue(msg);
            if (rawDialogue == null) return false;
            try { return rawDialogue.get("cancel").getAsBoolean(); } catch(Exception e) {return false;}
            //The try/catch is there so if they don't provide the 'cancel' thing, it won't cancel it.
        }

        private JsonObject getRawDialogue(String msg) {
            try {
                InputStream in = MinecraftClient.getInstance().getResourceManager().getResource(dialogue).getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder data = new StringBuilder();
                String l = reader.readLine();
                while(l != null) {
                    data.append(l);
                    l = reader.readLine();
                }
                JsonObject obj = JsonParser.parseString(data.toString()).getAsJsonObject();
                return obj.get(msg).getAsJsonObject();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
 }
