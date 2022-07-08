package mod.gate.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;

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

    //TODO Add event to run this command
    private static void loadTablist() {
        if (!Scoreboard.isOnSkyblock()) return;

        ArrayList<String> lines = getTabData();

        for (String line : lines) {
            if (line.startsWith("Skills: "))
                lastSkill = Skills.getByName(line.substring(8).substring(0, line.substring(8).indexOf(" ")));

            else if (line.startsWith("Server: "))
                lobby = line.substring(8);

            else if (line.startsWith("Profile: "))
                profile = line.substring(9).toLowerCase(Locale.ROOT).replace(" ","_");

            else if (line.startsWith("Area: "))
                area = line.substring(6).toLowerCase(Locale.ROOT).replace(" ","_");
        }
    }

    private static ArrayList<String> getTabData() {
        ArrayList<String> lines = new ArrayList<>();
        try {
            assert MinecraftClient.getInstance().player != null;
            Collection<PlayerListEntry> playerList = MinecraftClient.getInstance().player.networkHandler.getPlayerList();
            for (PlayerListEntry player : playerList) {
                try {
                    if (player.getProfile().getName().contains("!")) //This check is there because since all the tab stuff is fake players, hypixel puts a '!' in their fake usernames because normally you can't.
                        lines.add(Objects.requireNonNull(player.getDisplayName()).getString().trim());
                } catch(Exception ignored) {}
            }
        } catch (Exception ignored) {}
        return lines;
    }
}
