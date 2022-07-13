package mod.gate.core.command;

import mod.gate.features.commands.ChangeSetting;
import mod.gate.features.commands.GateSave;
import mod.gate.core.config.ConfigManager;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;

public class CommandRegistry {
    public static void register(Object command) {
        if (ConfigManager.inDebugMode()) System.out.println("registering command: " + ((Command) command).getName());
        ClientCommandManager.DISPATCHER.register(((Command) command).register());
    }

    public static void init() {
        register(new GateSave());
        register(new ChangeSetting());
    }
}
