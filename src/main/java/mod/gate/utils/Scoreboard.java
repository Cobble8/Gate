package mod.gate.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

public class Scoreboard {

    //region OnSkyblock
    private static boolean onSkyblock = false;

    public static boolean isOnSkyblock() {
        return onSkyblock;
    }
    //endregion

    //region SubArea
    private static String SubArea = "";

    public static String getCurrentSubArea() {
        return SubArea;
    }
    //endregion

    //region Lines
    private static ArrayList<String> Lines = new ArrayList<>();
    public static ArrayList<String> getLines() {
        return Lines;
    }


    public static void loadScoreboard() {
        assert MinecraftClient.getInstance().world != null;

        Collection<ScoreboardObjective> objectives = MinecraftClient.getInstance().world.getScoreboard().getObjectives();
        Collection<Team> teams = MinecraftClient.getInstance().world.getScoreboard().getTeams();

        //region CheckOnSkyblock
        onSkyblock = false;
        for (ScoreboardObjective objective : objectives) {
            if(objective.getName().equals("SBScoreboard")) {
                onSkyblock = objective.getDisplayName().getString().equals("SKYBLOCK");
                break;
            }
        }
        if (!onSkyblock) return;
        //endregion
        Lines = new ArrayList<>();//reset lines

        for (Team team : teams) {
            String line = team.getPrefix().getString()+team.getSuffix().getString();

            Lines.add(line);

            //region CheckSubArea
            if (line.contains("⏣"))
                SubArea = line.substring(3).toLowerCase(Locale.ROOT).replace(" ","_");
            //endregion
        }
    }
}
