package mod.gate.commands;

import mod.gate.events.CommandEvent;
import mod.gate.events.Event;
import mod.gate.utils.ChatUtils;

public class CommandDispatcher {

    @Event(event = CommandEvent.class)
    public void onCommand(CommandEvent e) {
        new MainCommand("update").e(e);

    }
}
