package mod.gate.commands;

import mod.gate.utils.ChatUtils;

import java.util.List;

public class MainCommand extends CommandBase {


    public MainCommand(String... name) { super(name); }

    public void run(List<String> args) {
        ChatUtils.sendChatMessage("IT CAN DO THINGS");
    }
}
