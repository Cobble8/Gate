package mod.gate.commands;

import mod.gate.events.CommandEvent;
import mod.gate.utils.ChatUtils;

import java.util.List;

public class CommandBase {

    String[] aliases;

    public CommandBase(String... name) {
        this.aliases = name;
    }

    public void e(CommandEvent event) {
        for(String alias : aliases) {
            if(event.getCommandName().equals(alias)) {
                event.getInfo().cancel();
                run(event.getArguments());
                return;
            }
        }
    }

    public void run(List<String> args) {
        ChatUtils.sendChatMessage("How are you seeing this?");
    }

}