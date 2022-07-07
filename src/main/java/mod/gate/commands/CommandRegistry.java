package mod.gate.commands;

import java.util.ArrayList;

public class CommandRegistry {
    public ArrayList<Command> Commands;

    public CommandRegistry() {
        this.Commands = new ArrayList<>();
    }

    public void register(Command Command) {
        this.Commands.add(Command);
    }

    public void register(ArrayList<Command> Commands) {
        for (Command Command : Commands) {
            register(Command);
        }
    }
}
