package mod.gate.features;

import mod.gate.core.config.Config;
import mod.gate.core.events.Event;
import mod.gate.utils.ChatUtils;
import mod.gate.utils.Tablist;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.ArrayList;

public class ForgeReminder {
    //region CONFIG
    @Config(description = "the configuration for the forge reminder")
    public ForgeConfig forgeReminder = new ForgeConfig();
    //endregion

    @Event(event = Tablist.TablistUpdateEvent.class)
    public void onTablistUpdate(Tablist.TablistUpdateEvent event) {
        if (event.type != Tablist.TablistUpdateType.OTHER) return;

        // Finding if the 5th slot is unlocked, it isn't shown on Tablist
        if (event.line.equals("Forges (+1 more)") && !forgeReminder.slots.get(5).unlocked) {
            forgeReminder.slots.get(5).unlocked = true;
        }

        for (ForgeSlot slot : forgeReminder.slots) {
            if (slot.slotNumber != 5) {
                processLine(slot, event.line);
            };

            if (!slot.unlocked && System.currentTimeMillis() < slot.time && slot.time == 0L && !slot.notify) continue;
            slot.notify = false;

            Style style = Style.EMPTY
                    .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.of("Click to warp to the forge [if unlocked]")))
                    //TODO find a way to make this click event work
                    .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/warpforge"));

            ChatUtils.sendChatMessage(Text.of(ChatUtils.PREFIX + "§8 | [§bForgeReminder§8] §f> " + "Forge §3slot " + slot.slotNumber + " §fis available for pickup.").getWithStyle(style).get(0));
        }
    }

    private void processLine(ForgeSlot slot, String line) {
        if (!line.startsWith(slot.slotNumber + ")")) return;

        if (line.endsWith("EMPTY")) {
            if (slot.time != 0L) slot.time = 0L;

            slot.unlocked = true;
            slot.notify = true;
        } else if (line.endsWith("Ready!")) {
            slot.time = System.currentTimeMillis();

            slot.unlocked = true;
        } else {
            String DateToParse = line.substring(3);
            long total = 0L;

            for (String time : DateToParse.split(" ")) {
                if (time.contains("s")) {
                    time = time.replace("s", "");
                    int s = Integer.parseInt(time);
                    total+= s * 1000L;
                } else if (time.contains("m")) {
                    time = time.replace("m", "");
                    int m = Integer.parseInt(time);
                    total += m * 60000L;
                } else if (time.contains("h")) {
                    time = time.replace("h", "");
                    int h = Integer.parseInt(time);
                    total += h * 3600000L;
                } else if (time.contains("d")) {
                    time = time.replace("d", "");
                    int d = Integer.parseInt(time);
                    total += d * 86400000L;
                }
            }

            total += System.currentTimeMillis();

            slot.time = total;

            slot.unlocked = true;
            slot.notify = true;
        }
    }

    public static class ForgeConfig {
        public boolean enabled = true;
        public ArrayList<ForgeSlot> slots = new ArrayList<>();

        public ForgeConfig() {
            slots.add(new ForgeSlot(1));
            slots.add(new ForgeSlot(2));
            slots.add(new ForgeSlot(3));
            slots.add(new ForgeSlot(4));
            slots.add(new ForgeSlot(5));
        }
    }

    public static class ForgeSlot {
        public long time;
        public boolean unlocked;
        public int slotNumber;
        public boolean notify;

        public ForgeSlot(int slotNumber) {
            this.slotNumber = slotNumber;
            this.time = 0L;
            this.unlocked = false;
            this.notify = true;
        }
    }
}
