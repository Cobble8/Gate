package mod.gate.utils;

import mod.gate.core.events.Event;
import mod.gate.core.events.EventHandler;
import mod.gate.core.events.TablistEvent;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;

import java.util.ArrayList;
import java.util.Locale;

public class Tablist {

    //region LastSkill
    private static Skills lastSkill;

    public static Skills getLastSkill() {
        return lastSkill;
    }
    //endregion

    //region Lobby
    private static String lobby;

    public static String getLobby() {
        return lobby;
    }
    public static boolean inMegaLobby() {
        return lobby.contains("mega");
    }
    //endregion

    //region Profile
    private static String profile;

    public static String getCurrentProfile() {
        return profile;
    }
    //endregion

    //region Area
    private static String area;

    public static String getArea() {
        return area;
    }
    //endregion

    //region Lines
    private static ArrayList<String> Lines;

    public static ArrayList<String> getLines() {
        return Lines;
    }
    //endregion

    @Event(event = TablistEvent.class)
    private static void loadTablist(TablistEvent event) {
        if (!Scoreboard.isOnSkyblock()) return;

        for (PlayerListS2CPacket.Entry entry : event.packet.getEntries()) {
            if (entry.getDisplayName() == null) continue;

            String line = entry.getDisplayName().asString().trim();

            if (!entry.getProfile().getName().contains("!"))
                EventHandler.run(new TablistUpdateEvent(TablistUpdateType.PLAYER, line));


            if (line.startsWith("Skills: ")) {
                lastSkill = Skills.getByName(line.substring(8).substring(0, line.substring(8).indexOf(" ")));
                EventHandler.run(new TablistUpdateEvent(TablistUpdateType.LAST_SKILL, lastSkill.getName()));
            }

            else if (line.startsWith("Server: ")) {
                lobby = line.substring(8);
                EventHandler.run(new TablistUpdateEvent(TablistUpdateType.LOBBY, lobby));
            }

            else if (line.startsWith("Profile: ")) {
                profile = line.substring(9).toLowerCase(Locale.ROOT).replace(" ", "_");
                EventHandler.run(new TablistUpdateEvent(TablistUpdateType.PROFILE, profile));
            }

            else if (line.startsWith("Area: ")) {
                area = line.substring(6).toLowerCase(Locale.ROOT).replace(" ", "_");
                EventHandler.run(new TablistUpdateEvent(TablistUpdateType.AREA, area));
            }

            else EventHandler.run(new TablistUpdateEvent(TablistUpdateType.OTHER, line));
        }
    }

    public static class TablistUpdateEvent {
        public TablistUpdateType type;
        public String line;

        public TablistUpdateEvent(TablistUpdateType type, String line) {
            this.type = type;
            this.line = line;
        }
    }

    public enum TablistUpdateType {
        LAST_SKILL,
        LOBBY,
        PROFILE,
        AREA,
        PLAYER,
        OTHER
    }
}
