package mod.gate.commands;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;

public class Command {
    public String LITERAL_NAME;
    public ArrayList<CommandArg> args;


    public Command(String literal_name) {
        args = new ArrayList<>();
        LITERAL_NAME = literal_name;
        System.out.println("creating new instance of " + this.getClass().getSimpleName() + " with literal name of " + this.LITERAL_NAME);
    }
    public int run(CommandContext<ServerCommandSource> context) {
        return 1;
    }

    public static class CommandArg {
        public String LITERAL_NAME;

        public CommandArg(String LITERALNAME) {
            LITERAL_NAME = LITERALNAME;
        }
    }
}
