package mod.gate.skyblock;

import mod.gate.utils.ChatUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.scoreboard.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;

public class SBInfo {

    public static Skills lastSkill = Skills.FORAGING;
    public static boolean inMegaLobby = false;
    public static String lobby = "";
    public static String profile = "";
    public static String area = "Unknown";
    public static String subarea = "Unknown";
    public static boolean onSkyblock = false;

    //Currently, this isn't being called from anywhere (I just tested it with the command)
    public static void update() {
        manageScoreBoard();
        manageTabList();
    }

    private static void manageScoreBoard() {
        assert MinecraftClient.getInstance().world != null;
        Collection<ScoreboardObjective> objectives = MinecraftClient.getInstance().world.getScoreboard().getObjectives();
        onSkyblock = false;
        for(ScoreboardObjective objective : objectives) {
            if(objective.getName().equals("SBScoreboard")) {
                onSkyblock = objective.getDisplayName().getString().equals("SKYBLOCK");
                break;
            }
        }
        if(!onSkyblock) {return;}
        Collection<Team> teams = MinecraftClient.getInstance().world.getScoreboard().getTeams();
        for(Team team : teams) {
            String line = team.getPrefix().getString()+team.getSuffix().getString();
            if(line.contains("⏣")) {
                subarea = line.substring(3).toLowerCase(Locale.ROOT).replace(" ","_");
            }

        }
    }

    private static void manageTabList() {
        if(!onSkyblock) {return;}
        ArrayList<String> tabData = new ArrayList<>();
        try {
            assert MinecraftClient.getInstance().player != null;
            Collection<PlayerListEntry> playerList = MinecraftClient.getInstance().player.networkHandler.getPlayerList();
            for(PlayerListEntry player : playerList) {
                try {
                    if(player.getProfile().getName().contains("!")) { //This check is there because since all the tab stuff is fake players, hypixel puts a '!' in their fake usernames because normally you can't.
                        tabData.add(Objects.requireNonNull(player.getDisplayName()).getString().trim());
                    }
                } catch(Exception ignored) {}
            }
        } catch(Exception ignored) {}
        for(int i=0;i<tabData.size();i++) { //This isn't an enhanced for loop because sometimes for detecting which line its on, you need to go line+1 (aka 'i' needs to be a number)
            String line = tabData.get(i);
            if(line.startsWith("Skills: ")) {
                line = line.substring(8);
                lastSkill = Skills.getByName(line.substring(0, line.indexOf(" ")));
            } else if(line.startsWith("Server: ")) {
                lobby = line.substring(8);
                inMegaLobby = lobby.contains("mega");
            } else if(line.startsWith("Profile: ")) {
                profile = line.substring(9).toLowerCase(Locale.ROOT).replace(" ","_");
            } else if(line.startsWith("Area: ")) {
                area = line.substring(6).toLowerCase(Locale.ROOT).replace(" ","_");
            }
        }
    }

}
