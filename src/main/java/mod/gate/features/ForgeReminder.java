package mod.gate.features;

import mod.gate.Gate;
import mod.gate.core.config.Config;
import mod.gate.core.events.EndTickEvent;
import mod.gate.core.events.Event;
import mod.gate.core.events.JoinWorldEvent;
import mod.gate.utils.ChatUtils;
import mod.gate.utils.Tablist;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.apache.commons.lang3.reflect.FieldUtils;

public class ForgeReminder {
    //region CONFIG
    @Config(description = "the configuration for the forge reminder")
    public ForgeConfig forgereminder = new ForgeConfig();
    //endregion


    private boolean hasReceivedScoreboard = false;

    @Event(event = JoinWorldEvent.class)
    public void onJoinWorldEvent(JoinWorldEvent event) {
        if (!this.hasReceivedScoreboard || !this.forgereminder.enabled) return;
        //region check for slots
        for (int i = 1; i <= 5; i++) {
            try {
                long slotTime = FieldUtils.getField(ForgeConfig.class,"slot" + i).getLong(forgereminder);

                //if (FieldUtils.getField(ForgeConfig.class, "unlocked" + i).getBoolean(forgereminder)) ChatUtils.sendChatMessage("slot" + i + " is currently at timestamp: " + slotTime);
                if (FieldUtils.getField(ForgeConfig.class, "unlocked" + i).getBoolean(forgereminder)
                        && System.currentTimeMillis() >= slotTime && slotTime != 0L)
                {
                    //forgereminder.getClass().getField("slot" + i).set(forgereminder, 0L);//set that when the item is removed from forge

                    Style style = Style.EMPTY.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.of("Click to warp to the forge (if unlocked)")));
                    style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/warpforge"));//this doesn't work, apparently

                    ChatUtils.sendChatMessage(Text.of("§l§6[Gate/ForgeReminder]§f§b Forge §3slot " + i + " is available for pickup.").getWithStyle(style).get(0));
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        //endregion

        this.hasReceivedScoreboard = false;//first scoreboard packet isn't received at that point
    }

    @Event(event = EndTickEvent.class)
    public void onEndTick(EndTickEvent event) {
      if (this.hasReceivedScoreboard || Tablist.getLines() == null || !this.forgereminder.enabled) return;
      this.hasReceivedScoreboard = true;
      //first tick of the scoreboard being accessible

      if (!Tablist.getArea().equals("dwarven_mines")) return;


      for (String line: Tablist.getLines()) {
          if (line.equals("Forges (+1 more)") && !forgereminder.unlocked5) {
              forgereminder.unlocked5 = true;//5th one is unlocked
          }

        for (int i = 1; i<=4; i++) {//forge 5 isn't accessible through tablist
            try {
                if (line.startsWith(i+")")) {
                    if (line.endsWith("EMPTY")) {
                        if (FieldUtils.getField(ForgeConfig.class, "slot" + i).getLong(forgereminder) != 0L) {
                            forgereminder.getClass().getField("slot" + i).set(forgereminder, 0L);//clear date
                        }

                        if (!FieldUtils.getField(ForgeConfig.class, "unlocked" + i).getBoolean(forgereminder)) {
                            forgereminder.getClass().getField("unlocked" + i).set(forgereminder, true);//set to unlocked
                        }

                    } else if (line.endsWith("Ready!")) {
                        FieldUtils.getField(ForgeConfig.class, "slot"+i).set(forgereminder, System.currentTimeMillis());
                        if (!FieldUtils.getField(ForgeConfig.class, "unlocked" + i).getBoolean(forgereminder)) {
                            forgereminder.getClass().getField("unlocked" + i).set(forgereminder, true);//set to unlocked
                        }
                    } else {
                        String DatetoParse = line.substring(3);

                        long total = 0L;

                        for (String time: DatetoParse.split(" ")) {
                            try {
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
                            }catch (Exception ignored) {}
                            //System.out.println("parsed time: " + total);
                            total += System.currentTimeMillis();
                            //might be useful to do a check to see if result is accurate

                            //System.out.println("when it will end: " + (total + System.currentTimeMillis()));
                            FieldUtils.getField(ForgeConfig.class, "slot"+i).set(forgereminder, total);
                        }

                        if (!FieldUtils.getField(ForgeConfig.class, "unlocked" + i).getBoolean(forgereminder)) {
                            forgereminder.getClass().getField("unlocked" + i).set(forgereminder, true);//set to unlocked
                        }
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
      }
    }

    public static class ForgeConfig {
        public boolean enabled = true;

        public long slot1 = 0L;
        public boolean unlocked1 = false;

        public long slot2 = 0L;
        public boolean unlocked2 = false;

        public long slot3 = 0L;
        public boolean unlocked3 = false;

        public long slot4 = 0L;
        public boolean unlocked4 = false;

        public long slot5 = 0L;//inaccessible through tab list
        public boolean unlocked5 = false;//inaccessible through tab list
    }
}
